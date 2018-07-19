package com.urise;

import com.urise.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume("uuid");
        Field field = resume.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getAnnotatedType());
        System.out.println(field.getDeclaringClass());
        System.out.println(field.getName());
        System.out.println(field.toString());
        System.out.println(resume.toString());
        field.set(resume, "new UUid");
        System.out.println(resume.toString());
        field.set(resume, "bla-bla-bla");

        Class clazz = resume.getClass();
        Method method = clazz.getDeclaredMethod("toString");
        System.out.println(method.invoke(resume));
    }
}
