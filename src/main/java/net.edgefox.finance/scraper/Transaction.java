package net.edgefox.finance.scraper;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by edgefox on 3/19/16.
 */
public class Transaction {
    private Date date;
    private BigDecimal amount;
    private String payee;
    private String particulars;
    private String code;
    private String reference;
    private String transactionType;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;

        Transaction that = (Transaction) o;

        if (!amount.equals(that.amount)) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (!date.equals(that.date)) return false;
        if (particulars != null ? !particulars.equals(that.particulars) : that.particulars != null) return false;
        if (!payee.equals(that.payee)) return false;
        if (reference != null ? !reference.equals(that.reference) : that.reference != null) return false;
        if (!transactionType.equals(that.transactionType)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + amount.hashCode();
        result = 31 * result + payee.hashCode();
        result = 31 * result + (particulars != null ? particulars.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (reference != null ? reference.hashCode() : 0);
        result = 31 * result + transactionType.hashCode();
        return result;
    }
}
