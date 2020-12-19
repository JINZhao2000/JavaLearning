package com.ayy.transaction.transfer.aopanno.service;

public interface AccountService {
    void transfer(String payer, String payee, Integer money);
}
