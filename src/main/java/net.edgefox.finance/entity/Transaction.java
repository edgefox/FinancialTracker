package net.edgefox.finance.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by edgefox on 3/19/16.
 */
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false, name = "date")
    private Date date;
    @Column(nullable = false, name = "amount")
    private BigDecimal amount;
    @Column(nullable = false, name = "payee")
    private String payee;
    @Column(nullable = true, name = "particulars")
    private String particulars;
    @Column(nullable = true, name = "code")
    private String code;
    @Column(nullable = true, name = "reference")
    private String reference;
    @Column(nullable = true, name = "transaction_type")
    private String transactionType;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
