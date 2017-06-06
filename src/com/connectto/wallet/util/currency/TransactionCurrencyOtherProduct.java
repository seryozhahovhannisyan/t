package com.connectto.wallet.util.currency;

import com.connectto.general.exception.InternalErrorException;
import com.connectto.general.model.WalletSetup;
import com.connectto.wallet.model.transaction.purchase.*;
import com.connectto.wallet.model.transaction.sendmoney.*;
import com.connectto.wallet.model.wallet.ExchangeRate;
import com.connectto.wallet.model.wallet.Wallet;
import com.connectto.wallet.model.wallet.lcp.CurrencyType;
import com.connectto.wallet.model.wallet.lcp.TransactionState;
import com.connectto.wallet.model.wallet.lcp.TransactionTaxType;
import com.connectto.wallet.util.Constant;
import com.connectto.wallet.util.TaxCalculator;

import java.util.Date;
import java.util.Map;

/**
 * Created by Serozh on 3/30/2017.
 */
public class TransactionCurrencyOtherProduct {


    public static <T> void otherProductCurrencyTransfer(T transaction,
                                                        TransactionState transactionState,
                                                        Date currentDate,
                                                        ExchangeRate selectedExchangeRate,
                                                        Wallet wallet,
                                                        WalletSetup walletSetup,
                                                        Double amount,
                                                        CurrencyType amountCurrencyType
    ) throws InternalErrorException {

        Double rateAmount = selectedExchangeRate.getBuy();

        Long walletId = wallet.getId();
        Long setupId = walletSetup.getId();

        CurrencyType setupCurrencyType = walletSetup.getCurrencyType();

        Double price = amount / rateAmount;//480.000AMD
        Map<String, Object> processTaxMap = TaxCalculator.calculateTransferTax(walletSetup, price);//1000 USD
        TransactionTaxType processTaxType = (TransactionTaxType) processTaxMap.get(Constant.TAX_TYPE_KEY);
        Double processTax = (Double) processTaxMap.get(Constant.TAX_KEY);//100 USD
        Double processTaxPrice = processTax * rateAmount;

        Map<String, Object> exchangeMap = TaxCalculator.calculateTransferExchangeTax(walletSetup, price);//1000 USD
        TransactionTaxType exchangeType = (TransactionTaxType) exchangeMap.get(Constant.TAX_TYPE_KEY);
        Double exchange = (Double) exchangeMap.get(Constant.TAX_KEY);//100 USD
        Double exchangePrice = exchange * rateAmount;//48.000 AMD

        Double totalTaxAmount = processTaxPrice + exchangePrice;
        Double totalTaxPrice = processTax + exchange;

        Double totalAmount = amount + totalTaxAmount;
        Double totalPrice = price + totalTaxPrice;
//
        if (TransactionPurchase.class.isInstance(transaction)) {
            otherProductCurrencyTransfer(
                    (TransactionPurchase) transaction, transactionState, currentDate,
                    walletId, setupId,
                    setupCurrencyType, amountCurrencyType,
                    processTax, processTaxType,
                    exchange, exchangeType,
                    amount, price,
                    totalPrice
            );
        } else if (TransactionSendMoney.class.isInstance(transaction)) {
            otherProductCurrencyTransfer(
                    (TransactionSendMoney) transaction, currentDate,
                    walletId, setupId,
                    setupCurrencyType, amountCurrencyType,
                    processTax,  processTaxType,
                    exchange,  exchangeType,
                    amount, price,
                    totalPrice
            );
        }
    }

    private static void otherProductCurrencyTransfer(
            TransactionPurchase transactionPurchase, TransactionState transactionState, Date currentDate,
            Long walletId, Long setupId,
            CurrencyType setupCurrencyType, CurrencyType amountCurrencyType,
            Double purchaseProcessTax, TransactionTaxType purchaseProcessTaxType,
            Double exchangePurchase, TransactionTaxType exchangePurchaseType,
            Double purchaseAmount, Double purchasePrice,
            Double purchaseTotalPrice
    ) {

        TransactionPurchaseExchangeTax purchaseExchangeTax = new TransactionPurchaseExchangeTax(
                currentDate, walletId, setupId, exchangePurchase, setupCurrencyType,  exchangePurchaseType);
        TransactionPurchaseExchange purchaseProcessExchange = new TransactionPurchaseExchange(walletId, setupId, currentDate,
                purchaseProcessTax, setupCurrencyType, purchaseExchangeTax);

        TransactionPurchaseProcessTax processTax = new TransactionPurchaseProcessTax(
                currentDate, walletId, setupId, purchaseProcessTax,  setupCurrencyType, purchaseProcessTaxType, purchaseProcessExchange);
        TransactionPurchaseTax purchaseTax = new TransactionPurchaseTax(currentDate, walletId, setupId, processTax, purchaseExchangeTax, purchaseExchangeTax);

        TransactionPurchaseProcess purchaseProcess = new TransactionPurchaseProcess(transactionState, currentDate, walletId, setupId,
                purchaseAmount, amountCurrencyType,
                purchasePrice, purchaseTotalPrice, setupCurrencyType,
                processTax,
                purchaseProcessExchange);

        transactionPurchase.setProcessStart(purchaseProcess);
        transactionPurchase.setWalletTotalPrice(purchaseTotalPrice);
        transactionPurchase.setWalletTotalPriceCurrencyType(setupCurrencyType);

        transactionPurchase.setSetupTotalAmount(purchaseTotalPrice);
        transactionPurchase.setSetupTotalAmountCurrencyType(setupCurrencyType);
        transactionPurchase.setTax(purchaseTax);
    }

    private static void otherProductCurrencyTransfer(
            TransactionSendMoney transactionPurchase, Date currentDate,
            Long walletId, Long setupId,
            CurrencyType setupCurrencyType, CurrencyType amountCurrencyType,
            Double purchaseProcessTax, TransactionTaxType purchaseProcessTaxType,
            Double exchangePurchase, TransactionTaxType exchangePurchaseType,
            Double purchaseAmount, Double purchasePrice,
            Double purchaseTotalPrice
    ) {

        TransactionSendMoneyExchangeTax purchaseExchangeTax = new TransactionSendMoneyExchangeTax(
                 walletId, setupId, exchangePurchase, setupCurrencyType,  exchangePurchaseType);
        TransactionSendMoneyExchange purchaseProcessExchange = new TransactionSendMoneyExchange(walletId, setupId,
                purchaseProcessTax, setupCurrencyType, purchaseExchangeTax);

        TransactionSendMoneyProcessTax processTax = new TransactionSendMoneyProcessTax(
                 walletId, setupId, purchaseProcessTax,  setupCurrencyType, purchaseProcessTaxType, purchaseProcessExchange);

        TransactionSendMoneyProcess purchaseProcess = new TransactionSendMoneyProcess( walletId,
                purchaseAmount, amountCurrencyType,
                purchasePrice, purchaseTotalPrice, setupCurrencyType,
                processTax,
                purchaseProcessExchange);

        purchaseProcess.calculateTotalTransfer();

        transactionPurchase.setFromTransactionProcess(purchaseProcess);
        transactionPurchase.setFromTotal(purchaseTotalPrice);
        transactionPurchase.setFromTotalPrice(purchaseTotalPrice);
        transactionPurchase.setFromTotalPriceCurrencyType(setupCurrencyType);
    }




}
