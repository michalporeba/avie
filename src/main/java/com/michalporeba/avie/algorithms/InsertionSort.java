package com.michalporeba.avie.algorithms;

import com.michalporeba.avie.variables.*;

import java.util.*;


public class InsertionSort implements Iterable<String> {
    private List<String> steps = new ArrayList<>();
    private Map<String, Integer> variables = new HashMap<>();

    private ScalarVariable.Recorder scalarRecorder = new ScalarVariable.Recorder() {
        @Override
        public void read(ScalarVariable variable) {
            steps.add(String.format("%s ->", variable.getName()));
        }

        @Override
        public void write(ScalarVariable variable, Object value) {
            steps.add(String.format("%s <- %d", variable.getName(), value));
        }
    };

    private ScalarVariable.Recorder indexRecorder = new ScalarVariable.Recorder() {
        @Override
        public void read(ScalarVariable variable) {
            // do nothing
        }

        @Override
        public void write(ScalarVariable variable, Object value) {
            steps.add(String.format("%s <- %d", variable.getName(), value));
        }
    };

    private ArrayVariable.Recorder arrayRecorder = new ArrayVariable.Recorder() {
        @Override
        public void read(ArrayVariable variable, ArrayIndex index) {
            steps.add(String.format("%s[%s=%s] ->", variable.getName(), index.getName(), index.get()));
        }

        @Override
        public void write(ArrayVariable variable, ArrayIndex index, Object value) {
            steps.add(String.format("%s[$s=%s] <- %d", variable.getName(), index.getName(), index.get(), value));
        }
    };

    private ArrayVariable<Integer> a = new ArrayVariable<>(arrayRecorder, "a");
    private ArrayIndex i = new ArrayIndex(indexRecorder, "i");
    private ArrayIndex j = new ArrayIndex(indexRecorder, "j");
    private NumericVariable<Integer> k = new NumericVariable<>(scalarRecorder, "k", 0);

    public void setup(Integer[] input) {
        a.set(input);
        initialize();
    }

    public Integer[] getData() {
        return a.get();
    }

    private void initialize() {
        i.set(0);
        k.set(0);
        j.set(1);
    }

    private boolean continueWhile() {
        return j.get() < a.size();
    }

    private boolean advance() {
        if (continueWhile()) {
            step();
            return true;
        }

        return false;
    }

    private void step() {
        k.set(a.getAt(j));
        i.set(j.get()-1);
        while (i.get() >=0 && a.getAt(i) > k.get()) {
            a.move(i, i.next());
            i.decrement();
        }
        a.setAt(i.next(), k);

        j.increment();
    }

    public boolean validate() {
        boolean outcome = true;
        Integer[] data = a.get();
        for(int i = 1; outcome && i < a.size(); ++i) {
            if (data[i] < data[i-1]) outcome = false;
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