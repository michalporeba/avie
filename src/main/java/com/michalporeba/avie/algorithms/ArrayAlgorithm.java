package com.michalporeba.avie.algorithms;

import com.michalporeba.avie.operations.*;
import com.michalporeba.avie.variables.*;
import com.michalporeba.avie.visualisations.ArrayVisualisation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ArrayAlgorithm implements Algorithm {
    private final List<ArrayVisualisation> visualisations = new ArrayList<>();
    private final List<Variable> variables = new ArrayList<>();
    private List<Operation> steps = new ArrayList<>();

    private boolean complete = false;

    private ScalarVariable.Recorder scalarRecorder = new ScalarVariable.Recorder() {
        @Override
        public void read(ScalarVariable variable) {
            steps.add(new VariableGet());
        }

        @Override
        public void write(ScalarVariable variable, Object value) {
            steps.add(new VariableSet(variable.getName(), value));
        }

        @Override
        public void copy(ArrayVariable array, ArrayIndexer index, ScalarVariable variable) {
            steps.add(new ArrayToScalar(array.getName(), index.get(), variable.getName()));
        }

        @Override
        public void copy(ScalarVariable variable, ArrayVariable array, ArrayIndexer index) {
            steps.add(new ScalarToArray(array.getName(), index.get(), variable.getName()));
        }
    };

    private ScalarVariable.Recorder indexRecorder = new ScalarVariable.Recorder() {
        @Override
        public void write(ScalarVariable variable, Object value) {
            steps.add(new IndexerSet(variable.getName(), (int)value));
        }
    };

    private ArrayVariable.Recorder arrayRecorder = new ArrayVariable.Recorder() {
        @Override
        public void read(ArrayVariable variable, ArrayIndexer index) {
            //steps.add(String.format("%s[%s=%s] ->", variable.getName(), index.getName(), index.get()));
        }

        @Override
        public void write(ArrayVariable variable, ArrayIndexer index, Object value) {
            //steps.add(String.format("%s[$s=%s] <- %d", variable.getName(), index.getName(), index.get(), value));
        }

        @Override
        public void copy(ArrayVariable array, ArrayIndexer from, ArrayIndexer to) {
            steps.add(new ArrayToArray(array.getName(), from.get(), array.getName(), to.get()));
        }

        @Override
        public void swap(ArrayVariable array, ArrayIndexer a, ArrayIndexer b) {
            steps.add(new ArraySwap(array.getName(), a.get(), array.getName(), b.get()));
        }
    };

    public void setup(int[] input) {
        initialize(input);
    }

    protected abstract void initialize(int[] data);

    public boolean isComplete() { return this.complete; }

    public void attachTo(ArrayVisualisation visualisation) {
        visualisation.reset();
        visualisation.setName(getName());
        for(Variable v : variables) {
            if(v instanceof ArrayIndexer) {
                visualisation.registerIndexer(v.getName());
            } else if (v instanceof ArrayVariable) {
                visualisation.setData(v.getName(), ((ArrayVariable)v).get());
            } else {
                visualisation.registerVariable(v.getName());
            }
        }
        visualisations.add(visualisation);
    }

    protected ArrayVariable createArrayVariable(String name) {
        var variable = new ArrayVariable(arrayRecorder, name);
        variables.add(variable);
        return variable;
    }

    protected ArrayIndexer createArrayIndexer(String name) {
        var variable = new ArrayIndexer(indexRecorder, name);
        variables.add(variable);
        return variable;
    }

    protected NumericVariable createNumericVariable(String name, int initialValue) {
        var variable = new NumericVariable(scalarRecorder, name, initialValue);
        variables.add(variable);
        return variable;
    }



    protected abstract boolean continueWhile();
    protected abstract void step();

    private boolean advance() {
        if (continueWhile()) {
            step();
            return true;
        }
        complete = true;
        return false;
    }

    public void progress() {
        if (iterator().hasNext()) {
            var nextOperation = iterator().next();
            for (var v : visualisations) {
                v.show(nextOperation);
            }
        }
    }

    @Override
    public Iterator<Operation> iterator() {
        return new Iterator<>() {

            @Override
            public boolean hasNext() {
                if (steps.size() == 0)
                    return advance();

                return true;
            }

            @Override
            public Operation next() {
                return steps.remove(0);
            }
        };
    }
}