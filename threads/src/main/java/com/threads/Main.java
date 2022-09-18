package com.threads;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ThreadsHello hello = new ThreadsHello();
        hello.sayHello();

        ThreadsWordsCount count = new ThreadsWordsCount();
        count.count(List.of(1,2,3,4,5,6,7,8,9,10));
    }
}
