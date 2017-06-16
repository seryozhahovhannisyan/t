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
public class TransactionCurrencyOtherProduct {


    public static <T> void otherProductCurrencyTransfer(T transaction,
                                                        TransactionState transactionState,
                                                        Date currentDate,
                                                        ExchangeRate selectedExchangeRate,
                                                        Wallet fromWallet,
                                                        WalletSetup walletSetup,
                                                        Double amount,
                                                        CurrencyType amountCurrencyType
    ) throws InternalErrorException {

        Double rateAmount = selectedExchangeRate.getBuy();

        Long walletId = fromWallet.getId();
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

            Double currentBalance = fromWallet.getMoney();
            Double frozenAmount = fromWallet.getFrozenAmount();
            Double availableAmount = currentBalance - frozenAmount;

            if (availableAmount < totalAmount) {
                throw new InternalErrorException(Constant.MESSAGE_LESS_MONEY);
            }

            otherProductCurrencyTransfer(
                    (TransactionSendMoney) transaction, selectedExchangeRate,
                    walletId, setupId,
                    setupCurrencyType, amountCurrencyType,
                    processTax, processTaxType,
                    exchange, exchangeType,
                    amount, price,
                    totalPrice
            );
        } else if (TransactionRequest.class.isInstance(transaction)) {

            Double currentBalance = fromWallet.getMoney();
            Double frozenAmount = fromWallet.getFrozenAmount();
            Double availableAmount = currentBalance - frozenAmount;

            if (availableAmount < totalAmount) {
                throw new InternalErrorException(Constant.MESSAGE_LESS_MONEY);
            }

            otherProductCurrencyTransfer(
                    (TransactionRequest) transaction, selectedExchangeRate,
                    walletId, setupId,
                    setupCurrencyType, amountCurrencyType,
                    processTax, processTaxType,
                    exchange, exchangeType,
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
                currentDate, walletId, setupId, exchangePurchase, setupCurrencyType, exchangePurchaseType);
        TransactionPurchaseExchange purchaseProcessExchange = new TransactionPurchaseExchange(walletId, setupId, currentDate,
                purchaseProcessTax, setupCurrencyType, purchaseExchangeTax);

        TransactionPurchaseProcessTax processTax = new TransactionPurchaseProcessTax(
                currentDate, walletId, setupId, purchaseProcessTax, setupCurrencyType, purchaseProcessTaxType, purchaseProcessExchange);
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
            TransactionSendMoney transactionSendMoney, ExchangeRate exchangeRate,
            Long walletId, Long setupId,
            CurrencyType setupCurrencyType, CurrencyType valueCurrencyType,
            Double sendMoneyProcessTax, TransactionTaxType sendMoneyProcessTaxType,
            Double exchangeSendMoney, TransactionTaxType exchangeSendMoneyType,
            Double valueAmount, Double sendMoneyPrice,
            Double sendMoneyTotalPrice
    ) {

        TransactionSendMoneyExchange valueExchange = new TransactionSendMoneyExchange(walletId, setupId, exchangeRate.getId(), sendMoneyPrice, setupCurrencyType, exchangeRate.getBuy(), valueAmount, valueCurrencyType, sendMoneyPrice, setupCurrencyType);

        TransactionSendMoneyExchangeTax sendMoneyExchangeTax = new TransactionSendMoneyExchangeTax(walletId, setupId, exchangeSendMoney, setupCurrencyType, exchangeSendMoneyType);
        TransactionSendMoneyExchange sendMoneyProcessExchange = new TransactionSendMoneyExchange(walletId, setupId, sendMoneyProcessTax, setupCurrencyType, sendMoneyExchangeTax);

        TransactionSendMoneyProcessTax processTax = new TransactionSendMoneyProcessTax(walletId, setupId, sendMoneyProcessTax, setupCurrencyType, sendMoneyProcessTaxType, sendMoneyProcessExchange);

        TransactionSendMoneyProcess sendMoneyProcess = new TransactionSendMoneyProcess(walletId, valueAmount, valueCurrencyType, valueExchange, sendMoneyPrice, sendMoneyProcessTax, exchangeSendMoney, setupCurrencyType, processTax, sendMoneyProcessExchange);
        sendMoneyProcess.calculateTotalTransfer();

        transactionSendMoney.setFromTransactionProcess(sendMoneyProcess);
        transactionSendMoney.setFromTotalPrice(sendMoneyTotalPrice);
        transactionSendMoney.setFromTotalPriceCurrencyType(setupCurrencyType);
        transactionSendMoney.setFromTotal(sendMoneyTotalPrice);

    }

    private static void otherProductCurrencyTransfer(
            TransactionRequest transactionRequest, ExchangeRate exchangeRate,
            Long walletId, Long setupId,
            CurrencyType setupCurrencyType, CurrencyType valueCurrencyType,
            Double requestProcessTax, TransactionTaxType requestProcessTaxType,
            Double exchangeRequest, TransactionTaxType exchangeRequestType,
            Double valueAmount, Double requestPrice,
            Double requestTotalPrice
    ) {

        TransactionRequestExchange valueExchange = new TransactionRequestExchange(walletId, setupId, exchangeRate.getId(), requestPrice, setupCurrencyType, exchangeRate.getBuy(), valueAmount, valueCurrencyType, requestPrice, setupCurrencyType);

        TransactionRequestExchangeTax requestExchangeTax = new TransactionRequestExchangeTax(walletId, setupId, exchangeRequest, setupCurrencyType, exchangeRequestType);
        TransactionRequestExchange requestProcessExchange = new TransactionRequestExchange(walletId, setupId, requestProcessTax, setupCurrencyType, requestExchangeTax);

        TransactionRequestProcessTax processTax = new TransactionRequestProcessTax(walletId, setupId, requestProcessTax, setupCurrencyType, requestProcessTaxType, requestProcessExchange);

        TransactionRequestProcess requestProcess = new TransactionRequestProcess(walletId, valueAmount, valueCurrencyType, valueExchange, requestPrice, requestProcessTax, exchangeRequest, setupCurrencyType, processTax, requestProcessExchange);
        requestProcess.calculateTotalTransfer();

        transactionRequest.setFromTransactionProcess(requestProcess);
        transactionRequest.setFromTotalPrice(requestTotalPrice);
        transactionRequest.setFromTotalPriceCurrencyType(setupCurrencyType);
        transactionRequest.setFromTotal(requestTotalPrice);

    }

    public static <T> void otherProductCurrencyReceiver(T transaction,
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
        Map<String, Object> processTaxMap = TaxCalculator.calculateReceiverTax(walletSetup, price);
        TransactionTaxType processTaxType = (TransactionTaxType) processTaxMap.get(Constant.TAX_TYPE_KEY);
        Double processTax = (Double) processTaxMap.get(Constant.TAX_KEY);
        Double processTaxPrice = processTax * rateAmount;

        Map<String, Object> exchangeMap = TaxCalculator.calculateReceiverExchangeTax(walletSetup, price);//1000 USD
        TransactionTaxType exchangeType = (TransactionTaxType) exchangeMap.get(Constant.TAX_TYPE_KEY);
        Double exchange = (Double) exchangeMap.get(Constant.TAX_KEY);
        Double exchangePrice = exchange * rateAmount;

        Double totalTaxAmount = processTaxPrice + exchangePrice;
        Double totalTaxPrice = processTax + exchange;
        Double totalPrice = price - totalTaxPrice;
//

        if (totalPrice < 0) {
            throw new InternalErrorException(Constant.MESSAGE_LESS_REQUEST);
        }

        if (TransactionSendMoney.class.isInstance(transaction)) {

            otherProductCurrencyReceiver(
                    (TransactionSendMoney) transaction, selectedExchangeRate,
                    walletId, setupId,
                    setupCurrencyType, amountCurrencyType,
                    processTax, processTaxType,
                    exchange, exchangeType,
                    amount, price,
                    totalPrice
            );
        } else if (TransactionRequest.class.isInstance(transaction)) {

            otherProductCurrencyReceiver(
                    (TransactionRequest) transaction, selectedExchangeRate,
                    walletId, setupId,
                    setupCurrencyType, amountCurrencyType,
                    processTax, processTaxType,
                    exchange, exchangeType,
                    amount, price,
                    totalPrice
            );
        }
    }

    private static void otherProductCurrencyReceiver(
            TransactionSendMoney transactionSendMoney, ExchangeRate exchangeRate,
            Long walletId, Long setupId,
            CurrencyType setupCurrencyType, CurrencyType valueCurrencyType,
            Double sendMoneyProcessTax, TransactionTaxType sendMoneyProcessTaxType,
            Double exchangeSendMoney, TransactionTaxType exchangeSendMoneyType,
            Double valueAmount, Double sendMoneyPrice,
            Double sendMoneyTotalPrice
    ) {

        TransactionSendMoneyExchange valueExchange = new TransactionSendMoneyExchange(walletId, setupId, exchangeRate.getId(), sendMoneyPrice, setupCurrencyType, exchangeRate.getBuy(), valueAmount, valueCurrencyType, sendMoneyPrice, setupCurrencyType);

        TransactionSendMoneyExchangeTax sendMoneyExchangeTax = new TransactionSendMoneyExchangeTax(walletId, setupId, exchangeSendMoney, setupCurrencyType, exchangeSendMoneyType);
        TransactionSendMoneyExchange sendMoneyProcessExchange = new TransactionSendMoneyExchange(walletId, setupId, sendMoneyProcessTax, setupCurrencyType, sendMoneyExchangeTax);

        TransactionSendMoneyProcessTax processTax = new TransactionSendMoneyProcessTax(walletId, setupId, sendMoneyProcessTax, setupCurrencyType, sendMoneyProcessTaxType, sendMoneyProcessExchange);

        TransactionSendMoneyProcess sendMoneyProcess = new TransactionSendMoneyProcess(walletId, valueAmount, valueCurrencyType, valueExchange, sendMoneyPrice, sendMoneyProcessTax, exchangeSendMoney, setupCurrencyType, processTax, sendMoneyProcessExchange);
        sendMoneyProcess.calculateTotalReceiver();

        transactionSendMoney.setToTransactionProcess(sendMoneyProcess);
        transactionSendMoney.setToTotalPrice(sendMoneyTotalPrice);
        transactionSendMoney.setToTotalPriceCurrencyType(setupCurrencyType);
        transactionSendMoney.setToTotal(sendMoneyTotalPrice);
    }

    private static void otherProductCurrencyReceiver(
            TransactionRequest transactionRequest, ExchangeRate exchangeRate,
            Long walletId, Long setupId,
            CurrencyType setupCurrencyType, CurrencyType valueCurrencyType,
            Double requestProcessTax, TransactionTaxType requestProcessTaxType,
            Double exchangeRequest, TransactionTaxType exchangeRequestType,
            Double valueAmount, Double requestPrice,
            Double requestTotalPrice
    ) {

        TransactionRequestExchange valueExchange = new TransactionRequestExchange(walletId, setupId, exchangeRate.getId(), requestPrice, setupCurrencyType, exchangeRate.getBuy(), valueAmount, valueCurrencyType, requestPrice, setupCurrencyType);

        TransactionRequestExchangeTax requestExchangeTax = new TransactionRequestExchangeTax(walletId, setupId, exchangeRequest, setupCurrencyType, exchangeRequestType);
        TransactionRequestExchange requestProcessExchange = new TransactionRequestExchange(walletId, setupId, requestProcessTax, setupCurrencyType, requestExchangeTax);

        TransactionRequestProcessTax processTax = new TransactionRequestProcessTax(walletId, setupId, requestProcessTax, setupCurrencyType, requestProcessTaxType, requestProcessExchange);

        TransactionRequestProcess requestProcess = new TransactionRequestProcess(walletId, valueAmount, valueCurrencyType, valueExchange, requestPrice, requestProcessTax, exchangeRequest, setupCurrencyType, processTax, requestProcessExchange);
        requestProcess.calculateTotalReceiver();

        transactionRequest.setToTransactionProcess(requestProcess);
        transactionRequest.setToTotalPrice(requestTotalPrice);
        transactionRequest.setToTotalPriceCurrencyType(setupCurrencyType);
        transactionRequest.setToTotal(requestTotalPrice);
    }


}
