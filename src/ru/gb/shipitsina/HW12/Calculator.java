package ru.gb.shipitsina.HW12;

import java.util.Arrays;

public class Calculator {
    static final int SIZE = 10_000_000;
    static final int HALF = SIZE / 2;
    static float[] arr = new float[SIZE];

    void calcOneThread(){
        long start = System.currentTimeMillis();

        Arrays.fill(arr, 1);

        Thread t1 = new Thread(() -> calc(arr));

        t1.start();

        try {
            t1.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("final time to calculate in one tread " + (System.currentTimeMillis()-start));
    }

    void calcTwoTreads(){
        long start = System.currentTimeMillis();

        Arrays.fill(arr, 1);

        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];

        System.arraycopy(arr,0,a1,0,HALF);
        System.arraycopy(arr,HALF,a2,0,HALF);

        Thread t1 = new Thread(() -> calc(a1));
        Thread t2 = new Thread(() -> calc(a2));

        t1.start();
        t2.start();

        try {
             t1.join();
             t2.join();
        } catch (InterruptedException e){
             e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, HALF);
        System.arraycopy(a2, 0, arr, HALF, HALF);

        System.out.println("final time to calculate in two treads " + (System.currentTimeMillis()-start));
    }

    private void calc(float[] arr){
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }
}
