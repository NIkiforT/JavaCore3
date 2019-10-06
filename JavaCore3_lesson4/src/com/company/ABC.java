package com.company;

import java.util.concurrent.atomic.AtomicInteger;

public class ABC {
    Runnable a, b, c;
    void go(){
        Object lock = new Object();
        AtomicInteger i= new AtomicInteger();
        AtomicInteger j = new AtomicInteger();
        AtomicInteger k = new AtomicInteger();
        b = () ->{

            synchronized (lock){
                while (i.get() < 5) {
                    i.getAndIncrement();
                    try {
                        lock.wait(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print("B");
                    try {
                        lock.notifyAll();lock.wait();

                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        a = () ->{
            synchronized (lock){
                while (j.get() < 5) {
                    j.getAndIncrement();
                    try {
                        lock.wait(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print("A");
                    try {
                        lock.notifyAll();
                        lock.wait();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        c = () ->{
            synchronized (lock){
                while (k.get() < 5) {
                    k.getAndIncrement();
                    try {
                        lock.wait(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("C");
                    try {
                        lock.notifyAll();
                        lock.wait();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };

        new Thread(a).start();
        new Thread(b).start();
        new Thread(c).start();
    }

    public static void main(String[] args) {
        new ABC().go();
    }

}
