package com.urise;

public class MainTestConcurrency {
    private static int counter;

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread() {
            @Override
            public void run() {
//                System.out.println(getName() + " " + getState());
            }
        };
//        System.out.println(thread.getState());
//        thread.start();
//        System.out.println(thread.getState());

//        new Thread(() -> System.out.println(Thread.currentThread().getPriority())).start();

        MainTestConcurrency mainTestConcurrency  = new MainTestConcurrency();
        for (int i = 0; i < 10; i++) {
            new Thread(mainTestConcurrency::inc).start();
        }
        Thread.sleep(500);

        System.out.println(counter);
    }

    private void inc() {
        synchronized (this) {
            counter++;
        }
    }
}
