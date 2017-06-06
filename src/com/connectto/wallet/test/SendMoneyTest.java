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
        //purchaseAmount, purchaseCurrencyType,  from, CurrencyType setup
        TransactionSendMoney purchase1 = TransactionSendMoneyDemo.initTransaction(null, 100d, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD);

        TransactionSendMoney purchase2 = TransactionSendMoneyDemo.initTransaction(selectedExchangeRate, 480 * 100d, CurrencyType.AMD, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD);
        TransactionSendMoney purchase4 = TransactionSendMoneyDemo.initTransaction(selectedExchangeRate2, 100d, CurrencyType.USD, CurrencyType.AMD, CurrencyType.USD, CurrencyType.AMD);

        TransactionSendMoney purchase3 = TransactionSendMoneyDemo.initTransaction(selectedExchangeRate, 100d, CurrencyType.USD, CurrencyType.AMD, CurrencyType.USD, CurrencyType.USD);
        TransactionSendMoney purchase6 = TransactionSendMoneyDemo.initTransaction(selectedExchangeRate2, 480 * 100d, CurrencyType.AMD, CurrencyType.USD, CurrencyType.USD, CurrencyType.AMD);


        TransactionSendMoney purchase5 = TransactionSendMoneyDemo.initTransaction(selectedExchangeRate2, 100d, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD, CurrencyType.AMD);
        TransactionSendMoney purchase7 = TransactionSendMoneyDemo.initTransaction(selectedExchangeRate, 480 * 100d, CurrencyType.AMD, CurrencyType.AMD, CurrencyType.USD, CurrencyType.USD);

//        print(purchase1, purchase2, purchase3, purchase4, purchase5);
        print(purchase2, purchase4);
    }

    private static void print(TransactionSendMoney purchase1, TransactionSendMoney purchase2, TransactionSendMoney purchase3, TransactionSendMoney purchase4, TransactionSendMoney purchase5) {
        System.out.println("All");
        System.out.println("1  USD USD USD " + purchase1);
        System.out.println("2  AMD USD USD " + purchase2);
        System.out.println("3  USD AMD USD " + purchase3);
        System.out.println("4  USD AMD AMD " + purchase4);
        System.out.println("5  USD USD AMD " + purchase5);
        System.out.println("getProcessStart");
//        System.out.println("1  USD USD USD " + purchase1.getProcessStart());
//        System.out.println("2  AMD USD USD " + purchase2.getProcessStart());
//        System.out.println("3  USD AMD USD " + purchase3.getProcessStart());
//        System.out.println("4  USD AMD AMD " + purchase4.getProcessStart());
//        System.out.println("5  USD USD AMD " + purchase5.getProcessStart());
        System.out.println("getTax");
        System.out.println("1  USD USD USD " + purchase1.getTax());
        System.out.println("2  AMD USD USD " + purchase2.getTax());
        System.out.println("3  USD AMD USD " + purchase3.getTax());
        System.out.println("4  USD AMD AMD " + purchase4.getTax());
        System.out.println("5  USD USD AMD " + purchase5.getTax());
        System.out.println("getProcessTax");
//        System.out.println("1  USD USD USD " + purchase1.getTax().getProcessTax());
//        System.out.println("2  AMD USD USD " + purchase2.getTax().getProcessTax());
//        System.out.println("3  USD AMD USD " + purchase3.getTax().getProcessTax());
//        System.out.println("4  USD AMD AMD " + purchase4.getTax().getProcessTax());
//        System.out.println("5  USD USD AMD " + purchase5.getTax().getProcessTax());
//        System.out.println("getExchangeTax");
//        System.out.println("1  USD USD USD " + purchase1.getTax().getExchangeTax());
//        System.out.println("2  AMD USD USD " + purchase2.getTax().getExchangeTax());
//        System.out.println("3  USD AMD USD " + purchase3.getTax().getExchangeTax());
//        System.out.println("4  USD AMD AMD " + purchase4.getTax().getExchangeTax());
//        System.out.println("5  USD USD AMD " + purchase5.getTax().getExchangeTax());
//
//
//        System.out.println("getProcessTax.getExchange");
//        System.out.println("1  USD USD USD " + purchase1.getTax().getProcessTax().getExchange());
//        System.out.println("2  AMD USD USD " + purchase2.getTax().getProcessTax().getExchange());
//        System.out.println("3  USD AMD USD " + purchase3.getTax().getProcessTax().getExchange());
//        System.out.println("4  USD AMD USD " + purchase4.getTax().getProcessTax().getExchange());

    }

    private static void print(TransactionSendMoney purchase1, TransactionSendMoney purchase2) {
        System.out.println("All");
        System.out.println("1 " + purchase1);
        System.out.println("2 " + purchase2);
        System.out.println("getFromTransactionProcess");
        System.out.println("1   " + purchase1.getFromTransactionProcess());
        System.out.println("2   " + purchase2.getFromTransactionProcess());
        System.out.println("getTax");
        System.out.println("1   " + purchase1.getTax());
        System.out.println("2   " + purchase2.getTax());
        System.out.println("getFromProcessTax");
        System.out.println("1   " + purchase1.getTax().getFromProcessTax());
        System.out.println("2   " + purchase2.getTax().getFromProcessTax());
        System.out.println("getFromExchangeTax");
        System.out.println("1   " + purchase1.getTax().getFromExchangeTax());
        System.out.println("2   " + purchase2.getTax().getFromExchangeTax());
//
//
        System.out.println("getFromProcessTax.getExchange");
        System.out.println("1   " + purchase1.getTax().getFromProcessTax().getExchange());
        System.out.println("2   " + purchase2.getTax().getFromProcessTax().getExchange());

    }


}
