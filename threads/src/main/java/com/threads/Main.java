package com.threads;

import com.threads.factory.Robots;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        ThreadsHello hello = new ThreadsHello();
//        hello.sayHello();
//
//        ThreadsWordsCount count = new ThreadsWordsCount();
//        count.count(List.of(1,2,3,4,5,6,7,8,9,10));

        Robots robots = new Robots();
        robots.startSession();
    }
}
