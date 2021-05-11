package com.ayy.service.impl;

import com.ayy.bean.Dispatcher;
import com.ayy.dao.DispatcherDao;
import com.ayy.service.DispatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/05/2021
 * @ Version 1.0
 */
@Service
public class DispatcherServiceImpl implements DispatcherService {
    @Autowired
    private DispatcherDao dispatcherDao;

    @Override
    public void sendDispacher(Dispatcher dispatcher) {
        dispatcherDao.insert(dispatcher);
    }
}
