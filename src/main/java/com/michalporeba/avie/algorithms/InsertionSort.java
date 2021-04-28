package com.michalporeba.avie.algorithms;

public class InsertionSort {
    private int[] state = new int[0];
    public void setup(int[] input) {
        state = input.clone();
    }

    public boolean validate() {
        boolean outcome = true;
        for(int i = 1; outcome && i < state.length; ++i) {
            if (state[i] < state[i-1]) outcome = false;
        }
        return outcome;
    }
}