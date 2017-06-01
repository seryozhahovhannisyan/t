package com.connectto.wallet.test;

import com.connectto.general.exception.InternalErrorException;
import com.connectto.general.exception.InvalidParameterException;
import com.connectto.general.exception.PermissionDeniedException;
import com.connectto.general.exception.UnsupportedCurrencyException;
import com.connectto.wallet.model.transaction.purchase.TransactionPurchase;
import com.connectto.wallet.model.transaction.request.TransactionRequest;
import com.connectto.wallet.model.transaction.sendmoney.TransactionSendMoney;
import com.connectto.wallet.model.wallet.lcp.CurrencyType;
import com.connectto.wallet.util.TransactionPurchaseDemo;
import com.connectto.wallet.util.TransactionRequestDemo;
import com.connectto.wallet.util.TransactionSendMoneyDemo;

/**
 * Created by Serozh on 3/30/2017.
 */
public class OtherProductTest {

    public static void main(String[] args) throws InvalidParameterException, InternalErrorException, PermissionDeniedException, UnsupportedCurrencyException {
                                                                                        //productAmo, productType,       from,            to, CurrencyType setup ,
        TransactionSendMoney sendMoney = TransactionSendMoneyDemo.initDemoTransactionSendMoney(480*100d, CurrencyType.AMD, CurrencyType.USD, CurrencyType.AMD, CurrencyType.USD);
        //TransactionRequest request     = TransactionRequestDemo.initDemoTransactionRequest    (480*100d, CurrencyType.AMD, CurrencyType.USD, CurrencyType.AMD, CurrencyType.USD);
                                                                                        //purchaseAmount, purchaseCurrencyType,  from, CurrencyType setup
        //TransactionPurchase purchase   = TransactionPurchaseDemo.initTransaction              (480*100d, CurrencyType.AMD, CurrencyType.USD, CurrencyType.USD);


//
        print(sendMoney, null, null);
    }

    private static void print(TransactionSendMoney sendMoney, TransactionRequest request, TransactionPurchase purchase) {
        System.out.println("All");
        System.out.println(sendMoney);
//        System.out.println(request);
//        System.out.println(purchase);
//        System.out.println("TAX");
//        System.out.println(sendMoney.getTax());
//        //System.out.println(request.getTax());
//        System.out.println(purchase.getTax());
//        System.out.println("TAX getFromProcessTax getProcessTax");
//        System.out.println(sendMoney.getTax().getFromProcessTax());
//        //System.out.println(request.getTax().getFromProcessTax());
//        System.out.println(sendMoney.getTax().getToProcessTax());
//        //System.out.println(request.getTax().getToProcessTax());
//        System.out.println(purchase.getTax().getProcessTax());
    }
}
