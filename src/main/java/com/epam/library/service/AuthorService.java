package com.epam.library.service;

import com.epam.library.entity.book.Author;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

public interface AuthorService {
    List<Author> getAuthorsList() throws ServiceException;
}
