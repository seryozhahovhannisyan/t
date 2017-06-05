package com.connectto.wallet.test;

import com.connectto.general.exception.InternalErrorException;
import com.connectto.general.exception.InvalidParameterException;
import com.connectto.general.exception.PermissionDeniedException;
import com.connectto.general.exception.UnsupportedCurrencyException;
import com.connectto.wallet.model.transaction.purchase.TransactionPurchase;
import com.connectto.wallet.model.wallet.lcp.CurrencyType;
import com.connectto.wallet.util.TransactionPurchaseDemo;

/**
 * Created by Serozh on 3/30/2017.
 */
public class PurchaseTest {

    public static void main(String[] args) throws InvalidParameterException, InternalErrorException, PermissionDeniedException, UnsupportedCurrencyException {

        //purchaseAmount, purchaseCurrencyType,  from, CurrencyType setup
        TransactionPurchase purchase1 = TransactionPurchaseDemo.initTransaction(100d, CurrencyType.USD, CurrencyType.USD, CurrencyType.USD);
        TransactionPurchase purchase2 = TransactionPurchaseDemo.initTransaction(480 * 100d, CurrencyType.AMD, CurrencyType.USD, CurrencyType.USD);
        TransactionPurchase purchase3 = TransactionPurchaseDemo.initTransaction(100d, CurrencyType.USD, CurrencyType.AMD, CurrencyType.USD);
        TransactionPurchase purchase4 = TransactionPurchaseDemo.initTransaction(100d, CurrencyType.USD, CurrencyType.AMD, CurrencyType.AMD);
        TransactionPurchase purchase5 = TransactionPurchaseDemo.initTransaction(100d, CurrencyType.USD, CurrencyType.USD, CurrencyType.AMD);
        print(purchase1, purchase2, purchase3, purchase4, purchase5);

    }

    private static void print(TransactionPurchase purchase1, TransactionPurchase purchase2, TransactionPurchase purchase3, TransactionPurchase purchase4, TransactionPurchase purchase5) {
        System.out.println("All");
        System.out.println("1  USD USD USD " + purchase1);
        System.out.println("2  AMD USD USD " + purchase2);
        System.out.println("3  USD AMD USD " + purchase3);
//        System.out.println("4  USD AMD AMD " + purchase4);
//        System.out.println("5  USD USD AMD " + purchase5);
        System.out.println("getProcessStart");
        System.out.println("1  USD USD USD " + purchase1.getProcessStart());
        System.out.println("2  AMD USD USD " + purchase2.getProcessStart());
        System.out.println("3  USD AMD USD " + purchase3.getProcessStart());
//        System.out.println("4  USD AMD AMD " + purchase4.getProcessStart());
//        System.out.println("5  USD USD AMD " + purchase5.getProcessStart());
        System.out.println("");
        System.out.println("1  USD USD USD " + purchase1.getTax());
        System.out.println("2  AMD USD USD " + purchase2.getTax());
        System.out.println("3  USD AMD USD " + purchase3.getTax());
        System.out.println("4  USD AMD AMD " + purchase4.getTax());
        System.out.println("5  USD USD AMD " + purchase5.getTax());
        System.out.println("");
        System.out.println("1  USD USD USD " + purchase1.getTax().getProcessTax());
        System.out.println("2  AMD USD USD " + purchase2.getTax().getProcessTax());
        System.out.println("3  USD AMD USD " + purchase3.getTax().getProcessTax());
//        System.out.println("4  USD AMD AMD " + purchase4.getTax().getProcessTax());
//        System.out.println("5  USD USD AMD " + purchase5.getTax().getProcessTax());
        System.out.println("");
        System.out.println("1  USD USD USD " + purchase1.getTax().getExchangeTax());
        System.out.println("2  AMD USD USD " + purchase2.getTax().getExchangeTax());
        System.out.println("3  USD AMD USD " + purchase3.getTax().getExchangeTax());
//        System.out.println("4  USD AMD AMD " + purchase4.getTax().getExchangeTax());
//        System.out.println("5  USD USD AMD " + purchase5.getTax().getExchangeTax());



    }
}
