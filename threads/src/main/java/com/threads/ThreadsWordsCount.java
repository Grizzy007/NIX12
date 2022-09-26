package com.threads;

import java.util.List;

public class ThreadsWordsCount {


    public void count(List<Integer> list) {
        NumberCounter nc1 = new NumberCounter(0, list.size() / 2, list);
        NumberCounter nc2 = new NumberCounter(list.size() / 2 + 1, list.size(), list);
        Thread t1 = new Thread(nc1);
        Thread t2 = new Thread(nc2);
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Total count = " + (nc1.getCount() + nc2.getCount()));
    }
}
