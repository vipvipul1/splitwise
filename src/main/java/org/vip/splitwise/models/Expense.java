package org.vip.splitwise.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "EXPENSE")
public class Expense extends BaseModel {

    @Column(name = "DESCRIPTION")
    private String Description;

    @Column(name = "AMOUNT")
    private Long amount;

    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

    @OneToOne
    @JoinColumn(name = "CREATED_BY")
    private User createdBy;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "EXPENSE_TYPE")
    private ExpenseType expenseType;

    @JsonIgnoreProperties({"expense"})
    @OneToMany(mappedBy = "expense")
    private List<UserExpense> userExpenses;

    @JsonIgnoreProperties({"expenses"})
    @ManyToOne(targetEntity = Group.class)
    private Group group;
}
