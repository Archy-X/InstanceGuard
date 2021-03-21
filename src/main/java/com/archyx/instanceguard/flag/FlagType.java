package com.archyx.instanceguard.flag;

import java.util.Locale;

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

    public static String getValueList() {
        StringBuilder list = new StringBuilder();
        for (FlagType flagType : FlagType.values()) {
            list.append(flagType.toString().toLowerCase(Locale.ROOT)).append(", ");
        }
        list.delete(list.length() - 2, list.length());
        return list.toString();
    }

}
