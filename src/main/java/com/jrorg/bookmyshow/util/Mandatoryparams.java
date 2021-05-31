package com.jrorg.bookmyshow.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Mandatoryparams {
	/**
	 * Comma seperated strings
	 * @return
	 */
	String params() default ""; 
}
