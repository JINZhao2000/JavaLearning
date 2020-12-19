package com.ayy.transaction.transfer.manuel.service;

public interface AccountService {
    void transfer(String payer, String payee, Integer money);
}
