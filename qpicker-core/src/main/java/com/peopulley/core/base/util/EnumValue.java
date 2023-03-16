package com.peopulley.core.base.util;

public class EnumValue {
    private String name;
    private String value;

    public EnumValue(EnumModel enumModel) {
        name = enumModel.getValue();
        value = enumModel.getKey();
    }

    public EnumValue(String key, String value) {
        this.name = key.concat("(").concat(value).concat(")");
        this.value = key;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
