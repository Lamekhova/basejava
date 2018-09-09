package com.urise;

public class MainTestConcurrency {
    public static void main(String[] args) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + " " + getState());
            }
        };
        System.out.println(thread.getState());
        thread.start();
        System.out.println(thread.getState());

        new Thread(() -> System.out.println(Thread.currentThread().getPriority())).start();
    }
}
