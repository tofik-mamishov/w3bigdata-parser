package com.w3gdata.util


class EnumUtils {
    private EnumUtils() {}

    static <T extends Enum<T> & Valued> T of(Class<T> enumClass, int value) {
        for (T type: enumClass.enumConstants) {
            if (type.value == value) {
                return type
            }
        }
        throw new IllegalArgumentException("Illegal value $value for enumClass $enumClass")
    }
}
