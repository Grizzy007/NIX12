package com.threads;

public class ThreadsHello {
    private int count;

    private synchronized int getCount() {
        return count;
    }

    private synchronized void setCount(int count) {
        this.count = count;
    }

    private synchronized void decrementCount() {
        count--;
    }

    public void sayHello() {
        setCount(50);
        Runnable runnable = () -> System.out.println("Hello from thread " + getCount());
        while (count > 0) {
            Thread t = new Thread(runnable);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            decrementCount();
        }
    }
}
