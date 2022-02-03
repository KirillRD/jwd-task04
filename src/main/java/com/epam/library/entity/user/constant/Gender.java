package com.epam.library.entity.user.constant;

public enum Gender {
    M, F;

    public static boolean containsGender(String value) {
        for (Gender gender : Gender.values()) {
            if (gender.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
