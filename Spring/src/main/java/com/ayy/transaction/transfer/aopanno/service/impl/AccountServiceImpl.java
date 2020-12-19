package com.ayy.transaction.transfer.aopanno.service.impl;

import com.ayy.transaction.transfer.aopanno.dao.AccountDao;
import com.ayy.transaction.transfer.aopanno.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ ClassName AccountServiceImpl
 * @ Description realization of interface AccountService
 * @ Author Zhao JIN
 * @ Date 03/11/2020 15:18
 * @ Version 1.0
 */
// @Transactional
@Service
public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao;

    @Autowired
    @Qualifier("accountDao")
    public void setAccountDao (AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT)
    public void transfer (String payer, String payee, Integer money) {
        accountDao.out(payer, money);
        // simulation of power off
        // int i= 1/0;
        accountDao.in(payee, money);
    }
}
