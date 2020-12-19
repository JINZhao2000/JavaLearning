package com.ayy.transaction.transfer.aopxml.service.impl;

import com.ayy.transaction.transfer.aopxml.dao.AccountDao;
import com.ayy.transaction.transfer.aopxml.service.AccountService;

/**
 * @ ClassName AccountServiceImpl
 * @ Description realization of interface AccountService
 * @ Author Zhao JIN
 * @ Date 03/11/2020 15:18
 * @ Version 1.0
 */
public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao;

    public void setAccountDao (AccountDao accountDao) {
        this.accountDao = accountDao;
    }


    @Override
    public void transfer (String payer, String payee, Integer money) {
        accountDao.out(payer, money);
        // simulation of power off
        // int i= 1/0;
        accountDao.in(payee, money);
    }
}
