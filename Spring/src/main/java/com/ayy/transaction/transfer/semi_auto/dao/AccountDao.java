package com.ayy.transaction.transfer.semi_auto.dao;

public interface AccountDao {
    /**
     * remit
     * @param payer user payer
     * @param money money transferred
     */
    void out(String payer, Integer money);

    /**
     * receive
     * @param payee user payee
     * @param money money received
     */
    void in(String payee, Integer money);
}

