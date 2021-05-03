package com.michalporeba.avie.algorithms;

import com.michalporeba.avie.variables.ArrayIndexer;
import com.michalporeba.avie.variables.ArrayVariable;
import com.michalporeba.avie.variables.Variable;
import com.michalporeba.avie.visualisations.ArrayVisualisation;

import java.util.ArrayList;
import java.util.List;

public abstract class ArrayAlgorithm implements Algorithm {
    private final List<ArrayVisualisation> visualisations = new ArrayList<>();

    private final List<Variable> variables = new ArrayList<>();

    public String getName() { return "Insertion Sort"; }

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

    protected <T extends Variable> T register(T variable) {
        variables.add(variable);
        return variable;
    }
}