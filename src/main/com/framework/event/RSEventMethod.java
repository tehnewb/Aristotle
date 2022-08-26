package com.framework.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Any method in the project with this annotation will be added as an
 * RSEventInvoker to the RSEventBus for the RSFramework.
 * 
 * @author Albert Beaupre
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RSEventMethod {

}
