package com.epam.library.service;

import com.epam.library.entity.instance.hall.Hall;
import com.epam.library.service.exception.ServiceException;

import java.util.List;

public interface HallService {
    List<Hall> getHallList() throws ServiceException;
}
