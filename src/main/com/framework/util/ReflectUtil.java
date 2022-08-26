package com.framework.util;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

public class ReflectUtil {

	public static ArrayList<Class<?>> getClassesInPackage(String packageName) {
		ArrayList<Class<?>> list = new ArrayList<>();
		try {
			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			ClassPath path = ClassPath.from(classLoader);
			ImmutableSet<ClassInfo> set = path.getTopLevelClassesRecursive(packageName);

			Iterator<ClassInfo> iterator = set.iterator();
			while (iterator.hasNext()) {
				ClassInfo info = iterator.next();
				list.add(Class.forName(info.getName(), true, classLoader));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
