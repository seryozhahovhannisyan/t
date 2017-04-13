package com.connectto.wallet.util;

import com.connectto.general.exception.InvalidParameterException;
import com.connectto.general.model.WalletSetup;
import com.connectto.wallet.model.transaction.purchase.PurchaseTicket;
import com.connectto.wallet.model.wallet.ExchangeRate;
import com.connectto.wallet.model.wallet.Wallet;
import com.connectto.wallet.model.wallet.lcp.CurrencyType;
import com.connectto.wallet.model.wallet.lcp.TransactionPurchaseType;

import java.util.Date;

/**
 * Created by Serozh on 11/11/2016.
 */
public class DemoModel {

    public static synchronized PurchaseTicket initPurchase() throws InvalidParameterException {
        try {
            PurchaseTicket purchaseTicket = new PurchaseTicket();
            purchaseTicket.setItemId(Long.parseLong("1"));
            purchaseTicket.setPurchaseType(TransactionPurchaseType.typeOf("purchase"));
            purchaseTicket.setName("Name");
            purchaseTicket.setDescription("description");
            return purchaseTicket;
        } catch (Exception e) {
            throw new InvalidParameterException(e);
        }
    }

    public static synchronized Wallet initWallet(CurrencyType currencyType, String userId) {
        Wallet wallet = new Wallet();
        wallet.setId(100l +Long.parseLong(userId));
        wallet.setUserId(Long.parseLong(userId));
        wallet.setMoney(1000000d);
        wallet.setFrozenAmount(0d);
        wallet.setCurrencyType(currencyType);

        return wallet;
    }

    public static synchronized Wallet initWallet(CurrencyType currencyType) {
        return initWallet(currencyType,"1");
    }

    public static synchronized WalletSetup initWalletSetup(CurrencyType currencyType, int partitionId) {
        WalletSetup walletSetup = new WalletSetup();
        walletSetup.setId(7l);
        walletSetup.setPartitionId(partitionId);
        walletSetup.setCurrencyType(currencyType);
        walletSetup.setBalance(100000d);
        walletSetup.setAvailableRateValues("152, 4, 125");
        walletSetup.parseAvailableRates();

        walletSetup.setTransferMinFee(1d);
        walletSetup.setTransferMaxFee(1000d);
        walletSetup.setTransferFeePercent(10d);

        walletSetup.setExchangeTransferMinFee(1d);
        walletSetup.setExchangeTransferMaxFee(1000d);
        walletSetup.setExchangeTransferFeePercent(11d);

        walletSetup.setReceiverMinFee(1d);
        walletSetup.setReceiverMaxFee(1000d);
        walletSetup.setReceiverFeePercent(12d);

        walletSetup.setExchangeReceiverMinFee(1d);
        walletSetup.setExchangeReceiverMaxFee(1000d);
        walletSetup.setExchangeReceiverFeePercent(13d);

        return walletSetup;
    }

    public static synchronized ExchangeRate initExchangeRate() {
        ExchangeRate rate = new ExchangeRate();
        rate.setOneCurrency(CurrencyType.USD);
        rate.setToCurrency(CurrencyType.AMD);
        rate.setBuy(480d);
        rate.setSell(1/480d);
        rate.setUpdatedDate(new Date(System.currentTimeMillis()));
        rate.setId(452149L);
        return rate;
    }

    public static synchronized ExchangeRate initExchangeRate(CurrencyType toCurrencyType) {
        ExchangeRate rate = new ExchangeRate();
        rate.setOneCurrency(CurrencyType.USD);
        rate.setToCurrency(toCurrencyType);
        rate.setBuy(480d);
        rate.setSell(1/480d);
        rate.setUpdatedDate(new Date(System.currentTimeMillis()));
        rate.setId(452149L);
        return rate;
    }

    public static synchronized ExchangeRate initExchangeRate(CurrencyType toCurrencyType, Double buy) {
        ExchangeRate rate = new ExchangeRate();
        rate.setOneCurrency(CurrencyType.USD);
        rate.setToCurrency(toCurrencyType);
        rate.setBuy(buy);
        rate.setSell(1/buy);
        rate.setUpdatedDate(new Date(System.currentTimeMillis()));
        rate.setId(452149L);
        return rate;
    }



}
