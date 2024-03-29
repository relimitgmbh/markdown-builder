package de.relimit.commons.markdown;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({
		METHOD
})
public @interface Sample {

	// For source code parsing
	public static final String KEY = "key";

	// Must be unique
	String key();

	int order();

	boolean escape() default false;

}
