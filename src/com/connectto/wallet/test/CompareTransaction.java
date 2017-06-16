package com.connectto.wallet.test;

import com.connectto.general.exception.InternalErrorException;
import com.connectto.general.exception.InvalidParameterException;
import com.connectto.general.exception.PermissionDeniedException;
import com.connectto.general.exception.UnsupportedCurrencyException;
import com.connectto.wallet.model.transaction.purchase.TransactionPurchase;
import com.connectto.wallet.model.transaction.request.TransactionRequest;
import com.connectto.wallet.model.transaction.sendmoney.TransactionSendMoney;
import com.connectto.wallet.model.wallet.ExchangeRate;
import com.connectto.wallet.model.wallet.lcp.CurrencyType;
import com.connectto.wallet.util.DemoModel;
import com.connectto.wallet.util.TransactionRequestDemo;
import com.connectto.wallet.util.TransactionSendMoneyDemo;

/**
 * Created by Serozh on 6/15/2017.
 */
public class CompareTransaction {

    public static void main(String[] args) throws InvalidParameterException, InternalErrorException, PermissionDeniedException, UnsupportedCurrencyException {
        ExchangeRate selectedExchangeRate = DemoModel.initExchangeRate(480d, CurrencyType.USD, CurrencyType.AMD);
        ExchangeRate selectedExchangeRate2 = DemoModel.initExchangeRate(1 / 480d, CurrencyType.USD, CurrencyType.AMD);

//        TransactionSendMoney sendMoney1 = TransactionSendMoneyDemo.initTransaction(null, 100d, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD);
//        TransactionRequest request1 = TransactionRequestDemo.initDemoTransactionRequest(null, 100d, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD);
//        print(request1, sendMoney1);

//        TransactionSendMoney sendMoney2 = TransactionSendMoneyDemo.initTransaction(selectedExchangeRate, 480 * 100d, CurrencyType.AMD, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD);
//        TransactionRequest request2 = TransactionRequestDemo.initDemoTransactionRequest(selectedExchangeRate, 480 * 100d, CurrencyType.AMD, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD);
//        print(request2, sendMoney2);

//        TransactionSendMoney sendMoney3     = TransactionSendMoneyDemo.initTransaction(selectedExchangeRate, 100d, CurrencyType.USD, CurrencyType.AMD, CurrencyType.USD, CurrencyType.USD);
//        TransactionRequest request3= TransactionRequestDemo.initDemoTransactionRequest(selectedExchangeRate, 100d, CurrencyType.USD, CurrencyType.AMD, CurrencyType.USD, CurrencyType.USD);
//        print(request3, sendMoney3);

//        TransactionSendMoney sendMoney4     = TransactionSendMoneyDemo.initTransaction(selectedExchangeRate, 100d, CurrencyType.USD, CurrencyType.USD, CurrencyType.AMD, CurrencyType.USD);
//        TransactionRequest request4= TransactionRequestDemo.initDemoTransactionRequest(selectedExchangeRate, 100d, CurrencyType.USD, CurrencyType.USD, CurrencyType.AMD, CurrencyType.USD);
//        print(request4, sendMoney4);

//        TransactionSendMoney sendMoney5 = TransactionSendMoneyDemo.initTransaction(selectedExchangeRate, 56 * 100d, CurrencyType.RUB, CurrencyType.AMD, CurrencyType.AMD, CurrencyType.USD);
//        TransactionRequest request5 = TransactionRequestDemo.initDemoTransactionRequest(selectedExchangeRate, 56 * 100d, CurrencyType.RUB, CurrencyType.AMD, CurrencyType.AMD, CurrencyType.USD);
//        print(request5, sendMoney5);

        TransactionSendMoney sendMoney6 = TransactionSendMoneyDemo.initTransaction(selectedExchangeRate, 480 * 100d, CurrencyType.AMD, CurrencyType.AMD, CurrencyType.AMD, CurrencyType.USD);
        TransactionRequest request6 = TransactionRequestDemo.initDemoTransactionRequest(selectedExchangeRate, 480 * 100d, CurrencyType.AMD, CurrencyType.AMD, CurrencyType.AMD, CurrencyType.USD);
        print(request6, sendMoney6);
    }


    private static void print(TransactionPurchase purchase, TransactionSendMoney sendMoney) {
        System.out.println("All");
        System.out.println("1 " + purchase);
        System.out.println("2 " + sendMoney);
        System.out.println("getProcessStart");
        System.out.println("1   " + purchase.getProcessStart());
        System.out.println("2   " + sendMoney.getFromTransactionProcess());
        System.out.println("getTax");
        System.out.println("1   " + purchase.getTax());
        System.out.println("2   " + sendMoney.getTax());
        System.out.println("getProcessTax");
        System.out.println("1   " + purchase.getTax().getProcessTax());
        System.out.println("2   " + sendMoney.getTax().getFromProcessTax());
        System.out.println("getExchangeTax");
        System.out.println("1   " + purchase.getTax().getExchangeTax());
        System.out.println("2   " + sendMoney.getTax().getFromExchangeTax());


        System.out.println("getProcessTax.getExchange");
        System.out.println("1   " + purchase.getTax().getProcessTax().getExchange());
        System.out.println("2   " + sendMoney.getTax().getFromProcessTax().getExchange());

    }

    private static void print(TransactionRequest request, TransactionSendMoney sendMoney) {
        System.out.println("A All");
        System.out.println("1 " + request);
        System.out.println("2 " + sendMoney);
        System.out.println("B FROM");
        System.out.println("1   " + request.getFromTransactionProcess());
        System.out.println("2   " + sendMoney.getFromTransactionProcess());
        System.out.println("C TO");
        System.out.println("1   " + request.getToTransactionProcess());
        System.out.println("2   " + sendMoney.getToTransactionProcess());
        System.out.println("D getTax");
        System.out.println("1   " + request.getTax());
        System.out.println("2   " + sendMoney.getTax());
        System.out.println("E getFromProcessTax");
        System.out.println("1   " + request.getTax().getFromProcessTax());
        System.out.println("2   " + sendMoney.getTax().getFromProcessTax());
        System.out.println("F getToProcessTax");
        System.out.println("1   " + request.getTax().getToProcessTax());
        System.out.println("2   " + sendMoney.getTax().getToProcessTax());
        System.out.println("G getFromExchangeTax");
        System.out.println("1   " + request.getTax().getFromExchangeTax());
        System.out.println("2   " + sendMoney.getTax().getFromExchangeTax());
        System.out.println("H getToExchangeTax");
        System.out.println("1   " + request.getTax().getToExchangeTax());
        System.out.println("2   " + sendMoney.getTax().getToExchangeTax());
        System.out.println("K getFromProcessTax.getExchange");
        System.out.println("1   " + request.getTax().getFromProcessTax().getExchange());
        System.out.println("2   " + sendMoney.getTax().getFromProcessTax().getExchange());
        System.out.println("L getFromProcessTax.getExchange");
        System.out.println("1   " + request.getTax().getToProcessTax().getExchange());
        System.out.println("2   " + sendMoney.getTax().getToProcessTax().getExchange());

    }
}
