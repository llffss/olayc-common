package com.olayc.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @author jun
 * @date 2018/8/1 13:57
 * @company olasharing.com
 */
public abstract class BaseTxService {

    @Autowired
    protected DataSourceTransactionManager txManager;

    private static final ThreadLocal<TransactionStatus> TRANSACTIONSTATUSCONTEXT = new ThreadLocal<>();

    /**
     * 默认事务级别 TransactionDefinition.PROPAGATION_REQUIRED
     *
     * @return
     */
    protected TransactionStatus startTransactionStatus() {
        TransactionStatus transactionStatus = startTransactionStatus(TransactionDefinition.PROPAGATION_REQUIRED);
        return transactionStatus;
    }

    /**
     * @param transactionType
     * @return
     */
    protected TransactionStatus startTransactionStatus(int transactionType) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(transactionType);
        TransactionStatus transaction = txManager.getTransaction(def);
        TRANSACTIONSTATUSCONTEXT.set(transaction);
        return transaction;
    }

    /**
     * 事务提交
     */
    protected void commit() {
        TransactionStatus transactionStatus = TRANSACTIONSTATUSCONTEXT.get();
        TRANSACTIONSTATUSCONTEXT.remove();
        if (transactionStatus != null && !transactionStatus.isCompleted()) {
            txManager.commit(transactionStatus);
        }
    }

    /**
     * 事务回滚
     */
    protected void rollback() {
        TransactionStatus transactionStatus = TRANSACTIONSTATUSCONTEXT.get();
        TRANSACTIONSTATUSCONTEXT.remove();
        if (transactionStatus != null && !transactionStatus.isCompleted()) {
            txManager.rollback(transactionStatus);
        }
    }
}
