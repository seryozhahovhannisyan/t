package com.connectto.wallet.test;

import com.connectto.general.exception.InternalErrorException;
import com.connectto.general.exception.InvalidParameterException;
import com.connectto.general.exception.PermissionDeniedException;
import com.connectto.general.exception.UnsupportedCurrencyException;
import com.connectto.wallet.model.transaction.sendmoney.TransactionSendMoney;
import com.connectto.wallet.model.wallet.ExchangeRate;
import com.connectto.wallet.model.wallet.lcp.CurrencyType;
import com.connectto.wallet.util.DemoModel;
import com.connectto.wallet.util.TransactionSendMoneyDemo;

/**
 * Created by Serozh on 3/30/2017.
 */
public class SendMoneyTest {

    public static void main(String[] args) throws InvalidParameterException, InternalErrorException, PermissionDeniedException, UnsupportedCurrencyException {
        ExchangeRate selectedExchangeRate = DemoModel.initExchangeRate(480d, CurrencyType.USD, CurrencyType.AMD);
        ExchangeRate selectedExchangeRate2 = DemoModel.initExchangeRate(1 / 480d, CurrencyType.USD, CurrencyType.AMD);
        //Double productAmount,  productCurrencyType,    from,   to,   setup
//        TransactionSendMoney transaction1 = TransactionSendMoneyDemo.initTransaction(null, 100d, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD);
//        print(transaction1);

//        TransactionSendMoney transaction2 = TransactionSendMoneyDemo.initTransaction(selectedExchangeRate, 480 * 100d, CurrencyType.AMD, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD);
//        print(transaction2);

//        TransactionSendMoney transaction3 = TransactionSendMoneyDemo.initTransaction(selectedExchangeRate, 100d, CurrencyType.USD, CurrencyType.AMD, CurrencyType.USD, CurrencyType.USD);
//        print(transaction3);

//        TransactionSendMoney transaction4 = TransactionSendMoneyDemo.initTransaction(selectedExchangeRate, 100d, CurrencyType.USD, CurrencyType.USD, CurrencyType.AMD, CurrencyType.USD);
//        print(transaction4);

        TransactionSendMoney transaction5 = TransactionSendMoneyDemo.initTransaction(selectedExchangeRate, 56 * 100d, CurrencyType.RUB, CurrencyType.AMD, CurrencyType.AMD, CurrencyType.USD);
        print(transaction5);

//
//        TransactionSendMoney transaction3 = TransactionSendMoneyDemo.initTransaction(selectedExchangeRate, 100d, CurrencyType.USD, CurrencyType.AMD, CurrencyType.USD, CurrencyType.USD);
//        TransactionSendMoney transaction6 = TransactionSendMoneyDemo.initTransaction(selectedExchangeRate2, 480 * 100d, CurrencyType.AMD, CurrencyType.USD, CurrencyType.USD, CurrencyType.AMD);
//
//
//        TransactionSendMoney transaction5 = TransactionSendMoneyDemo.initTransaction(selectedExchangeRate2, 100d, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD, CurrencyType.AMD);
//        TransactionSendMoney transaction7 = TransactionSendMoneyDemo.initTransaction(selectedExchangeRate, 480 * 100d, CurrencyType.AMD, CurrencyType.AMD, CurrencyType.USD, CurrencyType.USD);

//        print(transaction1, transaction2, transaction3, transaction4, transaction5);
//        print(transaction2, transaction4);
    }

    private static void print(TransactionSendMoney transaction1, TransactionSendMoney transaction2, TransactionSendMoney transaction3, TransactionSendMoney transaction4, TransactionSendMoney transaction5) {
        System.out.println("All");
        System.out.println("1  USD USD USD " + transaction1);
        System.out.println("2  AMD USD USD " + transaction2);
        System.out.println("3  USD AMD USD " + transaction3);
        System.out.println("4  USD AMD AMD " + transaction4);
        System.out.println("5  USD USD AMD " + transaction5);
        System.out.println("getProcessStart");
//        System.out.println("1  USD USD USD " + transaction1.getProcessStart());
//        System.out.println("2  AMD USD USD " + transaction2.getProcessStart());
//        System.out.println("3  USD AMD USD " + transaction3.getProcessStart());
//        System.out.println("4  USD AMD AMD " + transaction4.getProcessStart());
//        System.out.println("5  USD USD AMD " + transaction5.getProcessStart());
        System.out.println("getTax");
        System.out.println("1  USD USD USD " + transaction1.getTax());
        System.out.println("2  AMD USD USD " + transaction2.getTax());
        System.out.println("3  USD AMD USD " + transaction3.getTax());
        System.out.println("4  USD AMD AMD " + transaction4.getTax());
        System.out.println("5  USD USD AMD " + transaction5.getTax());
        System.out.println("getProcessTax");
//        System.out.println("1  USD USD USD " + transaction1.getTax().getProcessTax());
//        System.out.println("2  AMD USD USD " + transaction2.getTax().getProcessTax());
//        System.out.println("3  USD AMD USD " + transaction3.getTax().getProcessTax());
//        System.out.println("4  USD AMD AMD " + transaction4.getTax().getProcessTax());
//        System.out.println("5  USD USD AMD " + transaction5.getTax().getProcessTax());
//        System.out.println("getExchangeTax");
//        System.out.println("1  USD USD USD " + transaction1.getTax().getExchangeTax());
//        System.out.println("2  AMD USD USD " + transaction2.getTax().getExchangeTax());
//        System.out.println("3  USD AMD USD " + transaction3.getTax().getExchangeTax());
//        System.out.println("4  USD AMD AMD " + transaction4.getTax().getExchangeTax());
//        System.out.println("5  USD USD AMD " + transaction5.getTax().getExchangeTax());
//
//
//        System.out.println("getProcessTax.getExchange");
//        System.out.println("1  USD USD USD " + transaction1.getTax().getProcessTax().getExchange());
//        System.out.println("2  AMD USD USD " + transaction2.getTax().getProcessTax().getExchange());
//        System.out.println("3  USD AMD USD " + transaction3.getTax().getProcessTax().getExchange());
//        System.out.println("4  USD AMD USD " + transaction4.getTax().getProcessTax().getExchange());

    }

    private static void print(TransactionSendMoney transaction1, TransactionSendMoney transaction2) {
        System.out.println("All");
        System.out.println("1 " + transaction1);
        System.out.println("2 " + transaction2);
        System.out.println("getFromTransactionProcess");
        System.out.println("1   " + transaction1.getFromTransactionProcess());
        System.out.println("2   " + transaction2.getFromTransactionProcess());
        System.out.println("getTax");
        System.out.println("1   " + transaction1.getTax());
        System.out.println("2   " + transaction2.getTax());
        System.out.println("getFromProcessTax");
        System.out.println("1   " + transaction1.getTax().getFromProcessTax());
        System.out.println("2   " + transaction2.getTax().getFromProcessTax());
        System.out.println("getFromExchangeTax");
        System.out.println("1   " + transaction1.getTax().getFromExchangeTax());
        System.out.println("2   " + transaction2.getTax().getFromExchangeTax());
//
//
        System.out.println("getFromProcessTax.getExchange");
        System.out.println("1   " + transaction1.getTax().getFromProcessTax().getExchange());
        System.out.println("2   " + transaction2.getTax().getFromProcessTax().getExchange());

    }

    private static void print(TransactionSendMoney transaction1) {
        System.out.println("All");
        System.out.println("1 " + transaction1);
        System.out.println("TransactionProcesses");
        System.out.println("f   " + transaction1.getFromTransactionProcess());
        System.out.println("t   " + transaction1.getToTransactionProcess());
        System.out.println("getTax");
        System.out.println("1   " + transaction1.getTax());
        System.out.println("getTaxes");
        System.out.println("f   " + transaction1.getTax().getFromProcessTax());
        System.out.println("t   " + transaction1.getTax().getToProcessTax());
        System.out.println("getExchangeTaxes");
        System.out.println("f   " + transaction1.getTax().getFromExchangeTax());
        System.out.println("t   " + transaction1.getTax().getToExchangeTax());

//
//
        System.out.println("getProcessTaxes.getExchanges");
        System.out.println("f   " + transaction1.getTax().getFromProcessTax().getExchange());
        System.out.println("f   " + transaction1.getTax().getToProcessTax().getExchange());

    }
}
