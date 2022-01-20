package com.epam.library.controller.command.constant;

public final class RedirectCommand {

    private RedirectCommand() {}

    public static final String RESERVATION_PAGE = "?command=go-to-reservation-page&book_id=%s";
    public static final String MAIN_PAGE = "?command=go-to-main-page";
    public static final String READER_PAGE = "?command=go-to-reader-page&reader_id=%s";
    public static final String BOOK_ISSUANCE_PAGE = "?command=book-issuance-page&reader_id=%s";
    public static final String BOOK_LIST_PAGE = "?command=book-list-page";
    public static final String INSTANCE_PAGE = "?command=instance-page&book_id=%s";
    public static final String USER_PAGE = "?command=go-to-user-page%s";
    public static final String BOOK_PAGE = "?command=go-to-book-page&book_id=%s";
    public static final String AUTHENTICATION_PAGE = "?command=go-to-authentication-page";
    public static final String ERROR_PAGE = "?command=go-to-error-page&error=%s";
    public static final String ADD_EDIT_BOOK_PAGE = "?command=go-to-add-edit-book-page%s";
    public static final String REGISTRATION_PAGE = "?command=go-to-registration-page";
    public static final String EDIT_USER_PAGE = "?command=go-to-edit-user-page%s";
}
