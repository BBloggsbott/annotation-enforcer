package org.bbloggsbott.annotationenforcer;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.util.Set;

@SupportedAnnotationTypes("org.bbloggsbott.annotationenforcer.EnforceAnnotation")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class EnforceAnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Starting EnforceAnnotationProcessor");
        StringBuilder errorMessageBuilder = new StringBuilder();
        for (final Element element: roundEnv.getElementsAnnotatedWith(EnforceAnnotation.class)){
            if (element instanceof VariableElement variableElement){
                EnforceAnnotation enforce = variableElement.getAnnotation(EnforceAnnotation.class);
                Class enforcedAnnotationClass = enforce.value();
                Annotation enforcedAnnotation = variableElement.getAnnotation(enforcedAnnotationClass);
                if (enforcedAnnotation == null){
                    processingEnv.getMessager().printMessage(
                            Diagnostic.Kind.ERROR,
                            String.format(
                                    "%s.%s does not have the necessary annotation %s",
                                    variableElement.getEnclosingElement().getClass().getName(),
                                    variableElement.getSimpleName(),
                                    enforcedAnnotationClass.getSimpleName()
                            )
                    );
                }
            }
        }
        return true;
    }
}
