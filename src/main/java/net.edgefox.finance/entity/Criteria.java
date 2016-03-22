package net.edgefox.finance.entity;

import javax.persistence.*;

/**
 * Created by ivan.lyutov on 22/03/16.
 */
@Entity
@Table(name = "criteria")
public class Criteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Category category;
    @Column(nullable = true, name = "payee_pattern")
    private String payeePattern;
    @Column(nullable = true, name = "reference_pattern")
    private String referencePattern;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getPayeePattern() {
        return payeePattern;
    }

    public void setPayeePattern(String payeePattern) {
        this.payeePattern = payeePattern;
    }

    public String getReferencePattern() {
        return referencePattern;
    }

    public void setReferencePattern(String referencePattern) {
        this.referencePattern = referencePattern;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Criteria criteria = (Criteria) o;

        if (!category.equals(criteria.category)) return false;
        if (!payeePattern.equals(criteria.payeePattern)) return false;
        return referencePattern.equals(criteria.referencePattern);

    }

    @Override
    public int hashCode() {
        int result = category.hashCode();
        result = 31 * result + payeePattern.hashCode();
        result = 31 * result + referencePattern.hashCode();
        return result;
    }
}
