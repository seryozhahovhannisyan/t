package com.connectto.wallet.util.currency;

import com.connectto.general.exception.InternalErrorException;
import com.connectto.general.model.WalletSetup;
import com.connectto.wallet.model.transaction.merchant.deposit.*;
import com.connectto.wallet.model.transaction.merchant.transfer.MerchantTransferExchange;
import com.connectto.wallet.model.transaction.merchant.transfer.MerchantTransferExchangeTax;
import com.connectto.wallet.model.transaction.merchant.transfer.MerchantTransferTax;
import com.connectto.wallet.model.transaction.merchant.transfer.MerchantTransferTransaction;
import com.connectto.wallet.model.transaction.purchase.*;
import com.connectto.wallet.model.transaction.request.*;
import com.connectto.wallet.model.transaction.sendmoney.*;
import com.connectto.wallet.model.transaction.transfer.TransferExchange;
import com.connectto.wallet.model.transaction.transfer.TransferExchangeTax;
import com.connectto.wallet.model.transaction.transfer.TransferTax;
import com.connectto.wallet.model.transaction.transfer.TransferTransaction;
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
            otherWalletCurrencyTransfer(
                    (TransactionPurchase) transaction, transactionState, currentDate, selectedExchangeRate,
                    walletId, setupId,
                    walletCurrencyType, setupCurrencyType,
                    processTax, processTaxPrice, processTaxType,
                    exchange, exchangePrice, exchangeType,
                    amount, price,
                    totalAmount, totalPrice
            );
        }  else if (TransactionSendMoney.class.isInstance(transaction)) {
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

        if (totalAmount <= 0) {
            throw new InternalErrorException(Constant.MESSAGE_LESS_REQUEST);
        }
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
        } else if (TransactionRequest.class.isInstance(transaction)) {
            otherWalletCurrencyReceiver(
                    (TransactionRequest) transaction, selectedExchangeRate,
                    walletId, setupId,
                    walletCurrencyType, setupCurrencyType,
                    processTax, processTaxPrice, processTaxType,
                    exchange, exchangePrice, exchangeType,
                    amount, price,
                    totalAmount, totalPrice
            );
        } else if (TransactionDeposit.class.isInstance(transaction)) {

            Map<String, Object> depositTaxTypeMap = TaxCalculator.calculateDepositTax(walletSetup, amount);//100
            TransactionTaxType depositTaxType = (TransactionTaxType) depositTaxTypeMap.get(Constant.TAX_TYPE_KEY);
            Double depositTaxAmount = (Double) depositTaxTypeMap.get(Constant.TAX_KEY);
            Double depositTaxPrice = depositTaxAmount * rateAmount;//3 USD

            otherWalletCurrencyReceiver(
                    (TransactionDeposit) transaction, currentDate, transactionState, selectedExchangeRate,
                    walletId, setupId,
                    walletCurrencyType, setupCurrencyType,
                    processTax, processTaxPrice, processTaxType,
                    depositTaxAmount, depositTaxPrice, depositTaxType,
                    exchange, exchangePrice, exchangeType,
                    amount, price
            );
        } else if (MerchantTransferTransaction.class.isInstance(transaction)) {
            otherWalletCurrencyReceiver(
                    (MerchantTransferTransaction) transaction, currentDate, selectedExchangeRate,
                    walletId, setupId,
                    walletCurrencyType, setupCurrencyType,
                    exchange, exchangePrice, exchangeType,
                    amount, price
            );
        } else if (TransferTransaction.class.isInstance(transaction)) {
            otherWalletCurrencyReceiver(
                    (TransferTransaction) transaction, currentDate, selectedExchangeRate,
                    walletId, setupId,
                    walletCurrencyType, setupCurrencyType,
                    exchange, exchangePrice, exchangeType,
                    amount, price
            );
        }
    }


    private static void otherWalletCurrencyTransfer(
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
                purchaseProcessTaxPrice, walletCurrencyType,
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


    private static void otherWalletCurrencyReceiver(
            TransactionDeposit transactionDeposit, Date currentDate, TransactionState transactionState, ExchangeRate selectedExchangeRate,
            Long walletId, Long setupId,
            CurrencyType walletCurrencyType, CurrencyType setupCurrencyType,
            Double receiverProcessTaxAmount, Double receiverProcessTaxPrice, TransactionTaxType receiverProcessTaxType,
            Double depositTaxAmount, Double depositTaxPrice,  TransactionTaxType depositTaxType ,
            Double exchangeDepositTaxAmount, Double exchangeDepositTaxPrice, TransactionTaxType exchangeDepositTaxType,
            Double depositAmount, Double transferPrice
    ) throws InternalErrorException {

        Double rateAmount = selectedExchangeRate.getBuy();
        Long rateId = selectedExchangeRate.getId();

        MerchantDeposit merchantDeposit = transactionDeposit.getMerchantDeposit();
        MerchantDepositTax merchantDepositTax = merchantDeposit.getMerchantDepositTax();


        Double merchantDepositTaxAmount = merchantDepositTax.getDepositTax();
        Double rate = selectedExchangeRate.getBuy();
        CurrencyType rateCurrencyType = selectedExchangeRate.getToCurrency();
        Double merchantDepositTaxPrice = merchantDepositTaxAmount * rate;

        TransactionDepositExchange merchantDepositTaxExchange = new TransactionDepositExchange(walletId, setupId, rateId, currentDate, merchantDepositTaxAmount, setupCurrencyType, rate, rateCurrencyType, merchantDepositTaxPrice, rateCurrencyType);
        merchantDepositTax.setExchange(merchantDepositTaxExchange);


        Double depositPrice = depositAmount * rateAmount;//48000AMD

        TransactionDepositExchange depositExchange = new TransactionDepositExchange(walletId, setupId, rateId, currentDate, depositAmount, setupCurrencyType, rateAmount, walletCurrencyType, depositPrice, walletCurrencyType);

        TransactionDepositExchange receiverProcessExchange = new TransactionDepositExchange(walletId, setupId, rateId, currentDate, receiverProcessTaxAmount, setupCurrencyType, rateAmount, walletCurrencyType, receiverProcessTaxPrice, walletCurrencyType);
        TransactionDepositProcessTax receiverProcessTax = new TransactionDepositProcessTax(currentDate, walletId, setupId, receiverProcessTaxAmount, setupCurrencyType, receiverProcessTaxPrice, walletCurrencyType, receiverProcessTaxType, receiverProcessExchange);

        TransactionDepositExchange depositTaxExchange = new TransactionDepositExchange(walletId, setupId, rateId, currentDate, depositTaxAmount, setupCurrencyType, rateAmount, walletCurrencyType, depositTaxPrice, walletCurrencyType);
        WalletSetupDepositTax setupDepositTax = new WalletSetupDepositTax(currentDate, walletId, setupId, depositTaxAmount, setupCurrencyType, depositTaxPrice, walletCurrencyType, depositTaxType, depositTaxExchange);

        TransactionDepositExchangeTax exchangeDepositTax = new TransactionDepositExchangeTax(currentDate, walletId, setupId, exchangeDepositTaxAmount, setupCurrencyType, exchangeDepositTaxPrice, walletCurrencyType, exchangeDepositTaxType);
        TransactionDepositExchange exchangeDeposit = new TransactionDepositExchange(walletId, setupId, rateId, currentDate, exchangeDepositTaxAmount, setupCurrencyType, rateAmount, walletCurrencyType, exchangeDepositTaxPrice, walletCurrencyType, exchangeDepositTax);

        Double setupTotalProcess = receiverProcessTaxAmount + depositTaxAmount;//2+3
        Double setupTotalAmount = setupTotalProcess + exchangeDepositTaxAmount;//5+4
        Double totalTaxAmount = merchantDepositTaxAmount + setupTotalAmount;
        Double totalTaxPrice = merchantDepositTaxPrice + receiverProcessTaxPrice + depositTaxPrice + exchangeDepositTaxPrice;

        Double walletTotalAmount = depositAmount + totalTaxAmount;
        Double walletTotalPrice = depositPrice + totalTaxPrice;

        TransactionDepositTax depositTax = new TransactionDepositTax(currentDate, walletId, setupId, receiverProcessTax, setupDepositTax, merchantDepositTax, exchangeDepositTax);
        TransactionDepositProcess depositProcess =
                new TransactionDepositProcess(transactionState, currentDate, walletId, setupId,
                        depositAmount, setupCurrencyType,
                        depositPrice, walletTotalPrice, walletCurrencyType,
                        depositAmount, setupTotalProcess, setupCurrencyType,
                        receiverProcessTax, setupDepositTax, exchangeDeposit);

        transactionDeposit.setProcessStart(depositProcess);
        transactionDeposit.setWalletTotalPrice(walletTotalPrice);
        transactionDeposit.setWalletTotalPriceCurrencyType(walletCurrencyType);

        transactionDeposit.setSetupTotalAmount(setupTotalAmount);
        transactionDeposit.setSetupTotalAmountCurrencyType(setupCurrencyType);
        transactionDeposit.setTax(depositTax);
    }


    private static void otherWalletCurrencyReceiver(
            MerchantTransferTransaction transfer, Date currentDate, ExchangeRate selectedExchangeRate,
            Long walletId, Long setupId,
            CurrencyType walletCurrencyType, CurrencyType setupCurrencyType,
            Double exchangeTransfer, Double exchangeTransferPrice, TransactionTaxType exchangeTransferType,
            Double transferAmount, Double transferPrice
    ) throws InternalErrorException {

        Double totalPrice = transferPrice - exchangeTransferPrice;
        Double totalAmount = exchangeTransfer;

        if (totalPrice <= 0) {
            throw new InternalErrorException(Constant.MESSAGE_LESS_MONEY);
        }

        Double rateAmount = selectedExchangeRate.getBuy();
        Long rateId = selectedExchangeRate.getId();

        MerchantTransferExchangeTax transferExchangeTax = new MerchantTransferExchangeTax(currentDate, walletId, setupId, exchangeTransfer, setupCurrencyType, exchangeTransferPrice, walletCurrencyType, exchangeTransferType);
        MerchantTransferExchange transferExchange = new MerchantTransferExchange(walletId, setupId, rateId, currentDate, transferAmount, setupCurrencyType, rateAmount, walletCurrencyType, transferPrice, walletCurrencyType, transferExchangeTax);

        MerchantTransferTax transferTax = new MerchantTransferTax(currentDate, walletId, setupId, transferExchangeTax);

        transfer.setMerchantTransferExchange(transferExchange);
        transfer.setWalletTotalPrice(totalPrice);
        transfer.setWalletTotalPriceCurrencyType(walletCurrencyType);

        transfer.setSetupTotalAmount(totalAmount);
        transfer.setSetupTotalAmountCurrencyType(setupCurrencyType);
        transfer.setTax(transferTax);
    }


    private static void otherWalletCurrencyReceiver(
            TransferTransaction transfer, Date currentDate, ExchangeRate selectedExchangeRate,
            Long walletId, Long setupId,
            CurrencyType walletCurrencyType, CurrencyType setupCurrencyType,
            Double exchangeTransfer, Double exchangeTransferPrice, TransactionTaxType exchangeTransferType,
            Double transferAmount, Double transferPrice
    ) throws InternalErrorException {

        Double totalPrice = transferPrice - exchangeTransferPrice;
        Double totalAmount = transferAmount - exchangeTransfer;

        if (totalPrice <= 0) {
            throw new InternalErrorException(Constant.MESSAGE_LESS_MONEY);
        }

        Double rateAmount = selectedExchangeRate.getBuy();
        Long rateId = selectedExchangeRate.getId();

        TransferExchangeTax transferExchangeTax = new TransferExchangeTax(currentDate, walletId, setupId, exchangeTransfer, setupCurrencyType, exchangeTransferPrice, walletCurrencyType, exchangeTransferType);
        TransferExchange transferExchange = new TransferExchange(walletId, setupId, rateId, currentDate, transferAmount, setupCurrencyType, rateAmount, walletCurrencyType, transferPrice, walletCurrencyType, transferExchangeTax);

        TransferTax transferTax = new TransferTax(currentDate, walletId, setupId, transferExchangeTax);

        transfer.setTransferExchange(transferExchange);
        transfer.setWalletTotalPrice(totalPrice);
        transfer.setWalletTotalPriceCurrencyType(walletCurrencyType);

        transfer.setSetupTotalAmount(totalAmount);
        transfer.setSetupTotalAmountCurrencyType(setupCurrencyType);
        transfer.setTax(transferTax);
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

        TransactionSendMoneyExchange valueExchange = new TransactionSendMoneyExchange(walletId, setupId, rateId, sendMoneyAmount, setupCurrencyType, rateAmount, sendMoneyPrice, walletCurrencyType);

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

        TransactionRequestExchange valueExchange = new TransactionRequestExchange(walletId, setupId, rateId, requestAmount, setupCurrencyType, rateAmount, requestPrice, walletCurrencyType);

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

        TransactionSendMoneyExchange valueExchange = new TransactionSendMoneyExchange(walletId, setupId, rateId, sendMoneyAmount, setupCurrencyType, rateAmount, sendMoneyPrice, walletCurrencyType);

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
        sendMoneyProcess.calculateTotalReceiver();

        transaction.setToTransactionProcess(sendMoneyProcess);
        transaction.setToTotal(sendMoneyTotalAmount);
        transaction.setToTotalPrice(sendMoneyTotalPrice);
        transaction.setToTotalPriceCurrencyType(walletCurrencyType);
    }

    private static void otherWalletCurrencyReceiver(
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

        TransactionRequestExchange valueExchange = new TransactionRequestExchange(walletId, setupId, rateId, requestAmount, setupCurrencyType, rateAmount, requestPrice, walletCurrencyType);

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
        requestProcess.calculateTotalReceiver();

        transaction.setToTransactionProcess(requestProcess);
        transaction.setToTotal(requestTotalAmount);
        transaction.setToTotalPrice(requestTotalPrice);
        transaction.setToTotalPriceCurrencyType(walletCurrencyType);
    }


}
