package com.epam.library.controller.util;

public class Util {

    public Util () {}

    /**
     * Returns true if string is in ID format
     * @param cs string that is checked
     * @return true if string is in ID format
     */
    public static boolean isID(CharSequence cs) {
        if (cs == null || cs.length() == 0 || (cs.length() == 1 && cs.charAt(0) == '0')) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
