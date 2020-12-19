package com.ayy.transaction.transfer.original.service;

public interface AccountService {
    void transfer(String payer, String payee, Integer money);
}
