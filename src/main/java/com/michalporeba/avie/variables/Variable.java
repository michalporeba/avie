package com.michalporeba.avie.variables;

public abstract class Variable {
    private final String name;

    protected Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
