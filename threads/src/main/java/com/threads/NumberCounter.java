package com.threads;

import java.math.BigInteger;
import java.util.List;

public class NumberCounter implements Runnable {
    private final int start;
    private final int end;
    private final List<Integer> list;
    private int count;

    public NumberCounter(int start, int end, List<Integer> list) {
        this.start = start;
        this.end = end;
        this.list = list;
    }

    private boolean isSimple(int i) {
        BigInteger bigInteger = BigInteger.valueOf(i);
        return bigInteger.isProbablePrime((int) Math.log(i));
    }

    public int getCount() {
        return count;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            if (isSimple(list.get(i))) {
                count++;
            }
        }
        System.out.println("Count of simple numbers = " + count);
    }
}
