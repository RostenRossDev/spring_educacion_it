package EducacionIt.web.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public final class NotVoidValidator {

    private NotVoidValidator() {
    }

    // ---------- ¿Es inválido el valor? ----------
    public static boolean isInvalid(Object value) {
        if (value == null) return true;
        if (value instanceof CharSequence cs) return cs.toString().isEmpty();
        if (value instanceof Collection<?> collection) return collection.isEmpty();
        if (value instanceof Map<?, ?> collection) return collection.isEmpty();
        if (value instanceof Optional<?> o) return o.isEmpty();
        if (value instanceof Object[] a) return a.length == 0;
        return false;
    }

    // ---------- Validar parámetros de un método o constructor ----------
    public static void validateParameters(Executable executable, Object[] args) {
        if (args == null) return;

        Annotation[][] parameterAnnotations = executable.getParameterAnnotations();
        Parameter[] params = executable.getParameters();

        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation ann : parameterAnnotations[i]) {
                if (ann instanceof NotVoid nv && isInvalid(args[i])) {
                    throw new IllegalArgumentException(nv.message() + " (parámetro: '" + params[i].getName() + "' en " + describe(executable) + ")");
                }
            }
        }

        // Si el propio constructor/método tiene @NotVoid, validar TODOS los args
        if (executable.isAnnotationPresent(NotVoid.class)) {
            NotVoid nv = executable.getAnnotation(NotVoid.class);
            for (int i = 0; i < args.length; i++) {
                if (isInvalid(args[i])) {
                    throw new IllegalArgumentException(nv.message() + " (parámetro: '" + params[i].getName() + "' en " + describe(executable) + ")");
                }
            }
        }
    }

    // ---------- Validar un campo ----------
    public static  void validateField(Field field, Object owner) throws IllegalAccessException  {
        if (!field.isAnnotationPresent(NotVoid.class)) return;
        field.setAccessible(true);
        Object value = field.get(owner);
        if (isInvalid(value)) {
            NotVoid nv = field.getAnnotation(NotVoid.class);
            throw new IllegalArgumentException(nv.message() + " (parámetro: '" + field.getName() + ")");
        }
    }

    // ---------- Validar TODOS los campos anotados de un objeto ----------
    public static void validateFields(Object obj){
        if (obj == null) return;
        for (Field field : obj.getClass().getDeclaredFields()) {
            try {
                validateField(field, obj);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("No se pudo acceder al campo " + field.getName(), e);
            }
        }
    }

    private static String describe(Executable e) {
        return e instanceof Constructor<?> ? e.getDeclaringClass().getSimpleName() + ".<init>" : e.getName();
    }
}
