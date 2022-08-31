package com.framework;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import com.framework.event.RSController;
import com.framework.event.RSEventBus;
import com.framework.event.RSEventInvoker;
import com.framework.event.RSEventMethod;
import com.framework.network.RSNetworkTask;
import com.framework.network.RSSessionCoder;
import com.framework.resource.RSResource;
import com.framework.resource.RSResourceWorker;
import com.framework.tick.RSTick;
import com.framework.tick.RSTickTask;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * The {@code RSFramework} holds all necessary functionality of a RSController.
 * Any class using the RSController will be loaded into the RSFramework.
 * 
 * @author Albert Beaupre
 * @see com.framework.event.RSController
 */
@Slf4j
public final class RSFramework {

	private static final ScheduledExecutorService TickScheduler = Executors.newSingleThreadScheduledExecutor(d -> new Thread(d, "Tick-Thread"));
	private static final ExecutorService ResourceService = Executors.newSingleThreadExecutor(d -> new Thread(d, "Resrource-Worker"));

	private static final RSResourceWorker ResourceWorker = new RSResourceWorker();
	private static final RSTickTask TickTask = new RSTickTask();
	private static final RSEventBus EventBus = new RSEventBus();

	private static RSNetworkTask NetworkTask;

	private RSFramework() {
		// Inaccessible
	}

	/**
	 * Assigns the given {@code mainClass} as an RSFrameworkApplication and searches
	 * for every <b>@RSController</b> from its current package to the top-most level
	 * package.
	 * 
	 * @param mainClass the main class to search from
	 * @throws Exception if an error occurs during this application's life span
	 */
	public static void run(Class<?> mainClass) throws Exception {
		TickScheduler.scheduleWithFixedDelay(TickTask, 0, 1, TimeUnit.NANOSECONDS);
		log.info("Tick Service started.");

		log.info("Retrieving controller classes...");
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		ClassPath path = ClassPath.from(classLoader);
		String packageName = mainClass.getPackage().getName();
		ImmutableSet<ClassInfo> set = path.getTopLevelClassesRecursive(packageName);

		Iterator<ClassInfo> iterator = set.iterator();
		while (iterator.hasNext()) {
			ClassInfo info = iterator.next();

			Class<?> clazz = Class.forName(info.getName(), true, classLoader);
			if (clazz.isAnnotationPresent(RSController.class)) {
				Object parentObject = clazz.getDeclaredConstructor().newInstance();
				extractInvokers(parentObject);
			}
		}

		log.info("Finished loading controllers.");
	}

	/**
	 * Extracts methods within the given {@code parentObject} that have the
	 * {@code @RSSubscribe} annotation.
	 * 
	 * @param parentObject the parenting object to find underlying methods with the
	 *                     RSSubscribe annotation
	 */
	private static void extractInvokers(Object parentObject) {
		Class<?> clazz = parentObject.getClass();
		for (int methodIndex = 0; methodIndex < clazz.getMethods().length; methodIndex++) {
			Method method = clazz.getMethods()[methodIndex];
			method.setAccessible(true);
			if (method.isAnnotationPresent(RSEventMethod.class)) {
				for (Class<?> type : method.getParameterTypes()) {
					EventBus.addInvoker(new RSEventInvoker(type, parentObject, method));
				}
			}
		}
	}

	/**
	 * The given {@code file} must be a jar file containing classes meant to be
	 * loaded into the system.
	 * 
	 * @param file the jar file to load
	 */
	public static boolean loadPlugin(@NonNull File file) {
		try (URLClassLoader classLoader = new URLClassLoader(new URL[] { file.toURI().toURL() }, ClassLoader.getSystemClassLoader())) {
			JarFile jar = new JarFile(file);
			Enumeration<? extends ZipEntry> enumeration = jar.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry entry = enumeration.nextElement();
				String entryName = entry.getName();

				if (entryName.endsWith(".class")) {
					String newName = entryName.replace("/", ".").replace(".class", "");
					Class<?> clazz = classLoader.loadClass(newName);
					if (clazz.isAnnotationPresent(RSController.class)) {
						extractInvokers(clazz.getDeclaredConstructor().newInstance());
					}
					log.debug("Loaded class {} from plugin {}", newName, file.getName());
				}
			}

			jar.close();
		} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Binds the network of this {@code RSFramework} to the given {@code port}.
	 * 
	 * @param port
	 */
	public static void bind(int port, RSSessionCoder defaultCoder) {
		if (RSFramework.NetworkTask != null)
			throw new UnsupportedOperationException("The network has already been bound");
		if (defaultCoder == null)
			throw new NullPointerException("The default coder must be set to ensure decoding/encoding happens");

		RSFramework.NetworkTask = new RSNetworkTask(defaultCoder, port);

		Thread thread = new Thread(RSFramework.NetworkTask, "Network Thread");
		thread.setPriority(Thread.NORM_PRIORITY);
		thread.setDaemon(false);
		thread.start();
		log.info("Network bound to port {} using {}", port, defaultCoder.getClass().getSimpleName());
	}

	/**
	 * Posts to the RSEventBus of this {@code RSFramework}, and any RSEventInvoker
	 * using the type of the given {@code event} will invoke that method as long as
	 * the RSSubscribe annotation is on the method.
	 * 
	 * @param event the event to post
	 */
	public static void post(@NonNull Object event) {
		RSFramework.EventBus.invoke(event);
	}

	/**
	 * Queues the given {@code callable} to process the resource.
	 * 
	 * @param callable the callable to process the resource
	 */
	public static void queueResource(@SuppressWarnings("rawtypes") @NonNull RSResource callable) {
		ResourceWorker.queue(callable);
		ResourceService.execute(ResourceWorker);
	}

	/**
	 * Adds the given {@code processor} to the {@code RSTickService} for processing.
	 * 
	 * @param processor the processor to add
	 */
	public static void addTick(@NonNull RSTick tick) {
		RSFramework.TickTask.addTick(tick);
	}

}
