package com.epam.library.entity.user.constant;

/**
 * Enum of user genders
 */
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
