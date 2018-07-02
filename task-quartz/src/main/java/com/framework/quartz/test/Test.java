package com.framework.quartz.test;

/**
 * Created by HR on 2018/6/15.
 */
public class Test {
    public static void main(String[] args) {
        Cus c = new Cus();
        new Thread(c).start();
        new Thread(c).start();
    }
}

class Bank {
    private volatile int sum = 0;

    public /*synchronized*/ void add(int n) {
        sum = sum + n;
        System.out.println("sum= " + Thread.currentThread().getName() + "---" + sum);
    }
}

class Cus implements Runnable {
    Bank b = new Bank();

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            b.add(100);
        }
    }
}
