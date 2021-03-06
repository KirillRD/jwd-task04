package com.epam.library.controller.command;

import com.epam.library.controller.constant.CommandName;
import com.epam.library.controller.command.impl.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to get the object of the class that implements the {@link com.epam.library.controller.command.Command} interface by method name
 */
public final class CommandProvider {

    private static final Map<String, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.GO_TO_MAIN_PAGE.getCommandName(), new GoToMainPage());
        commands.put(CommandName.CHANGE_LOCALE.getCommandName(), new ChangeLocale());
        commands.put(CommandName.BOOK_CATALOG_PAGE.getCommandName(), new BookCatalogPage());
        commands.put(CommandName.GO_TO_BOOK_PAGE.getCommandName(), new GoToBookPage());
        commands.put(CommandName.GO_TO_RESERVATION_PAGE.getCommandName(), new GoToReservationPage());
        commands.put(CommandName.ADD_RESERVATION.getCommandName(), new AddReservation());
        commands.put(CommandName.LOG_OUT.getCommandName(), new LogOut());
        commands.put(CommandName.GO_TO_REGISTRATION_PAGE.getCommandName(), new GoToRegistrationPage());
        commands.put(CommandName.GO_TO_AUTHENTICATION_PAGE.getCommandName(), new GoToAuthenticationPage());
        commands.put(CommandName.REGISTRATION.getCommandName(), new Registration());
        commands.put(CommandName.AUTHENTICATION.getCommandName(), new Authentication());
        commands.put(CommandName.READER_LIST_PAGE.getCommandName(), new ReaderListPage());
        commands.put(CommandName.GO_TO_READER_PAGE.getCommandName(), new GoToReaderPage());
        commands.put(CommandName.READER_ISSUANCE_OPERATION.getCommandName(), new ReaderIssuanceOperation());
        commands.put(CommandName.READER_RESERVATION_OPERATION.getCommandName(), new ReaderReservationOperation());
        commands.put(CommandName.BOOK_ISSUANCE_PAGE.getCommandName(), new BookIssuancePage());
        commands.put(CommandName.ADD_ISSUANCE.getCommandName(), new AddIssuance());
        commands.put(CommandName.BOOK_LIST_PAGE.getCommandName(), new BookListPage());
        commands.put(CommandName.DELETE_BOOK.getCommandName(), new DeleteBook());
        commands.put(CommandName.INSTANCE_PAGE.getCommandName(), new InstancePage());
        commands.put(CommandName.ADD_EDIT_INSTANCE.getCommandName(), new AddEditInstance());
        commands.put(CommandName.DELETE_INSTANCE.getCommandName(), new DeleteInstance());
        commands.put(CommandName.GO_TO_ADD_EDIT_BOOK_PAGE.getCommandName(), new GoToAddEditBookPage());
        commands.put(CommandName.ADD_EDIT_BOOK.getCommandName(), new AddEditBook());
        commands.put(CommandName.USER_LIST_PAGE.getCommandName(), new UserListPage());
        commands.put(CommandName.GO_TO_USER_PAGE.getCommandName(), new GoToUserPage());
        commands.put(CommandName.GO_TO_EDIT_USER_PAGE.getCommandName(), new GoToEditUserPage());
        commands.put(CommandName.EDIT_USER.getCommandName(), new EditUser());
        commands.put(CommandName.DELETE_RESERVATION.getCommandName(), new DeleteReservation());
        commands.put(CommandName.ADD_REVIEW.getCommandName(), new AddReview());
        commands.put(CommandName.GO_TO_NEW_BOOK_CATALOG_PAGE.getCommandName(), new GoToNewBookCatalogPage());
        commands.put(CommandName.GO_TO_POPULAR_BOOK_CATALOG_PAGE.getCommandName(), new GoToPopularBookCatalogPage());
        commands.put(CommandName.GO_TO_ERROR_PAGE.getCommandName(), new GoToErrorPage());
        commands.put(CommandName.AUTHOR_PAGE.getCommandName(), new AuthorPage());
        commands.put(CommandName.GENRE_PAGE.getCommandName(), new GenrePage());
        commands.put(CommandName.PUBLISHER_PAGE.getCommandName(), new PublisherPage());
        commands.put(CommandName.TYPE_PAGE.getCommandName(), new TypePage());
        commands.put(CommandName.ADD_EDIT_AUTHOR.getCommandName(), new AddEditAuthor());
        commands.put(CommandName.ADD_EDIT_GENRE.getCommandName(), new AddEditGenre());
        commands.put(CommandName.ADD_EDIT_PUBLISHER.getCommandName(), new AddEditPublisher());
        commands.put(CommandName.ADD_EDIT_TYPE.getCommandName(), new AddEditType());
        commands.put(CommandName.DELETE_AUTHOR.getCommandName(), new DeleteAuthor());
        commands.put(CommandName.DELETE_GENRE.getCommandName(), new DeleteGenre());
        commands.put(CommandName.DELETE_PUBLISHER.getCommandName(), new DeletePublisher());
        commands.put(CommandName.DELETE_TYPE.getCommandName(), new DeleteType());
        commands.put(CommandName.GO_TO_RULE_PAGE.getCommandName(), new GoToRulePage());
        commands.put(CommandName.GO_TO_CONTACT_PAGE.getCommandName(), new GoToContactPage());
        commands.put(CommandName.LOCK_USER.getCommandName(), new LockUser());
    }

    public final Command getCommand(String command) {
        return commands.get(command);
    }
}
