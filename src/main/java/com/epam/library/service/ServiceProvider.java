package com.epam.library.service;

import com.epam.library.service.impl.*;

public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();

    private final UserService userService = new UserServiceImpl();
    private final BookService bookService = new BookServiceImpl();
    private final PublisherService publisherService = new PublisherServiceImpl();
    private final TypeService typeService = new TypeServiceImpl();
    private final AuthorService authorService = new AuthorServiceImpl();
    private final GenreService genreService = new GenreServiceImpl();
    private final BookCatalogService bookCatalogService = new BookCatalogServiceImpl();
    private final BookReviewService bookReviewService = new BookReviewServiceImpl();
    private final ReaderService readerService = new ReaderServiceImpl();
    private final InstanceService instanceService = new InstanceServiceImpl();
    private final HallService hallService = new HallServiceImpl();
    private final IssuanceService issuanceService = new IssuanceServiceImpl();
    private final ReservationService reservationService = new ReservationServiceImpl();
    private final ReviewService reviewService = new ReviewServiceImpl();

    private ServiceProvider() {}

    public static ServiceProvider getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public BookService getBookService() {
        return bookService;
    }

    public PublisherService getPublisherService() {
        return publisherService;
    }

    public TypeService getTypeService() {
        return typeService;
    }

    public AuthorService getAuthorService() {
        return authorService;
    }

    public GenreService getGenreService() {
        return genreService;
    }

    public BookCatalogService getBookCatalogService() {
        return bookCatalogService;
    }

    public BookReviewService getBookReviewService() {
        return bookReviewService;
    }

    public ReaderService getReaderService() {
        return readerService;
    }

    public InstanceService getInstanceService() {
        return instanceService;
    }

    public HallService getHallService() {
        return hallService;
    }

    public IssuanceService getIssuanceService() {
        return issuanceService;
    }

    public ReservationService getReservationService() {
        return reservationService;
    }

    public ReviewService getReviewService() {
        return reviewService;
    }
}
