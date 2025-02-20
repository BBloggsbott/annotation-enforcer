package org.bbloggsbott.annotationenforcer;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface EnforceAnnotation {

    Class value();

}
