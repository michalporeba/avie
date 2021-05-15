package com.michalporeba.avie.algorithms;

import com.michalporeba.avie.variables.ArrayIndexer;
import com.michalporeba.avie.variables.ArrayVariable;
import com.michalporeba.avie.variables.NumericVariable;

public class BubbleSort
        extends ArrayAlgorithm {

    private ArrayVariable a = createArrayVariable("a");
    private ArrayIndexer i = createArrayIndexer("i");
    private ArrayIndexer j = createArrayIndexer("j");

    @Override
    public String getName() {
        return "Bubble Sort";
    }

    @Override
    protected void initialize(int[] data) {
        a.set(data);
        i.set(a.size()-1);
        j.set(0);
    }

    @Override
    protected boolean continueWhile() { return i.get() >= 0 ; }

    @Override
    protected void step() {
        j.set(0);
        while (j.get() < i.get()) {
            if (a.getAt(j)>a.getAt(j.next())) {
                a.swap(j, j.next());
            }
            j.increment();
        }
        i.decrement();
    }
}