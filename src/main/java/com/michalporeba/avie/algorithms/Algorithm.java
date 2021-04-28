package com.michalporeba.avie.algorithms;

public interface Algorithm {
    public void advance();
    public Cost getCost();
    public boolean isFinished();

    public class Cost {

    }
}