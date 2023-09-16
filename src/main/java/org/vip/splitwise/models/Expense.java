package org.vip.splitwise.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "EXPENSE")
public class Expense implements Serializable {
    @Id
    @GeneratedValue(generator = "expense-generator")
    @GenericGenerator(name = "expense-generator",
            parameters = @Parameter(name = "prefix", value = "e"),
            type = SplitwiseIdGenerator.class)
    @Column(name = "ID")
    private String id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

    @ManyToOne
    @JoinColumn(name = "CREATED_BY")
    private User createdBy;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "EXPENSE_TYPE")
    private ExpenseType expenseType;

    @JsonIgnoreProperties({"expense"})
    @OneToMany(mappedBy = "expense", cascade = CascadeType.REMOVE)
    private List<UserExpense> userExpenses;

//    @JsonIgnoreProperties({"expenses"})
//    @ManyToOne
//    @JoinTable(name = "GROUP_EXPENSE",
//            joinColumns = {@JoinColumn(name = "EXPENSE_ID")},
//            inverseJoinColumns = {@JoinColumn(name = "GROUP_ID")})
//    private Group group;


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Expense expense = (Expense) o;
        return Objects.equals(id, expense.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
