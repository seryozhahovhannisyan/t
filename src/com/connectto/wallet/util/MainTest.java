package com.connectto.wallet.util;

import com.connectto.general.exception.InternalErrorException;
import com.connectto.general.exception.InvalidParameterException;
import com.connectto.general.exception.PermissionDeniedException;
import com.connectto.wallet.model.transaction.purchase.TransactionPurchase;
import com.connectto.wallet.model.transaction.sendmoney.TransactionSendMoney;
import com.connectto.wallet.model.transaction.sendmoney.TransactionSendMoneyProcess;
import com.connectto.wallet.model.wallet.lcp.CurrencyType;

/**
 * Created by Serozh on 3/30/2017.
 */
public class MainTest {

    public static void main(String[] args) throws InvalidParameterException, InternalErrorException, PermissionDeniedException {

//        TransactionSendMoney sendMoney = TransactionSendMoneyDemo.initDemoTransactionSendMoney(100d, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD);
//        TransactionPurchase purchase = TransactionPurchaseDemo.initTransaction(100d, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD);
//        print(sendMoney, purchase);

        TransactionSendMoney one = TransactionSendMoneyDemo.initDemoTransactionSendMoney(100d, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD);
        TransactionSendMoney from = TransactionSendMoneyDemo.initDemoTransactionSendMoney(100d, CurrencyType.USD, CurrencyType.AMD, CurrencyType.USD, CurrencyType.USD);
        TransactionSendMoney to = TransactionSendMoneyDemo.initDemoTransactionSendMoney(100d, CurrencyType.USD, CurrencyType.AMD, CurrencyType.AMD, CurrencyType.USD);

        System.out.println("one  " + one.getFromTransactionProcess());
        System.out.println("one  " + one.getToTransactionProcess());
        System.out.println("----");
        System.out.println("from " + from.getFromTransactionProcess());
        System.out.println("from " + from.getToTransactionProcess());
        System.out.println("----");
        System.out.println("to   " + to.getFromTransactionProcess());
        System.out.println("to   " + to.getToTransactionProcess());
        System.out.println("***************************************");

        System.out.println("one  " + one.getTax());
        System.out.println("from " + from.getTax());
        System.out.println("to   " + to.getTax());
        System.out.println("***************************************");

        System.out.println("one  " + one);
        System.out.println("from " + from);
        System.out.println("to   " + to);
//        TransactionPurchase transactionPurchase = TransactionPurchaseDemo.initTransaction(100d, CurrencyType.USD, CurrencyType.AMD, CurrencyType.USD);
//
//        print(from, transactionPurchase);
    }

    private static void print(TransactionSendMoney sendMoney) {
        TransactionSendMoneyProcess process = sendMoney.getFromTransactionProcess();
        System.out.println("process");
        System.out.println(process);
        System.out.println("sendMoney");
        System.out.println(sendMoney);
    }

    private static void print(TransactionSendMoney sendMoney, TransactionPurchase purchase) {
        System.out.println("All");
        System.out.println(sendMoney);
        System.out.println(purchase);
        System.out.println("TAX");
        System.out.println(sendMoney.getTax());
        System.out.println(purchase.getTax());
        System.out.println("TAX getFromProcessTax getProcessTax");
        System.out.println(sendMoney.getTax().getFromProcessTax());
        System.out.println(purchase.getTax().getProcessTax());
    }
}
