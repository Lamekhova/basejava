package com.urise;

public class TestSingleton {

    private static TestSingleton instance;

    public static TestSingleton getInstance() {
        if (instance == null) {
            instance = new TestSingleton();
        }
        return instance;
    }

    private TestSingleton() {
    }

    public static void main(String[] args) {
        System.out.println(getInstance().toString());
        System.out.println((3169 % 13) + 1);
    }
}
