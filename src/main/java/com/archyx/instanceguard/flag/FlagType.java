package com.archyx.instanceguard.flag;

public enum FlagType {

    BLOCK_BREAK(ValueType.BOOLEAN),
    BLOCK_PLACE(ValueType.BOOLEAN),
    BLOCK_INTERACT(ValueType.BOOLEAN);

    private final ValueType valueType;

    FlagType(ValueType valueType) {
        this.valueType = valueType;
    }

    public ValueType getValueType() {
        return valueType;
    }

}
