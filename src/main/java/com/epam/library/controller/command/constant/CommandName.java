package com.epam.library.controller.command.constant;

public enum CommandName {
    CHANGE_LOCALE("change-locale"),
    GO_TO_MAIN_PAGE("go-to-main-page"),
    BOOK_CATALOG_PAGE("book-catalog-page"),
    GO_TO_BOOK_PAGE("go-to-book-page"),
    GO_TO_RESERVATION_PAGE("go-to-reservation-page"),
    ADD_RESERVATION("add-reservation"),
    LOG_OUT("log-out"),
    GO_TO_REGISTRATION_PAGE("go-to-registration-page"),
    GO_TO_AUTHENTICATION_PAGE("go-to-authentication-page"),
    REGISTRATION("registration"),
    AUTHENTICATION("authentication"),
    READER_LIST_PAGE("reader-list-page"),
    GO_TO_READER_PAGE("go-to-reader-page"),
    READER_ISSUANCE_OPERATION("reader-issuance-operation"),
    READER_RESERVATION_OPERATION("reader-reservation-operation"),
    BOOK_ISSUANCE_PAGE("book-issuance-page"),
    ADD_ISSUANCE("add-issuance"),
    BOOK_LIST_PAGE("book-list-page"),
    DELETE_BOOK("delete-book"),
    INSTANCE_PAGE("instance-page"),
    ADD_EDIT_INSTANCE("add-edit-instance"),
    DELETE_INSTANCE("delete-instance"),
    GO_TO_ADD_EDIT_BOOK_PAGE("go-to-add-edit-book-page"),
    ADD_EDIT_BOOK("add-edit-book"),
    USER_LIST_PAGE("user-list-page"),
    GO_TO_USER_PAGE("go-to-user-page"),
    GO_TO_EDIT_USER_PAGE("go-to-edit-user-page"),
    EDIT_USER("edit-user"),
    DELETE_RESERVATION("delete-reservation"),
    ADD_REVIEW("add-review"),
    GO_TO_NEW_BOOK_CATALOG_PAGE("go-to-new-book-catalog-page"),
    GO_TO_POPULAR_BOOK_CATALOG_PAGE("go-to-popular-book-catalog-page"),
    GO_TO_ERROR_PAGE("go-to-error-page"),
    AUTHOR_PAGE("author-page"),
    GENRE_PAGE("genre-page"),
    PUBLISHER_PAGE("publisher-page"),
    TYPE_PAGE("type-page"),
    ADD_EDIT_AUTHOR("add-edit-author"),
    ADD_EDIT_GENRE("add-edit-genre"),
    ADD_EDIT_PUBLISHER("add-edit-publisher"),
    ADD_EDIT_TYPE("add-edit-type"),
    DELETE_AUTHOR("delete-author"),
    DELETE_GENRE("delete-genre"),
    DELETE_PUBLISHER("delete-publisher"),
    DELETE_TYPE("delete-type"),
    GO_TO_RULE_PAGE("go-to-rule-page"),
    GO_TO_CONTACT_PAGE("go-to-contact-page");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }

    public static boolean containsCommandName(String commandName) {
        for (CommandName command : CommandName.values()) {
            if (command.commandName.equals(commandName)) {
                return true;
            }
        }
        return false;
    }
}
