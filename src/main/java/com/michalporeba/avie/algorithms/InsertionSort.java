package com.michalporeba.avie.algorithms;

import com.michalporeba.avie.variables.*;

import java.util.*;

public class InsertionSort
        extends ArrayAlgorithm {

    private ArrayVariable a = createArrayVariable("a");
    private ArrayIndexer i = createArrayIndexer("i");
    private ArrayIndexer j = createArrayIndexer("j");
    private NumericVariable k = createNumericVariable("k", 0);

    public void setup(int[] input) {
        a.set(input);
        initialize();
    }

    public int[] getData() {
        return a.get();
    }

    private void initialize() {
        i.set(0);
        k.set(0);
        j.set(1);
    }

    @Override
    protected boolean continueWhile() {
        return j.get() < a.size();
    }

    @Override
    protected void step() {
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
        int[] data = a.get();
        for(int i = 1; outcome && i < a.size(); ++i) {
            if (data[i] < data[i-1]) outcome = false;
        }
        return outcome;
    }
}