package com.epam.library.entity.user;

import java.util.Set;

public final class ReaderListFilterName {

    private ReaderListFilterName() {}

    public static final String LAST_NAME = "last_name";
    public static final String DEBTORS = "debtors";
    public static final String RESERVATION_DATE_FROM = "reservation_date_from";
    public static final String RESERVATION_DATE_TO = "reservation_date_to";
    public static final String RESERVATION = "reservation";
    public static final String SORT = "sort";
    public static final String LAST_NAME_ASCENDING = "last_name_ascending";
    public static final String LAST_NAME_DESCENDING = "last_name_descending";
    public static final String DAYS_DEBT_ASCENDING = "days_debt_ascending";
    public static final String DAYS_DEBT_DESCENDING = "days_debt_descending";
    public static final String RESERVATION_DATE_ASCENDING = "reservation_date_ascending";
    public static final String RESERVATION_DATE_DESCENDING = "reservation_date_descending";

    public static final Set<String> readerListFilterName = Set.of(
            LAST_NAME,
            DEBTORS,
            RESERVATION_DATE_FROM,
            RESERVATION_DATE_TO,
            RESERVATION,
            SORT
    );

    public static final Set<String> readerListFilterSortValues = Set.of(
            LAST_NAME_ASCENDING,
            LAST_NAME_DESCENDING,
            DAYS_DEBT_ASCENDING,
            DAYS_DEBT_DESCENDING,
            RESERVATION_DATE_ASCENDING,
            RESERVATION_DATE_DESCENDING
    );
}
