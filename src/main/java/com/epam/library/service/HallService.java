package com.epam.library.service;

import com.epam.library.entity.instance.hall.Hall;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

/**
 * Hall service interface
 */
public interface HallService {
    /**
     * Returns list with the halls' data
     * @return list with the halls' data
     * @throws ServiceException if a data processing error occurs
     */
    List<Hall> getHallList() throws ServiceException;
}
