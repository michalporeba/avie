package com.michalporeba.avie.algorithms;

import com.michalporeba.avie.variables.*;

public class InsertionSort
        extends ArrayAlgorithm {

    private ArrayVariable a = createArrayVariable("a");
    private ArrayIndexer i = createArrayIndexer("i");
    private ArrayIndexer j = createArrayIndexer("j");
    private NumericVariable k = createNumericVariable("k", 0);

    @Override
    protected void initialize(int[] data) {
        a.set(data);
        i.set(0);
        k.set(0);
        j.set(1);
    }

    @Override
    public String getName() {
        return "Insertion Sort";
    }

    @Override
    protected boolean continueWhile() {
        return j.get() < a.size();
    }

    @Override
    protected void step() {
        k.take(a, j);
        i.set(j.get()-1);
        while (i.get() >=0 && a.getAt(i) > k.get()) {
            a.copy(i, i.next());
            i.decrement();
        }
        k.set(a, i.next());
        j.increment();
    }
}