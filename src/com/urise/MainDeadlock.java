package com.urise;

class MainDeadLock {

    public static void main(String[] args) throws InterruptedException {
        MainDeadLock deadLock = new MainDeadLock();
        Object o1 = new Object();
        Object o2 = new Object();
        Thread thread1 = new Thread(() -> deadLock.doubleObjectLock(o1, o2));
        Thread thread2 = new Thread(() -> deadLock.doubleObjectLock(o2, o1));
        thread1.start();
        thread2.start();
    }

    void doubleObjectLock(Object lock1, Object lock2) {
        synchronized (lock1) {
            try {
                Thread.sleep(10);
            } catch (Exception ignore) {}
            synchronized (lock2) {
                System.out.println("inside");
            }
        }
    }
}
