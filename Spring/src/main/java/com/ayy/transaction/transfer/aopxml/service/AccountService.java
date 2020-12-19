package com.ayy.transaction.transfer.aopxml.service;

public interface AccountService {
    void transfer(String payer, String payee, Integer money);
}
