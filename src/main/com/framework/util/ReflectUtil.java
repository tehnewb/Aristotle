package com.framework.util;

import java.lang.reflect.Field;
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

	public static String[] describeFields(Object object) {
		try {
			Field[] fields = object.getClass().getDeclaredFields();
			String[] descriptions = new String[fields.length];
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);
				String fieldName = field.getName();
				Object fieldValue = field.get(object);
				if (fieldValue == null)
					continue;
				descriptions[i] = fieldName + " = " + fieldValue;
			}
			return descriptions;
		} catch (Exception e) {
			return null;
		}
	}

}
