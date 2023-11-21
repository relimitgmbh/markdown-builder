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
	public static final String HEADING = "heading";

	String heading();

	String introduction() default "";

	int order();

}
