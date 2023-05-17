package com.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @author: yangyingyang
 * @date: 2023/5/17.
 */
public class SeqPrint {

    private static String string = "C";

    private static final Lock LOCK = new ReentrantLock();

    private static final Condition CONDITION_A = LOCK.newCondition();
    private static final Condition CONDITION_B = LOCK.newCondition();
    private static final Condition CONDITION_C = LOCK.newCondition();

    public static void main(String[] args) {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;){
                    try {
                        LOCK.lock();
                        if (string.equals("C")){
                            System.out.println("A");
                            string = "A";
                            CONDITION_B.signal(); // A 线程打印完通知 B 线程
                        } else { // 如果变量不是 C，则 A 线程挂起等待
                            CONDITION_A.await();
                        }
                    } catch (Exception e){

                    }
                    finally {
                        LOCK.unlock();
                    }
                }
            }
        });
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;){
                    try {
                        LOCK.lock();
                        if (string.equals("A")){
                            System.out.println("B");
                            string = "B";
                            CONDITION_C.signal();// B 线程打印完通知 C 线程
                        } else {// 如果变量不是 A，则 B 线程挂起等待
                            CONDITION_B.await();
                        }
                    }
                    catch (Exception e){

                    }
                    finally {
                        LOCK.unlock();
                    }
                }
            }
        });
        Thread threadC = new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;){
                    try {
                        LOCK.lock();
                        if (string.equals("B")){
                            System.out.println("C");
                            string = "C";
                            CONDITION_A.signal();// C 线程打印完通知 A 线程
                        } else {// 如果变量不是 B，则 C 线程挂起等待
                            CONDITION_C.await();
                        }
                    }
                    catch (Exception e){

                    }
                    finally {
                        LOCK.unlock();
                    }
                }
            }
        });
        threadA.setName(" thread A");
        threadA.start();

        threadB.setName(" thread B");
        threadB.start();

        threadC.setName(" thread C");
        threadC.start();
    }
}
