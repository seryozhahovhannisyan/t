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
             throw new InternalErrorException("Under construction");
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
        }
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
                productAmount, productCurrencyType , null,
                sendMoneyAmount, sendMoneyProcessTax, setupCurrencyType,
                sendMoneyPrice, sendMoneyProcessTaxPrice, walletCurrencyType,
                processTax, exchange);
        sendMoneyProcess.calculateTotalTransfer();

        transaction.setFromTransactionProcess(sendMoneyProcess);
        transaction.setFromTotal(sendMoneyTotalAmount);
        transaction.setFromTotalPrice(sendMoneyTotalPrice);
        transaction.setFromTotalPriceCurrencyType(walletCurrencyType);
    }




}
