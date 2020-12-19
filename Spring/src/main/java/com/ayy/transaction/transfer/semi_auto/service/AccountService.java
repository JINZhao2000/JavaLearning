package com.ayy.transaction.transfer.semi_auto.service;

public interface AccountService {
    void transfer(String payer, String payee, Integer money);
}
