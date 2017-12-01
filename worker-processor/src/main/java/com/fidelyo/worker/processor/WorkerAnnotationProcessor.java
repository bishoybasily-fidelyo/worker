package com.fidelyo.worker.processor;

import com.fidelyo.worker.annotations.Job;
import com.fidelyo.worker.annotations.Worker;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

@SupportedAnnotationTypes("com.fidelyo.worker.annotations.Worker")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class WorkerAnnotationProcessor extends AbstractProcessor {

    private final String generatedPackageName = "com.app.generated";

    private Messager messager;
    private Filer filer;
    private Elements elements;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        this.messager = processingEnvironment.getMessager();
        this.filer = processingEnvironment.getFiler();
        this.elements = processingEnvironment.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        ClassName _contextClass = ClassName.get("android.content", "Context");
        ClassName _intentClass = ClassName.get("android.content", "Intent");
        ClassName _intentServiceClass = ClassName.get("android.app", "IntentService");

        for (Element element : roundEnvironment.getElementsAnnotatedWith(Worker.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                TypeElement typeElement = (TypeElement) element;

                String elementClassName = "Worker" + typeElement.getSimpleName();
                TypeSpec.Builder elementHandlerClass = TypeSpec.classBuilder(elementClassName)
                        .addModifiers(Modifier.PUBLIC)
                        .superclass(_intentServiceClass);

                MethodSpec.Builder elementHandlerConstructor = MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .addStatement("super(\"" + elementClassName + "\")");
                elementHandlerClass.addMethod(elementHandlerConstructor.build());

                Set<String> actions = new HashSet<>();

                for (Element e : typeElement.getEnclosedElements()) {
                    if (e.getKind() == ElementKind.METHOD && e.getAnnotation(Job.class) != null) {
                        ExecutableElement executableElement = (ExecutableElement) e;

                        MethodSpec.Builder elementTemplateFunction = MethodSpec.methodBuilder(methodName(executableElement))
                                .addModifiers(Modifier.PUBLIC)
                                .addParameter(_intentClass, "intent");
                        elementHandlerClass.addMethod(elementTemplateFunction.build());

                        MethodSpec.Builder elementExecuterFunction = MethodSpec.methodBuilder("execute" + capitalizeFirstLetter(methodName(executableElement)) + "Job")
                                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                .addParameter(_contextClass, "context")
                                .addParameter(_intentClass, "intent")
                                .addStatement("context.startService(intent.setClass(context, " + packageReference(typeElement) + "." + className(typeElement) + ".class).setAction(\"" + methodName(executableElement) + "\"))");
                        elementHandlerClass.addMethod(elementExecuterFunction.build());

                        actions.add(methodName(executableElement));

                    }
                }

                MethodSpec.Builder handlerOnHandleIntentFunction = MethodSpec.methodBuilder("onHandleIntent")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .addParameter(_intentClass, "intent");

                CodeBlock.Builder elementSwitchBlock = CodeBlock.builder();
                elementSwitchBlock.add("switch (intent.getAction()){");
                for (String action : actions)
                    elementSwitchBlock.add("case \"" + action + "\": " + action + "(intent); break;");
                elementSwitchBlock.add("}");
                handlerOnHandleIntentFunction.addCode(elementSwitchBlock.build());

                elementHandlerClass.addMethod(handlerOnHandleIntentFunction.build());

                writeClass(elementHandlerClass.build());

            }
        }

        return true;
    }

    private String methodName(ExecutableElement executableElement) {
        return executableElement.getSimpleName().toString();
    }

    private String className(TypeElement typeElement) {
        return typeElement.getSimpleName().toString();
    }

    private String packageReference(TypeElement typeElement) {
        return elements.getPackageOf(typeElement).getQualifiedName().toString();
    }

    private String capitalizeFirstLetter(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    private void writeClass(TypeSpec baseHandlerClass) {
        try {
            JavaFile.builder(generatedPackageName, baseHandlerClass)
                    .build()
                    .writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
