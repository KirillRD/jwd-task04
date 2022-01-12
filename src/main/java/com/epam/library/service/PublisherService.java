package com.epam.library.service;

import com.epam.library.entity.book.Publisher;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

public interface PublisherService {
    List<Publisher> getPublishersList() throws ServiceException;
}
