package com.michalporeba.avie.algorithms;

import com.michalporeba.avie.variables.AccessRecorder;
import com.michalporeba.avie.variables.ArrayIndex;
import com.michalporeba.avie.variables.NumericVariable;
import com.michalporeba.avie.variables.Variable;

import java.util.*;


public class InsertionSort implements Iterable<String> {
    private int[] a = new int[0];
    private List<String> steps = new ArrayList<>();
    private Map<String, Integer> variables = new HashMap<>();

    private AccessRecorder recorder = new AccessRecorder() {
        @Override
        public void read(Variable variable) {
            steps.add(String.format("%s -> %d", variable.getName(), variable.getValueWithoutLogging()));
        }

        @Override
        public void write(Variable variable) {
            steps.add(String.format("%s = %d", variable.getName(), variable.getValueWithoutLogging()));
        }
    };

    private ArrayIndex i = new ArrayIndex(recorder, "i");
    private ArrayIndex j = new ArrayIndex(recorder, "j");
    private NumericVariable<Integer> k = new NumericVariable<>(recorder, "k", 0);


    private void a(int index, int value) {
        steps.add(String.format("a[%s] = %d", index, value));
        a[index] = value;
    }

    private int a(int index) {
        steps.add(String.format("a[%d] -> %d", index, a[index]));
        return a[index];
    }

    public void setup(int[] input) {
        a = input.clone();
        initialize();
    }

    public int[] getData() {
        return a;
    }

    private void initialize() {
        i.set(0);
        k.set(0);
        j.set(1);
    }

    private boolean continueWhile() {
        return j.getValue() < a.length;
    }

    private boolean advance() {
        if (continueWhile()) {
            step();
            return true;
        }

        return false;
    }

    private void step() {
        k.set(a(j.getValue()));
        i.set(j.getValue()-1);
        while (i.getValue() >=0 && a(i.getValue()) > k.getValue()) {
            a(i.getValue()+1,  a(i.getValue()));
            i.add(-1);
        }
        a(i.getValue()+1, k.getValue());

        j.add(1);
    }

    public boolean validate() {
        boolean outcome = true;
        for(int i = 1; outcome && i < a.length; ++i) {
            if (a[i] < a[i-1]) outcome = false;
        }
        return outcome;
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<>() {

            @Override
            public boolean hasNext() {
                if (steps.size() == 0)
                    return advance();

                return true;
            }

            @Override
            public String next() {
                return steps.remove(0);
            }
        };
    }
}