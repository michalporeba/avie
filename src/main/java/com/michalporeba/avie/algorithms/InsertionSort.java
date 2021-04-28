package com.michalporeba.avie.algorithms;

public class InsertionSort {
    private int[] a = new int[0];
    public void setup(int[] input) {
        a = input.clone();
    }

    public void sort() {
        int k = 0, i = 0;
        for(int j = 1; j < a.length; ++j) {
            k = a[j]; // assignment
            i = j-1;
            while (i>0 && a[i] > k) {  // comparisons
                a[i+1] = a[i];  // assignment + create 'empty space'
                --i; // assignment
            }
            a[i+1] = k;
        }
    }

    public void sort

    public boolean validate() {
        boolean outcome = true;
        for(int i = 1; outcome && i < a.length; ++i) {
            if (a[i] < a[i-1]) outcome = false;
        }
        return outcome;
    }
}