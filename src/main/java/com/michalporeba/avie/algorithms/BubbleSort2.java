package com.michalporeba.avie.algorithms;

import com.michalporeba.avie.variables.ArrayIndexer;
import com.michalporeba.avie.variables.ArrayVariable;
import com.michalporeba.avie.variables.NumericVariable;

public class BubbleSort2
        extends ArrayAlgorithm {

    private ArrayVariable a = createArrayVariable("a");
    private ArrayIndexer i = createArrayIndexer("i");
    private ArrayIndexer j = createArrayIndexer("j");
    private NumericVariable t = createNumericVariable("t", 0);

    @Override
    public String getName() {
        return "Bubble Sort 2";
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
        boolean moving = false;
        j.set(0);
        while (j.get() < i.get()) {
            if (a.getAt(j)>a.getAt(j.next())) {
                t.take(a, j);
                a.copy(j.next(), j);
                t.set(a, j.next());
                moving = true;
            }
            j.increment();
        }
        if (!moving) {
            setComplete();
        }
        i.decrement();
    }
}