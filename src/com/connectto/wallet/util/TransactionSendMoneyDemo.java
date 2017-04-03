package com.connectto.wallet.util;

import com.connectto.general.exception.InternalErrorException;
import com.connectto.general.exception.InvalidParameterException;
import com.connectto.general.exception.PermissionDeniedException;
import com.connectto.general.model.WalletSetup;
import com.connectto.wallet.model.transaction.sendmoney.TransactionSendMoney;
import com.connectto.wallet.model.wallet.ExchangeRate;
import com.connectto.wallet.model.wallet.Wallet;
import com.connectto.wallet.model.wallet.lcp.CurrencyType;
import com.connectto.wallet.model.wallet.lcp.TransactionState;
import com.connectto.wallet.model.wallet.lcp.TransactionTaxType;
import com.connectto.wallet.model.wallet.lcp.TransactionType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Serozh on 2/15/16.
 */
public class TransactionSendMoneyDemo {


    //Form Fields
    private String userId;
    private String message;
    static ExchangeRate selectedExchangeRate;


    public static TransactionSendMoney initDemoTransactionSendMoney(
            Double productAmount,
            CurrencyType productCurrencyType,
            CurrencyType from, CurrencyType to, CurrencyType setup
    ) throws InvalidParameterException, PermissionDeniedException, InternalErrorException {

        Wallet fromWallet = DemoModel.initWallet(from, "4");
        Wallet toWallet = DemoModel.initWallet(to, "14");
        WalletSetup walletSetup = DemoModel.initWalletSetup(setup, 1);

        selectedExchangeRate = DemoModel.initExchangeRate();
        TransactionSendMoney sendMoney = createTransaction(productAmount, productCurrencyType, fromWallet, toWallet, walletSetup);
        sendMoney.createTax();
        return sendMoney;
    }


    protected static TransactionSendMoney createTransaction(
            Double productAmount, CurrencyType productCurrencyType,
            Wallet fromWallet, Wallet toWallet, WalletSetup walletSetup
    ) throws InternalErrorException, PermissionDeniedException {

        //<editor-fold desc="initBlock">
        Date currentDate = new Date(System.currentTimeMillis());

        if (fromWallet == null || toWallet == null) {
            //throw new InternalErrorException(MESSAGE_UNKNOWN_WALLETS);
        }

        CurrencyType fct = fromWallet.getCurrencyType();
        CurrencyType tct = toWallet.getCurrencyType();

        boolean isProductCurrencyTypeSupported = walletSetup.isCurrencyTypeSupported(productCurrencyType.getId());
        boolean isFromCurrencyTypeSupported = walletSetup.isCurrencyTypeSupported(fct.getId());
        boolean isToCurrencyTypeSupported = walletSetup.isCurrencyTypeSupported(tct.getId());

        if (!isProductCurrencyTypeSupported || !isFromCurrencyTypeSupported || !isToCurrencyTypeSupported) {
            throw new PermissionDeniedException();
        }

        CurrencyType setupCurrencyType = walletSetup.getCurrencyType();

        int setupCurrencyTypeId = setupCurrencyType.getId();
        int productCurrencyTypeId = productCurrencyType.getId();

        TransactionSendMoney transaction = new TransactionSendMoney();
        transaction.setProductAmount(productAmount);
        transaction.setProductCurrencyType(productCurrencyType);
        transaction.setState(TransactionState.PENDING);
        transaction.setOpenedAt(currentDate);
        transaction.setWalletSetupId(walletSetup.getId());
        transaction.setFromWalletId(fromWallet.getId());
        transaction.setFromWallet(fromWallet);
        transaction.setToWalletId(toWallet.getId());
        transaction.setToWallet(toWallet);

        Double currentBalance = fromWallet.getMoney();
        Double frozenAmount = fromWallet.getFrozenAmount();


        Double availableAmount = currentBalance - frozenAmount;
        if (availableAmount <= 0) {
            throw new InternalErrorException(Constant.MESSAGE_LESS_MONEY);
        }
        //</editor-fold>

        if (productCurrencyTypeId == setupCurrencyTypeId) {
            CurrencyType fromCurrencyType = fromWallet.getCurrencyType();
            int fromCurrencyTypeId = fromCurrencyType.getId();
            if (productCurrencyTypeId == fromCurrencyTypeId) {
                TransactionCurrencyEqual.equalCurrencyTransfer(transaction, null, currentDate, fromWallet, walletSetup, productAmount);
            } else {
                TransactionCurrencyOther.otherWalletCurrencyTransfer(transaction, null, currentDate, selectedExchangeRate, fromWallet, walletSetup, productAmount);
            }

            CurrencyType toCurrencyType = toWallet.getCurrencyType();
            int toCurrencyTypeId = toCurrencyType.getId();

            if (productCurrencyTypeId == toCurrencyTypeId) {
                TransactionCurrencyEqual.equalCurrencyReceiver(transaction, null, currentDate, toWallet, walletSetup, productAmount);
            } else {
                TransactionCurrencyOther.otherWalletCurrencyReceiver(transaction, null, currentDate, selectedExchangeRate, fromWallet, walletSetup, productAmount);
            }


        } else {

            if (fromWallet != null) {

                Long fromWalletId = fromWallet.getId();

                CurrencyType fromCurrencyType = fromWallet.getCurrencyType();

                int fromCurrencyTypeId = fromCurrencyType.getId();

                if (productCurrencyTypeId == fromCurrencyTypeId) {
                    //equalsProductFromCurrencies(transaction, fromWallet, walletSetup, productAmount, availableAmount, currentDate);
                } else {

                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("isDefault", 1);
                    params.put("toCurrency", productCurrencyType.getId());
                    params.put("oneCurrency", setupCurrencyTypeId);
                    params.put("lastOne", 1);

                    //ExchangeRate selectedExchangeRate = getDefaultExchangeRate(productCurrencyType, setupCurrencyType);
                    if (selectedExchangeRate == null) {
                        String msg = "Could not found exchangeRates, with params, params = " + params;
                        throw new InternalErrorException(msg);
                    }

                    Double rateAmount = selectedExchangeRate.getBuy();

                    Double overlayProduct = productAmount / rateAmount;

                    Map<String, Object> overlayExchangeMap = null;//calculateExchangeTransferFee(walletSetup, overlayProduct);

                    Double overlayProductExchangeTax = null;//(Double) overlayExchangeMap.get(TAX_KEY);
                    TransactionTaxType overlayProductTaxType = null;//(TransactionTaxType) overlayExchangeMap.get(TAX_TYPE_KEY);

                    Double overlayProductTotal = overlayProduct + overlayProductExchangeTax;

                    //Exchange overlayProductExchange = new Exchange(productAmount, productCurrencyType, rateAmount, setupCurrencyType, overlayProduct, setupCurrencyType, currentDate, fromWalletId, selectedExchangeRate.getId());

                    if (setupCurrencyTypeId == fromCurrencyTypeId) {
//                        TransactionTax overlayTax = new TransactionTax(fromWalletId, walletSetup.getId(), currentDate,
//                                null, null,
//                                null, null, null, null,
//                                null, null, null, null,
//                                overlayProductExchangeTax, overlayProductExchangeTax, overlayProductTaxType, null,
//                                null, false);

                        //overlayTransactionTo(transaction, overlayTax, overlayProductExchange, fromWallet, walletSetup, overlayProduct, currentDate);

                    } else {

                        Map<String, Object> paramsPrice = new HashMap<String, Object>();
                        paramsPrice.put("isDefault", 1);
                        paramsPrice.put("toCurrency", setupCurrencyTypeId);
                        paramsPrice.put("oneCurrency", fromCurrencyTypeId);
                        paramsPrice.put("lastOne", 1);


                        ExchangeRate selectedExchangeRatePrice = selectedExchangeRate;
                        //ExchangeRate selectedExchangeRatePrice = exchangeRatesPrice.get(0);
                        Double rateAmountPrice = selectedExchangeRatePrice.getBuy();

                        Double overlayProductPrice = overlayProduct / rateAmountPrice;
                        Double overlayProductTaxPrice = overlayProductExchangeTax / rateAmountPrice;

//                        Exchange overlayProductPriceExchange = new Exchange(overlayProductPrice, fromCurrencyType, rateAmountPrice, fromCurrencyType, overlayProduct, setupCurrencyType, currentDate, fromWalletId, selectedExchangeRate.getId());
//                        Exchange overlayProductTaxPriceExchange = new Exchange(overlayProductTaxPrice, fromCurrencyType, rateAmountPrice, fromCurrencyType, overlayProductExchangeTax, setupCurrencyType, currentDate, fromWalletId, selectedExchangeRate.getId());
//
//                        TransactionTax overlayTax = new TransactionTax(fromWalletId, walletSetup.getId(), currentDate,
//                                null, null,
//                                null, null, null, null,
//                                null, null, null, null,
//                                overlayProductExchangeTax, overlayProductTaxPrice, overlayProductTaxType, overlayProductTaxPriceExchange,
//                                null, false);
//
//                        overlayTransactionFrom(transaction, overlayTax, overlayProductExchange, overlayProductPriceExchange, fromWallet, walletSetup, overlayProduct, availableAmount, currentDate);

                    }
                }
            }

            if (toWallet != null) {

                Long toWalletId = toWallet.getId();

                CurrencyType toCurrencyType = toWallet.getCurrencyType();
                int toCurrencyTypeId = toCurrencyType.getId();
                if (productCurrencyTypeId == toCurrencyTypeId) {
//                    equalsProductToCurrencies(transaction, toWallet, walletSetup, productAmount, currentDate);
                } else {

                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("isDefault", 1);
                    params.put("toCurrency", productCurrencyType.getId());
                    params.put("oneCurrency", setupCurrencyTypeId);
                    params.put("lastOne", 1);


                    //ExchangeRate selectedExchangeRate = exchangeRates.get(0);
                    Double rateAmount = selectedExchangeRate.getBuy();

                    Double overlayProduct = productAmount / rateAmount;

                    Map<String, Object> overlayExchangeMap = null;//calculateExchangeReceiverFee(walletSetup, overlayProduct);
                    Double overlayProductExchangeTax = null;//(Double) overlayExchangeMap.get(TAX_KEY);
                    TransactionTaxType overlayProductExchangeTaxType = null;//(TransactionTaxType) overlayExchangeMap.get(TAX_TYPE_KEY);

                    Double overlayExchangeTaxPrice = overlayProductExchangeTax * rateAmount;
                    //Exchange overlayExchange = new Exchange(overlayProductExchangeTax, setupCurrencyType, rateAmount, setupCurrencyType, overlayExchangeTaxPrice, productCurrencyType, currentDate, toWalletId, selectedExchangeRate.getId());


                    if (setupCurrencyTypeId == toCurrencyTypeId) {
//                        TransactionTax overlayTax = new TransactionTax(toWalletId, walletSetup.getId(), currentDate,
//                                null, null,
//                                null, null, null, null,
//                                null, null, null, null,
//                                overlayProductExchangeTax, overlayProductExchangeTax, overlayProductExchangeTaxType, null,
//                                null, false);
//
//                        overlayTransactionFrom(transaction, overlayTax, overlayExchange, fromWallet, walletSetup, overlayProduct, availableAmount, currentDate);

                    } else {


                        ExchangeRate selectedExchangeRatePrice = selectedExchangeRate;
                        ;
                        //ExchangeRate selectedExchangeRatePrice = exchangeRatesPrice.get(0);
                        Double rateAmountPrice = selectedExchangeRatePrice.getBuy();

                        Double overlayProductPrice = overlayProduct / rateAmountPrice;
                        Double overlayProductTaxPrice = overlayProductExchangeTax / rateAmountPrice;

//                        Exchange overlayProductPriceExchange = new Exchange(overlayProductPrice, toCurrencyType, rateAmountPrice, toCurrencyType, overlayProduct, setupCurrencyType, currentDate, toWalletId, selectedExchangeRate.getId());
//                        Exchange overlayProductTaxPriceExchange = new Exchange(overlayProductTaxPrice, toCurrencyType, rateAmountPrice, toCurrencyType, overlayProductExchangeTax, setupCurrencyType, currentDate, toWalletId, selectedExchangeRate.getId());
//
//                        TransactionTax overlayTax = new TransactionTax(toWalletId, walletSetup.getId(), currentDate,
//                                null, null,
//                                null, null, null, null,
//                                null, null, null, null,
//                                overlayProductExchangeTax, overlayProductTaxPrice, overlayProductExchangeTaxType, overlayProductTaxPriceExchange,
//                                null, false);
//
//                        overlayTransactionTo(transaction, overlayTax, overlayExchange, overlayProductPriceExchange, toWallet, walletSetup, overlayProduct, currentDate);

                    }

                    /*TransactionTax transactionTax = new TransactionTax(toWalletId, walletSetup.getId(), currentDate,
                            null, null,
                            null, null, null, null,
                            null, null, null, null,
                            overlayExchangeTax, overlayExchangeTaxPrice, overlayExchangeTaxType, overlayExchange,
                            null, false);

                    overlayTransactionTo(transaction, transactionTax, toWallet, walletSetup, productAmount, currentDate);*/
                    //throw new PermissionDeniedException(MESSAGE_NOT_SUPPORTED_CURRENCY);
                }
            }
        }

        transaction.setTransactionType(TransactionType.WALLET);
        return transaction;
    }


}
