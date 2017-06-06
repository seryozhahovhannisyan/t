package com.connectto.wallet.test;

import com.connectto.general.exception.InternalErrorException;
import com.connectto.general.exception.InvalidParameterException;
import com.connectto.general.exception.PermissionDeniedException;
import com.connectto.general.exception.UnsupportedCurrencyException;
import com.connectto.wallet.model.transaction.purchase.TransactionPurchase;
import com.connectto.wallet.model.transaction.request.TransactionRequest;
import com.connectto.wallet.model.transaction.sendmoney.TransactionSendMoney;
import com.connectto.wallet.model.transaction.sendmoney.TransactionSendMoneyProcess;
import com.connectto.wallet.model.wallet.lcp.CurrencyType;
import com.connectto.wallet.util.TransactionPurchaseDemo;
import com.connectto.wallet.util.TransactionRequestDemo;
import com.connectto.wallet.util.TransactionSendMoneyDemo;

/**
 * Created by Serozh on 3/30/2017.
 */
public class MainTest {

    public static void main(String[] args) throws InvalidParameterException, InternalErrorException, PermissionDeniedException, UnsupportedCurrencyException {
                                                                                        //productAmo, productType,       from,            to, CurrencyType setup ,
        TransactionSendMoney sendMoney = TransactionSendMoneyDemo.initDemoTransactionSendMoney(100d, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD);
        TransactionRequest request     = TransactionRequestDemo.initDemoTransactionRequest    (100d, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD);
        TransactionPurchase purchase   = TransactionPurchaseDemo.initTransaction              (null, 100d, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD);


//
        print(sendMoney, request, purchase);
    }

    private static void print(TransactionSendMoney sendMoney, TransactionRequest request, TransactionPurchase purchase) {
        System.out.println("All");
        System.out.println(sendMoney);
        System.out.println(request);
        System.out.println(purchase);
        System.out.println("TAX");
        System.out.println(sendMoney.getTax());
        System.out.println(request.getTax());
        System.out.println(purchase.getTax());
        System.out.println("TAX getFromProcessTax getProcessTax");
        System.out.println(sendMoney.getTax().getFromProcessTax());
        System.out.println(request.getTax().getFromProcessTax());
        System.out.println(sendMoney.getTax().getToProcessTax());
        System.out.println(request.getTax().getToProcessTax());
        System.out.println(purchase.getTax().getProcessTax());
    }
}
