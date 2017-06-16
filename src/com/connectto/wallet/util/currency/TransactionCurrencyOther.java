package com.connectto.wallet.util.currency;

import com.connectto.general.exception.InternalErrorException;
import com.connectto.general.model.WalletSetup;
import com.connectto.wallet.model.transaction.purchase.*;
import com.connectto.wallet.model.transaction.request.*;
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
public class TransactionCurrencyOther {


    public static <T> void otherWalletCurrencyTransfer(T transaction,
                                                       TransactionState transactionState,
                                                       Date currentDate,
                                                       ExchangeRate selectedExchangeRate,
                                                       Wallet wallet,
                                                       WalletSetup walletSetup,
                                                       Double amount
    ) throws InternalErrorException {

        Double rateAmount = selectedExchangeRate.getBuy();

        Long walletId = wallet.getId();
        Long setupId = walletSetup.getId();

        CurrencyType walletCurrencyType = wallet.getCurrencyType();
        CurrencyType setupCurrencyType = walletSetup.getCurrencyType();

        Double price = amount * rateAmount;//480.000AMD
        Map<String, Object> processTaxMap = TaxCalculator.calculateTransferTax(walletSetup, amount);//1000 USD
        TransactionTaxType processTaxType = (TransactionTaxType) processTaxMap.get(Constant.TAX_TYPE_KEY);
        Double processTax = (Double) processTaxMap.get(Constant.TAX_KEY);//100 USD
        Double processTaxPrice = processTax * rateAmount;

        Map<String, Object> exchangeMap = TaxCalculator.calculateTransferExchangeTax(walletSetup, amount);//1000 USD
        TransactionTaxType exchangeType = (TransactionTaxType) exchangeMap.get(Constant.TAX_TYPE_KEY);
        Double exchange = (Double) exchangeMap.get(Constant.TAX_KEY);//100 USD
        Double exchangePrice = exchange * rateAmount;//48.000 AMD

        Double totalTaxAmount = processTax + exchange;
        Double totalTaxPrice = processTaxPrice + exchangePrice;

        Double totalAmount = amount + totalTaxAmount;
        Double totalPrice = price + totalTaxPrice;
//
        if (TransactionPurchase.class.isInstance(transaction)) {
            otherWalletCurrency(
                    (TransactionPurchase) transaction, transactionState, currentDate, selectedExchangeRate,
                    walletId, setupId,
                    walletCurrencyType, setupCurrencyType,
                    processTax, processTaxPrice, processTaxType,
                    exchange, exchangePrice, exchangeType,
                    amount, price,
                    totalAmount, totalPrice
            );
        } else if (TransactionSendMoney.class.isInstance(transaction)) {
            otherWalletCurrencyTransfer(
                    (TransactionSendMoney) transaction, selectedExchangeRate,
                    walletId, setupId,
                    walletCurrencyType, setupCurrencyType,
                    processTax, processTaxPrice, processTaxType,
                    exchange, exchangePrice, exchangeType,
                    amount, price,
                    totalAmount, totalPrice
            );
        } else if (TransactionRequest.class.isInstance(transaction)) {
            otherWalletCurrencyTransfer(
                    (TransactionRequest) transaction, selectedExchangeRate,
                    walletId, setupId,
                    walletCurrencyType, setupCurrencyType,
                    processTax, processTaxPrice, processTaxType,
                    exchange, exchangePrice, exchangeType,
                    amount, price,
                    totalAmount, totalPrice
            );
        }
    }

    public static <T> void otherWalletCurrencyReceiver(T transaction,
                                                       TransactionState transactionState,
                                                       Date currentDate,
                                                       ExchangeRate selectedExchangeRate,
                                                       Wallet wallet,
                                                       WalletSetup walletSetup,
                                                       Double amount
    ) throws InternalErrorException {

        Double rateAmount = selectedExchangeRate.getBuy();

        Long walletId = wallet.getId();
        Long setupId = walletSetup.getId();

        CurrencyType walletCurrencyType = wallet.getCurrencyType();
        CurrencyType setupCurrencyType = walletSetup.getCurrencyType();

        Double price = amount * rateAmount;//480.000AMD
        Map<String, Object> processTaxMap = TaxCalculator.calculateReceiverTax(walletSetup, amount);//1000 USD
        TransactionTaxType processTaxType = (TransactionTaxType) processTaxMap.get(Constant.TAX_TYPE_KEY);
        Double processTax = (Double) processTaxMap.get(Constant.TAX_KEY);//100 USD
        Double processTaxPrice = processTax * rateAmount;

        Map<String, Object> exchangeMap = TaxCalculator.calculateReceiverExchangeTax(walletSetup, amount);//1000 USD
        TransactionTaxType exchangeType = (TransactionTaxType) exchangeMap.get(Constant.TAX_TYPE_KEY);
        Double exchange = (Double) exchangeMap.get(Constant.TAX_KEY);//100 USD
        Double exchangePrice = exchange * rateAmount;//48.000 AMD

        Double totalTaxAmount = processTax + exchange;
        Double totalTaxPrice = processTaxPrice + exchangePrice;

        Double totalAmount = amount - totalTaxAmount;
        Double totalPrice = price - totalTaxPrice;
//
        if (TransactionSendMoney.class.isInstance(transaction)) {
            otherWalletCurrencyReceiver(
                    (TransactionSendMoney) transaction, selectedExchangeRate,
                    walletId, setupId,
                    walletCurrencyType, setupCurrencyType,
                    processTax, processTaxPrice, processTaxType,
                    exchange, exchangePrice, exchangeType,
                    amount, price,
                    totalAmount, totalPrice
            );
        }
    }


    private static void otherWalletCurrency(
            TransactionPurchase transactionPurchase, TransactionState transactionState, Date currentDate, ExchangeRate selectedExchangeRate,
            Long walletId, Long setupId,
            CurrencyType walletCurrencyType, CurrencyType setupCurrencyType,
            Double purchaseProcessTax, Double purchaseProcessTaxPrice, TransactionTaxType purchaseProcessTaxType,
            Double exchangePurchase, Double exchangePurchasePrice, TransactionTaxType exchangePurchaseType,
            Double purchaseAmount, Double purchasePrice,
            Double purchaseTotalAmount, Double purchaseTotalPrice
    ) {

        Double rateAmount = selectedExchangeRate.getBuy();
        Long rateId = selectedExchangeRate.getId();

        TransactionPurchaseExchangeTax purchaseExchangeTax = new TransactionPurchaseExchangeTax(
                currentDate, walletId, setupId, exchangePurchase, setupCurrencyType, exchangePurchasePrice, walletCurrencyType, exchangePurchaseType);
        TransactionPurchaseExchange purchaseProcessExchange = new TransactionPurchaseExchange(walletId, setupId, rateId, currentDate,
                purchaseProcessTax, setupCurrencyType, rateAmount, walletCurrencyType, purchaseProcessTaxPrice, walletCurrencyType, purchaseExchangeTax);

        TransactionPurchaseProcessTax processTax = new TransactionPurchaseProcessTax(
                currentDate, walletId, setupId,
                purchaseProcessTax, setupCurrencyType,
                purchaseProcessTaxPrice,  walletCurrencyType,
                purchaseProcessTaxType, purchaseProcessExchange);
        TransactionPurchaseTax purchaseTax =
                new TransactionPurchaseTax(currentDate, walletId, setupId, processTax, purchaseExchangeTax,
                        purchaseExchangeTax);

        TransactionPurchaseProcess purchaseProcess = new TransactionPurchaseProcess(transactionState, currentDate, walletId, setupId,
                purchaseAmount, setupCurrencyType,
                purchasePrice, purchaseTotalPrice, walletCurrencyType,
                purchaseAmount, purchaseTotalAmount, setupCurrencyType,
                processTax,
                purchaseProcessExchange);

        transactionPurchase.setProcessStart(purchaseProcess);
        transactionPurchase.setWalletTotalPrice(purchaseTotalPrice);
        transactionPurchase.setWalletTotalPriceCurrencyType(walletCurrencyType);

        transactionPurchase.setSetupTotalAmount(purchaseTotalAmount);
        transactionPurchase.setSetupTotalAmountCurrencyType(setupCurrencyType);
        transactionPurchase.setTax(purchaseTax);
    }


    private static void otherWalletCurrencyTransfer(
            TransactionSendMoney transaction, ExchangeRate selectedExchangeRate,
            Long walletId, Long setupId,
            CurrencyType walletCurrencyType, CurrencyType setupCurrencyType,
            Double sendMoneyProcessTax, Double sendMoneyProcessTaxPrice, TransactionTaxType sendMoneyProcessTaxType,
            Double exchangeSendMoney, Double exchangeSendMoneyPrice, TransactionTaxType exchangeSendMoneyType,
            Double sendMoneyAmount, Double sendMoneyPrice,
            Double sendMoneyTotalAmount, Double sendMoneyTotalPrice
    ) {

        Double rateAmount = selectedExchangeRate.getBuy();
        Long rateId = selectedExchangeRate.getId();

        TransactionSendMoneyExchange valueExchange = new TransactionSendMoneyExchange(walletId, setupId, rateId, sendMoneyAmount, setupCurrencyType, rateAmount,  sendMoneyPrice, walletCurrencyType);

        //Info(NOT decrease wallet balance, just show total exchange structure)
        // 10*480=4800
        TransactionSendMoneyExchangeTax processExchangeTax = new TransactionSendMoneyExchangeTax(walletId, setupId,
                sendMoneyProcessTax, setupCurrencyType, sendMoneyProcessTaxPrice, walletCurrencyType, sendMoneyProcessTaxType);
        TransactionSendMoneyExchange processExchange = new TransactionSendMoneyExchange(walletId, setupId, rateId,
                sendMoneyProcessTax, setupCurrencyType, rateAmount, walletCurrencyType, sendMoneyProcessTaxPrice, walletCurrencyType, processExchangeTax);
//
        //Info(NOT decrease wallet balance, just show total exchange structure)
        // 11*480=5280
        TransactionSendMoneyExchangeTax exchangeTax = new TransactionSendMoneyExchangeTax(walletId, setupId,
                exchangeSendMoney, setupCurrencyType, exchangeSendMoneyPrice, walletCurrencyType, exchangeSendMoneyType);
        TransactionSendMoneyExchange exchange = new TransactionSendMoneyExchange(walletId, setupId, rateId,
                exchangeSendMoney, setupCurrencyType, rateAmount, walletCurrencyType, exchangeSendMoneyPrice, walletCurrencyType, exchangeTax);
//

        TransactionSendMoneyProcessTax processTax = new TransactionSendMoneyProcessTax(walletId, setupId, sendMoneyProcessTax, setupCurrencyType, sendMoneyProcessTaxPrice, walletCurrencyType, sendMoneyProcessTaxType, processExchange);
        TransactionSendMoneyProcess sendMoneyProcess = new TransactionSendMoneyProcess(
                walletId, valueExchange,
                sendMoneyAmount, sendMoneyProcessTax, setupCurrencyType,
                sendMoneyPrice, sendMoneyProcessTaxPrice, walletCurrencyType,
                processTax, exchange);
        sendMoneyProcess.calculateTotalTransfer();

        transaction.setFromTransactionProcess(sendMoneyProcess);
        transaction.setFromTotal(sendMoneyTotalAmount);
        transaction.setFromTotalPrice(sendMoneyTotalPrice);
        transaction.setFromTotalPriceCurrencyType(walletCurrencyType);
    }

    private static void otherWalletCurrencyTransfer(
            TransactionRequest transaction, ExchangeRate selectedExchangeRate,
            Long walletId, Long setupId,
            CurrencyType walletCurrencyType, CurrencyType setupCurrencyType,
            Double requestProcessTax, Double requestProcessTaxPrice, TransactionTaxType requestProcessTaxType,
            Double exchangeRequest, Double exchangeRequestPrice, TransactionTaxType exchangeRequestType,
            Double requestAmount, Double requestPrice,
            Double requestTotalAmount, Double requestTotalPrice
    ) {

        Double rateAmount = selectedExchangeRate.getBuy();
        Long rateId = selectedExchangeRate.getId();

        TransactionRequestExchange valueExchange = new TransactionRequestExchange(walletId, setupId, rateId, requestAmount, setupCurrencyType, rateAmount,  requestPrice, walletCurrencyType);

        //Info(NOT decrease wallet balance, just show total exchange structure)
        // 10*480=4800
        TransactionRequestExchangeTax processExchangeTax = new TransactionRequestExchangeTax(walletId, setupId,
                requestProcessTax, setupCurrencyType, requestProcessTaxPrice, walletCurrencyType, requestProcessTaxType);
        TransactionRequestExchange processExchange = new TransactionRequestExchange(walletId, setupId, rateId,
                requestProcessTax, setupCurrencyType, rateAmount, walletCurrencyType, requestProcessTaxPrice, walletCurrencyType, processExchangeTax);
//
        //Info(NOT decrease wallet balance, just show total exchange structure)
        // 11*480=5280
        TransactionRequestExchangeTax exchangeTax = new TransactionRequestExchangeTax(walletId, setupId,
                exchangeRequest, setupCurrencyType, exchangeRequestPrice, walletCurrencyType, exchangeRequestType);
        TransactionRequestExchange exchange = new TransactionRequestExchange(walletId, setupId, rateId,
                exchangeRequest, setupCurrencyType, rateAmount, walletCurrencyType, exchangeRequestPrice, walletCurrencyType, exchangeTax);
//

        TransactionRequestProcessTax processTax = new TransactionRequestProcessTax(walletId, setupId, requestProcessTax, setupCurrencyType, requestProcessTaxPrice, walletCurrencyType, requestProcessTaxType, processExchange);
        TransactionRequestProcess requestProcess = new TransactionRequestProcess(
                walletId, valueExchange,
                requestAmount, requestProcessTax, setupCurrencyType,
                requestPrice, requestProcessTaxPrice, walletCurrencyType,
                processTax, exchange);
        requestProcess.calculateTotalTransfer();

        transaction.setFromTransactionProcess(requestProcess);
        transaction.setFromTotal(requestTotalAmount);
        transaction.setFromTotalPrice(requestTotalPrice);
        transaction.setFromTotalPriceCurrencyType(walletCurrencyType);
    }



    private static void otherWalletCurrencyReceiver(
            TransactionSendMoney transaction, ExchangeRate selectedExchangeRate,
            Long walletId, Long setupId,
            CurrencyType walletCurrencyType, CurrencyType setupCurrencyType,
            Double sendMoneyProcessTax, Double sendMoneyProcessTaxPrice, TransactionTaxType sendMoneyProcessTaxType,
            Double exchangeSendMoney, Double exchangeSendMoneyPrice, TransactionTaxType exchangeSendMoneyType,
            Double sendMoneyAmount, Double sendMoneyPrice,
            Double sendMoneyTotalAmount, Double sendMoneyTotalPrice
    ) {

        Double rateAmount = selectedExchangeRate.getBuy();
        Long rateId = selectedExchangeRate.getId();

        //Info(NOT decrease wallet balance, just show total exchange structure)
        // 10*480=4800
        TransactionSendMoneyExchangeTax processExchangeTax = new TransactionSendMoneyExchangeTax(walletId, setupId,
                sendMoneyProcessTax, setupCurrencyType, sendMoneyProcessTaxPrice, walletCurrencyType, sendMoneyProcessTaxType);
        TransactionSendMoneyExchange processExchange = new TransactionSendMoneyExchange(walletId, setupId, rateId,
                sendMoneyProcessTax, setupCurrencyType, rateAmount, walletCurrencyType, sendMoneyProcessTaxPrice, walletCurrencyType, processExchangeTax);
//
        //Info(NOT decrease wallet balance, just show total exchange structure)
        // 11*480=5280
        TransactionSendMoneyExchangeTax exchangeTax = new TransactionSendMoneyExchangeTax(walletId, setupId,
                exchangeSendMoney, setupCurrencyType, exchangeSendMoneyPrice, walletCurrencyType, exchangeSendMoneyType);
        TransactionSendMoneyExchange exchange = new TransactionSendMoneyExchange(walletId, setupId, rateId,
                exchangeSendMoney, setupCurrencyType, rateAmount, walletCurrencyType, exchangeSendMoneyPrice, walletCurrencyType, exchangeTax);
//

        TransactionSendMoneyProcessTax processTax = new TransactionSendMoneyProcessTax(walletId, setupId, sendMoneyProcessTax, setupCurrencyType, sendMoneyProcessTaxPrice, walletCurrencyType, sendMoneyProcessTaxType, processExchange);
        TransactionSendMoneyProcess sendMoneyProcess = new TransactionSendMoneyProcess(
                walletId, null,
                sendMoneyAmount, sendMoneyProcessTax, setupCurrencyType,
                sendMoneyPrice, sendMoneyProcessTaxPrice, walletCurrencyType,
                processTax, exchange);
        sendMoneyProcess.calculateTotalReceiver();

        transaction.setToTransactionProcess(sendMoneyProcess);
        transaction.setToTotal(sendMoneyTotalAmount);
        transaction.setToTotalPrice(sendMoneyTotalPrice);
        transaction.setToTotalPriceCurrencyType(walletCurrencyType);
    }


}
