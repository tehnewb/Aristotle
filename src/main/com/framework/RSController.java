package com.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code RSController} is a class that functions under the
 * {@code RSFramework} and will be automatically loaded when the program is
 * started.
 * 
 * @author Albert Beaupre
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RSController {

}
