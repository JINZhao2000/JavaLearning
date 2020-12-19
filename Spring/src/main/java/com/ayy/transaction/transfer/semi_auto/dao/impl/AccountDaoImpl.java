package com.ayy.transaction.transfer.semi_auto.dao.impl;

import com.ayy.transaction.transfer.semi_auto.dao.AccountDao;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * @ ClassName AccountDaoImpl
 * @ Description realization of interface AccountDao
 * @ Author Zhao JIN
 * @ Date 03/11/2020 15:11
 * @ Version 1.0
 */
public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao {
    @Override
    public void out (String payer, Integer money) {
        String sql = "update account set money = money - ? where username = ?";
        this.getJdbcTemplate().update(sql,money,payer);
    }

    @Override
    public void in (String payee, Integer money) {
        String sql = "update account set money = money + ? where username = ?";
        this.getJdbcTemplate().update(sql,money,payee);
    }
}
