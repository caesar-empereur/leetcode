package com.concurrent;

/**
 * @Description
 * @author: yangyingyang
 * @date: 2023/5/17.
 */
public class SeqPrintSyncWait {

    private static String string = "C";

    private static final Object lock = new Object();

    private static void print(Integer value){
        for (;;) {
            try {
                synchronized (lock) {
                    Thread.sleep(500);
                    if (value.equals(1)){
                        if (string.equals("C")) {
                            System.out.println("A");
                            string = "A";
                            lock.notifyAll();
                        }
                        else {
                            lock.wait();
                        }
                    }
                    if (value.equals(2)){
                        if (string.equals("A")) {
                            System.out.println("B");
                            string = "B";
                            lock.notifyAll();
                        }
                        else {
                            lock.wait();
                        }
                    }
                    if (value.equals(3)){
                        if (string.equals("B")) {
                            System.out.println("C");
                            string = "C";
                            lock.notifyAll();
                        }
                        else {
                            lock.wait();
                        }
                    }
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread threadA = new Thread(()->print(1));
        Thread threadB = new Thread(()->print(2));
        Thread threadC = new Thread(()->print(3));
        threadA.setName(" thread A");
        threadA.start();

        threadB.setName(" thread B");
        threadB.start();

        threadC.setName(" thread C");
        threadC.start();
    }
}
