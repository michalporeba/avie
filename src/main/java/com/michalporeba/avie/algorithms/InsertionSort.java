package com.michalporeba.avie.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InsertionSort {
    private int[] a = new int[0];
    private List<String> steps = new ArrayList<>();
    private Map<String, Integer> variables = new HashMap<>();

    private final String I = "i";
    private final String J = "j";
    private final String K = "k";

    private void v(String variable, int value) {
        steps.add(String.format("%s = %d", variable, value));
        variables.put(variable, value);
    }

    private int v(String variable) {
        steps.add(String.format("%s -> %d", variable, variables.get(variable)));
        return variables.get(variable);
    }

    private void a(int index, int value) {
        steps.add(String.format("a[%s] = %d", index, value));
        a[index] = value;
    }

    private int a(int index) {
        steps.add(String.format("a[%d] -> %d", index, a[index]));
        return a[index];
    }

    private void increment(String variable) {
        steps.add(String.format("++%s", variable));
        variables.put(variable, 1+variables.get(variable));
    }

    private void decrement(String variable) {
        steps.add(String.format("--%s", variable));
        variables.put(variable, -1+variables.get(variable));
    }

    public void setup(int[] input) {
        a = input.clone();
    }

    public int[] getArray() {
        return a;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void sort() {
        v(I, 0);
        v(K, 0);

        for(v(J, 1); v(J) < a.length; increment(J)) {
            v(K, a(v(J)));
            v(I, v(J)-1);

            while (v(I)>=0 && a(v(I)) > v(K)) {  // comparisons
                a(v(I)+1,  a(v(I)));  // assignment + create 'empty space'
                decrement(I);
            }
            a(v(I)+1, v(K));
        }
    }

    public boolean validate() {
        boolean outcome = true;
        for(int i = 1; outcome && i < a.length; ++i) {
            if (a[i] < a[i-1]) outcome = false;
        }
        return outcome;
    }
}