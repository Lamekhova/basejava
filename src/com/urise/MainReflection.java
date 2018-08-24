package com.urise;

import com.urise.model.Resume;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume("uuid", "New");

        Class<?> clazz = resume.getClass();
        Method method = clazz.getDeclaredMethod("toString");
        System.out.println(method.invoke(resume));
    }
}
