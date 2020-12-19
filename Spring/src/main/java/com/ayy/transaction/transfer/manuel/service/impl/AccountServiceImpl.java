package com.ayy.transaction.transfer.manuel.service.impl;

import com.ayy.transaction.transfer.manuel.dao.AccountDao;
import com.ayy.transaction.transfer.manuel.service.AccountService;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @ ClassName AccountServiceImpl
 * @ Description realization of interface AccountService
 * @ Author Zhao JIN
 * @ Date 03/11/2020 15:18
 * @ Version 1.0
 */
public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao;
    private TransactionTemplate transactionTemplate;

    public void setAccountDao (AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setTransactionTemplate (TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public void transfer (String payer, String payee, Integer money) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult (TransactionStatus status) {
                accountDao.out(payer, money);
                // simulation of power off
                // int i= 1/0;
                accountDao.in(payee, money);
            }
        });
    }
}
