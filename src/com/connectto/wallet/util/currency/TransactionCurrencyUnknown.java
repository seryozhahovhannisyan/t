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
 * Created by Serozh on 4/4/2017.
 */
public class TransactionCurrencyUnknown {


    public static <T> void unknownCurrencyTransfer(T transaction,
                                                   TransactionState transactionState,
                                                   Date currentDate,
                                                   ExchangeRate selectedExchangeRate,
                                                   Wallet wallet,
                                                   WalletSetup walletSetup,
                                                   Double amount,
                                                   Double productAmount,
                                                   CurrencyType productCurrencyType,
                                                   ExchangeRate productExchangeRate
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
            unknownCurrencyTransfer(
                    (TransactionPurchase) transaction, selectedExchangeRate,
                    walletId, setupId,
                    walletCurrencyType, setupCurrencyType,
                    processTax, processTaxPrice, processTaxType,
                    exchange, exchangePrice, exchangeType,
                    amount, price,
                    totalAmount, totalPrice,
                    productAmount, productCurrencyType, productExchangeRate,
                    transactionState, currentDate
            );
        } else if (TransactionSendMoney.class.isInstance(transaction)) {
            unknownCurrencyTransfer(
                    (TransactionSendMoney) transaction, selectedExchangeRate,
                    walletId, setupId,
                    walletCurrencyType, setupCurrencyType,
                    processTax, processTaxPrice, processTaxType,
                    exchange, exchangePrice, exchangeType,
                    amount, price,
                    totalAmount, totalPrice,
                    productAmount, productCurrencyType, productExchangeRate
            );
        } else if (TransactionRequest.class.isInstance(transaction)) {
            unknownCurrencyTransfer(
                    (TransactionRequest) transaction, selectedExchangeRate,
                    walletId, setupId,
                    walletCurrencyType, setupCurrencyType,
                    processTax, processTaxPrice, processTaxType,
                    exchange, exchangePrice, exchangeType,
                    amount, price,
                    totalAmount, totalPrice,
                    productAmount, productCurrencyType, productExchangeRate
            );
        }
    }

    private static void unknownCurrencyTransfer(
            TransactionPurchase transaction, ExchangeRate selectedExchangeRate,
            Long walletId, Long setupId,
            CurrencyType walletCurrencyType, CurrencyType setupCurrencyType,
            Double purchaseProcessTax, Double purchaseProcessTaxPrice, TransactionTaxType purchaseProcessTaxType,
            Double exchangePurchase, Double exchangePurchasePrice, TransactionTaxType exchangePurchaseType,
            Double purchaseAmount, Double purchasePrice,
            Double totalAmount, Double totalPrice,
            Double productAmount, CurrencyType productCurrencyType, ExchangeRate productExchangeRate,
            TransactionState transactionState, Date currentDate
    ) {

        Double rateAmount = selectedExchangeRate.getBuy();
        Long rateId = selectedExchangeRate.getId();

        //Info(NOT decrease wallet balance, just show total exchange structure)
        // 10*480=4800
        TransactionPurchaseExchangeTax processExchangeTax = new TransactionPurchaseExchangeTax(currentDate, walletId, setupId,
                purchaseProcessTax, setupCurrencyType, purchaseProcessTaxPrice, walletCurrencyType, purchaseProcessTaxType);

        TransactionPurchaseExchange processExchange = new TransactionPurchaseExchange(walletId, setupId, rateId, currentDate,
                purchaseProcessTax, setupCurrencyType, rateAmount, walletCurrencyType,
                purchaseProcessTaxPrice, walletCurrencyType, processExchangeTax);
//
        //Info(NOT decrease wallet balance, just show total exchange structure)
        // 11*480=5280
        TransactionPurchaseExchangeTax exchangeTax = new TransactionPurchaseExchangeTax(currentDate, walletId, setupId, exchangePurchase, setupCurrencyType,
                exchangePurchasePrice, walletCurrencyType, exchangePurchaseType);
        TransactionPurchaseExchange exchange = new TransactionPurchaseExchange(walletId, setupId, rateId, currentDate,
                purchaseProcessTax, setupCurrencyType, rateAmount, walletCurrencyType,
                purchaseProcessTaxPrice, walletCurrencyType, exchangeTax);
//

        TransactionPurchaseProcessTax processTax = new TransactionPurchaseProcessTax(currentDate, walletId, setupId,
                purchaseProcessTax, setupCurrencyType,
                purchaseProcessTaxPrice, walletCurrencyType, purchaseProcessTaxType, exchange);

        TransactionPurchaseProcess process = new TransactionPurchaseProcess(
                transactionState, currentDate,
                walletId, setupId,
                productAmount, productCurrencyType,
                purchasePrice, totalPrice, walletCurrencyType,
                purchaseAmount, totalAmount, setupCurrencyType,
                processTax, exchange);

        TransactionPurchaseTax purchaseTax
                = new TransactionPurchaseTax(currentDate, walletId, setupId, processTax, exchangeTax, exchangeTax);

        transaction.setProcessStart(process);
        transaction.setWalletTotalPrice(totalPrice);
        transaction.setWalletTotalPriceCurrencyType(walletCurrencyType);

        transaction.setSetupTotalAmount(totalAmount);
        transaction.setSetupTotalAmountCurrencyType(setupCurrencyType);
        transaction.setTax(purchaseTax);
    }

    private static void unknownCurrencyTransfer(
            TransactionSendMoney transaction, ExchangeRate selectedExchangeRate,
            Long walletId, Long setupId,
            CurrencyType walletCurrencyType, CurrencyType setupCurrencyType,
            Double sendMoneyProcessTax, Double sendMoneyProcessTaxPrice, TransactionTaxType sendMoneyProcessTaxType,
            Double exchangeSendMoney, Double exchangeSendMoneyPrice, TransactionTaxType exchangeSendMoneyType,
            Double sendMoneyAmount, Double sendMoneyPrice,
            Double sendMoneyTotalAmount, Double sendMoneyTotalPrice,
            Double productAmount, CurrencyType productCurrencyType, ExchangeRate productExchangeRate
    ) {

        Double rateAmount = selectedExchangeRate.getBuy();
        Long rateId = selectedExchangeRate.getId();

        TransactionSendMoneyExchange valueExchange = new TransactionSendMoneyExchange(walletId, setupId, productExchangeRate.getId(), sendMoneyAmount, setupCurrencyType, productExchangeRate.getBuy(), productAmount, productCurrencyType, sendMoneyPrice, walletCurrencyType);

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
                walletId,
                productAmount, productCurrencyType, valueExchange,
                sendMoneyAmount, sendMoneyProcessTax, setupCurrencyType,
                sendMoneyPrice, sendMoneyProcessTaxPrice, walletCurrencyType,
                processTax, exchange);
        sendMoneyProcess.calculateTotalTransfer();

        transaction.setFromTransactionProcess(sendMoneyProcess);
        transaction.setFromTotal(sendMoneyTotalAmount);
        transaction.setFromTotalPrice(sendMoneyTotalPrice);
        transaction.setFromTotalPriceCurrencyType(walletCurrencyType);
    }

    private static void unknownCurrencyTransfer(
            TransactionRequest transaction, ExchangeRate selectedExchangeRate,
            Long walletId, Long setupId,
            CurrencyType walletCurrencyType, CurrencyType setupCurrencyType,
            Double requestProcessTax, Double requestProcessTaxPrice, TransactionTaxType requestProcessTaxType,
            Double exchangeRequest, Double exchangeRequestPrice, TransactionTaxType exchangeRequestType,
            Double requestAmount, Double requestPrice,
            Double requestTotalAmount, Double requestTotalPrice,
            Double productAmount, CurrencyType productCurrencyType, ExchangeRate productExchangeRate
    ) {

        Double rateAmount = selectedExchangeRate.getBuy();
        Long rateId = selectedExchangeRate.getId();

        TransactionRequestExchange valueExchange = new TransactionRequestExchange(walletId, setupId, productExchangeRate.getId(), requestAmount, setupCurrencyType, productExchangeRate.getBuy(), productAmount, productCurrencyType, requestPrice, walletCurrencyType);

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
                walletId,
                productAmount, productCurrencyType, valueExchange,
                requestAmount, requestProcessTax, setupCurrencyType,
                requestPrice, requestProcessTaxPrice, walletCurrencyType,
                processTax, exchange);
        requestProcess.calculateTotalTransfer();

        transaction.setFromTransactionProcess(requestProcess);
        transaction.setFromTotal(requestTotalAmount);
        transaction.setFromTotalPrice(requestTotalPrice);
        transaction.setFromTotalPriceCurrencyType(walletCurrencyType);
    }

    public static <T> void unknownCurrencyReceiver(T transaction,
                                                   ExchangeRate selectedExchangeRate,
                                                   Wallet wallet,
                                                   WalletSetup walletSetup,
                                                   Double amount,
                                                   Double productAmount,
                                                   CurrencyType productCurrencyType,
                                                   ExchangeRate productExchangeRate
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
        if (totalAmount <= 0) {
            throw new InternalErrorException(Constant.MESSAGE_LESS_REQUEST);
        }
//
        if (TransactionSendMoney.class.isInstance(transaction)) {
            unknownCurrencyReceiver(
                    (TransactionSendMoney) transaction, selectedExchangeRate,
                    walletId, setupId,
                    walletCurrencyType, setupCurrencyType,
                    processTax, processTaxPrice, processTaxType,
                    exchange, exchangePrice, exchangeType,
                    amount, price,
                    totalAmount, totalPrice,
                    productAmount, productCurrencyType, productExchangeRate
            );
        } else if (TransactionRequest.class.isInstance(transaction)) {
            unknownCurrencyReceiver(
                    (TransactionRequest) transaction, selectedExchangeRate,
                    walletId, setupId,
                    walletCurrencyType, setupCurrencyType,
                    processTax, processTaxPrice, processTaxType,
                    exchange, exchangePrice, exchangeType,
                    amount, price,
                    totalAmount, totalPrice,
                    productAmount, productCurrencyType, productExchangeRate
            );
        }
    }


    private static void unknownCurrencyReceiver(
            TransactionSendMoney transaction, ExchangeRate selectedExchangeRate,
            Long walletId, Long setupId,
            CurrencyType walletCurrencyType, CurrencyType setupCurrencyType,
            Double sendMoneyProcessTax, Double sendMoneyProcessTaxPrice, TransactionTaxType sendMoneyProcessTaxType,
            Double exchangeSendMoney, Double exchangeSendMoneyPrice, TransactionTaxType exchangeSendMoneyType,
            Double sendMoneyAmount, Double sendMoneyPrice,
            Double sendMoneyTotalAmount, Double sendMoneyTotalPrice,
            Double productAmount, CurrencyType productCurrencyType, ExchangeRate productExchangeRate
    ) {

        Double rateAmount = selectedExchangeRate.getBuy();
        Long rateId = selectedExchangeRate.getId();

        TransactionSendMoneyExchange valueExchange = new TransactionSendMoneyExchange(walletId, setupId, productExchangeRate.getId(), sendMoneyAmount, setupCurrencyType, productExchangeRate.getBuy(), productAmount, productCurrencyType, sendMoneyPrice, walletCurrencyType);

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
                walletId,
                productAmount, productCurrencyType, valueExchange,
                sendMoneyAmount, sendMoneyProcessTax, setupCurrencyType,
                sendMoneyPrice, sendMoneyProcessTaxPrice, walletCurrencyType,
                processTax, exchange);
        sendMoneyProcess.calculateTotalReceiver();

        transaction.setToTransactionProcess(sendMoneyProcess);
        transaction.setToTotal(sendMoneyTotalAmount);
        transaction.setToTotalPrice(sendMoneyTotalPrice);
        transaction.setToTotalPriceCurrencyType(walletCurrencyType);
    }

    private static void unknownCurrencyReceiver(
            TransactionRequest transaction, ExchangeRate selectedExchangeRate,
            Long walletId, Long setupId,
            CurrencyType walletCurrencyType, CurrencyType setupCurrencyType,
            Double requestProcessTax, Double requestProcessTaxPrice, TransactionTaxType requestProcessTaxType,
            Double exchangeRequest, Double exchangeRequestPrice, TransactionTaxType exchangeRequestType,
            Double requestAmount, Double requestPrice,
            Double requestTotalAmount, Double requestTotalPrice,
            Double productAmount, CurrencyType productCurrencyType, ExchangeRate productExchangeRate
    ) {

        Double rateAmount = selectedExchangeRate.getBuy();
        Long rateId = selectedExchangeRate.getId();

        TransactionRequestExchange valueExchange = new TransactionRequestExchange(walletId, setupId, productExchangeRate.getId(), requestAmount, setupCurrencyType, productExchangeRate.getBuy(), productAmount, productCurrencyType, requestPrice, walletCurrencyType);

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
                walletId,
                productAmount, productCurrencyType, valueExchange,
                requestAmount, requestProcessTax, setupCurrencyType,
                requestPrice, requestProcessTaxPrice, walletCurrencyType,
                processTax, exchange);
        requestProcess.calculateTotalReceiver();

        transaction.setToTransactionProcess(requestProcess);
        transaction.setToTotal(requestTotalAmount);
        transaction.setToTotalPrice(requestTotalPrice);
        transaction.setToTotalPriceCurrencyType(walletCurrencyType);
    }


}
