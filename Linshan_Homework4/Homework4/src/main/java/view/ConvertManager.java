/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import controller.CurrencyConvert;
import model.*;

/**
 *
 * @author Rsheep
 */
@Named("conMan")
@ConversationScoped
public class ConvertManager implements Serializable {
    @EJB
    private CurrencyConvert currencyConvert;
    private convertDTO currentConvert;
    private String fromCurrency;
    private String toCurrency;
    private float inputAmount;
    private float outputAmount;
    private String tp;
    private float rate;
    //private Integer transactionAmount;
    private Integer searchedAcct;
    private Exception transactionFailure;
    @Inject
    private Conversation conversation;

    private void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    private void stopConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }

    private void handleException(Exception e) {
        stopConversation();
        e.printStackTrace(System.err);
        transactionFailure = e;
    }


    public boolean getSuccess() {
        return transactionFailure == null;
    }

    /**
     * Returns the latest thrown exception.
     */
    public Exception getException() {
        return transactionFailure;
    }

    /**
     * Searches for the account specified by the latest call to
     * <code>setSearchedAcct</code>.
     */
    public void findRate() {
        try {
            startConversation();
            transactionFailure = null;
            tp = fromCurrency + "-" + toCurrency;
            currentConvert = currencyConvert.findConvert(tp);
            rate = currentConvert.getRate();
            
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Deposits the amount set by the latest call to
     * <code>setTransactionAmount</code> from the account specified by
     * <code>currentAcct.getAcctNo()</code>.
     */
    public void convert() {
        try {
            transactionFailure = null;
            findRate();
            outputAmount = inputAmount * rate;
        } catch (Exception e) {
            handleException(e);
        }
    }

    public float getOutputAmount(){
        return outputAmount;
    }

    /**
     * Set the value of searchedAcct
     *
     * @param searchedAcct new value of searchedAcct
     */
   /* public void setSearchedAcct(Integer searchedAcct) {
        this.searchedAcct = searchedAcct;
    }*/

    /**
     * Never used but JSF does not support write-only properties.
     */
    /*public Integer getSearchedAcct() {
        return null;
    }*/

    /**
     * Never used but JSF does not support write-only properties.
     */
    /*public Integer getTransactionAmount() {
        return null;
    }*/

    /**
     * Set the value of newAccountBalance
     *
     * @param newAccountBalance new value of newAccountBalance
     */
    public void setInputAmount(float inputAmount) {
        this.inputAmount = inputAmount;
    }

    /**
     * Never used but JSF does not support write-only properties.
     */
    public float getInputAmount() {
        return inputAmount;
    }

    /**
     * Set the value of newAccountHolderLastName
     *
     * @param newAccountHolderLastName new value of newAccountHolderLastName
     */
    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    /**
     * Never used but JSF does not support write-only properties.
     */
    public String getToCurrency() {
        return null;
    }


    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    /**
     * Never used but JSF does not support write-only properties.
     */
    public String getfromCurrency() {
        return null;
    }

    /**
     * Get the value of currentAcct
     *
     * @return the value of currentAcct
     */
    public convertDTO getCurrentAcct() {
        return currentConvert;
    }
}
