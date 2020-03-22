package com.lpf.spring.jvm;

/**
 * @author lipengfei
 * @create 2018-08-22 20:02
 **/
public class TestJvmDemo {

    private String aa = "a";
    private static String bb = "b";
    private static final String cc = "c";
    private static int inta = 121;
    private static int intb = 222;

    public static void main(String[] args) {
        String s = "我是不是你最爱的人，你为什么不说话？";
        String b = "\n滚";
        System.out.println(s + b + bb);
        System.out.println(inta + intb);

    }
}
