package com.epam.library.entity.user;

import java.util.Set;

public final class UserListFilterName {

    private UserListFilterName() {}

    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String NICKNAME = "nickname";
    public static final String SORT = "sort";
    public static final String LAST_NAME_ASCENDING = "last_name_ascending";
    public static final String LAST_NAME_DESCENDING = "last_name_descending";
    public static final String ROLE_ASCENDING = "role_ascending";
    public static final String ROLE_DESCENDING = "role_descending";

    public static final Set<String> userListFilterName = Set.of(
            LAST_NAME,
            EMAIL,
            NICKNAME,
            SORT
    );

    public static final Set<String> userListFilterSortValues = Set.of(
            LAST_NAME_ASCENDING,
            LAST_NAME_DESCENDING,
            ROLE_ASCENDING,
            ROLE_DESCENDING
    );
}
