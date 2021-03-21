package com.archyx.instanceguard.flag;

public class FlagValue {

    private final Object value;

    public FlagValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public boolean getAsBoolean() {
        return (boolean) value;
    }

    public int getAsInt() {
        return (int) value;
    }

    public double getAsDouble() {
        return (double) value;
    }

}
