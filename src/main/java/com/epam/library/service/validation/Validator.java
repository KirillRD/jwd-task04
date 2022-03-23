package com.epam.library.service.validation;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * Data validation class
 */
public class Validator {
    private static final Validator instance = new Validator();

    public static Validator getInstance() {
        return instance;
    }

    private Validator() {}

    private static final String REGEX = "regex";
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(REGEX);
    private static final String EMAIL_PATTERN = "email-pattern";
    private static final String ISBN_PATTER = "isbn-pattern";
    private static final String ISSN_PATTER = "issn-pattern";
    private static final String PHONE_NUMBER_PATTER = "phone-number-pattern";
    private static final String YEAR_PATTER = "year-pattern";
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * Returns true if string is empty
     * @param cs string that is checked
     * @return true if string is empty
     */
    public boolean isEmpty (CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * Returns true if string is integer
     * @param cs string that is checked
     * @return true if string is integer
     */
    public boolean isInteger(CharSequence cs) {
        if (isEmpty(cs) || (cs.length() == 1 && cs.charAt(0) == '0')) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return new BigInteger(String.valueOf(cs)).compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0;
    }

    /**
     * Returns true if string is rational number
     * @param str string that is checked
     * @return true if string is rational number
     */
    public boolean isRationalNumber(String str) {
        if (isEmpty(str)) {
            return false;
        }
        if (str.charAt(str.length() - 1) == '.') {
            return false;
        }
        if (str.charAt(0) == '-') {
            if (str.length() == 1) {
                return false;
            }
            return withDecimalParsing(str, 1);
        }
        return withDecimalParsing(str, 0);
    }

    private boolean withDecimalParsing(String str, int beginIdx) {
        int decimalPoints = 0;
        for (int i = beginIdx; i < str.length(); i++) {
            boolean isDecimalPoint = str.charAt(i) == '.';
            if (isDecimalPoint) {
                decimalPoints++;
            }
            if (decimalPoints > 1) {
                return false;
            }
            if (!isDecimalPoint && !Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if string is date
     * @param value string that is checked
     * @return true if string is date
     */
    public boolean isDate(String value) {
        if (value == null) {
            return false;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
        formatter.setLenient(false);
        try {
            formatter.parse(value);
        } catch (ParseException e) {
            return false;
        }
        if (DATE_PATTERN.length() != value.length()) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if string is in email format
     * @param email string that is checked
     * @return true if string is in email format
     */
    public boolean isEmail(String email) {
        return Pattern.compile(resourceBundle.getString(EMAIL_PATTERN)).matcher(email).matches();
    }

    /**
     * Returns true if string is in ISBN format
     * @param isbn string that is checked
     * @return true if string is in ISBN format
     */
    public boolean isISBN(String isbn) {
        return Pattern.compile(resourceBundle.getString(ISBN_PATTER)).matcher(isbn).matches();
    }

    /**
     * Returns true if string is in ISSN format
     * @param issn string that is checked
     * @return true if string is in ISSN format
     */
    public boolean isISSN(String issn) {
        return Pattern.compile(resourceBundle.getString(ISSN_PATTER)).matcher(issn).matches();
    }

    /**
     * Returns true if string is in phone format
     * @param phone string that is checked
     * @return true if string is in phone format
     */
    public boolean isPhone(String phone) {
        return Pattern.compile(resourceBundle.getString(PHONE_NUMBER_PATTER)).matcher(phone).matches();
    }

    /**
     * Returns true if string is in year format
     * @param year string that is checked
     * @return true if string is in year format
     */
    public boolean isYear(String year) {
        return Pattern.compile(resourceBundle.getString(YEAR_PATTER)).matcher(year).matches();
    }
}
