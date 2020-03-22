package com.lpf.spring;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author lipengfei
 * @create 2018-09-19 16:29
 **/
public class TestQueue {

    public static void main(String[] args) {
        ConcurrentLinkedDeque queue = new ConcurrentLinkedDeque();
        queue.add("1");
        queue.add("2");
        queue.add("3");
        queue.add("4");
        queue.add("5");
        queue.add("6");
        queue.add("7");
        queue.add("8");
        queue.add("9");
        queue.add("10");
        queue.add("11");
        queue.add("12");
        queue.add("13");

        System.out.println(queue.pollFirst());
        System.out.println(queue.pollFirst());
        System.out.println(queue.getFirst());
        System.out.println("----------------");
        System.out.println(queue.getLast().toString());
        System.out.println(queue.getLast().toString());

    }
}
