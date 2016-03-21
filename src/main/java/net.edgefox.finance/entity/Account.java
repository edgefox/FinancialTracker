package net.edgefox.finance.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by edgefox on 3/19/16.
 */
@Entity
@Table(name = "account")
public class Account {
    @Id
    @Column(nullable = false, name = "natural_id")
    private String naturalId;
    @Column(nullable = false, name = "name")
    private String name;
    @Column(nullable = false, name = "balance")
    private BigDecimal balance;

    public String getNaturalId() {
        return naturalId;
    }

    public void setNaturalId(String naturalId) {
        this.naturalId = naturalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return naturalId.equals(account.naturalId);

    }

    @Override
    public int hashCode() {
        return naturalId.hashCode();
    }
}
