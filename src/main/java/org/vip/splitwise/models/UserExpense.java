package org.vip.splitwise.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "USER_EXPENSE")
public class UserExpense implements Serializable {
    @Id
    @GeneratedValue(generator = "user-expense-generator")
    @GenericGenerator(name = "user-expense-generator",
            parameters = @Parameter(name = "prefix", value = "ue"),
            type = SplitwiseIdGenerator.class)
    @Column(name = "ID")
    private String id;

    @Column(name = "PAID")
    private Double paid;

    @Column(name = "HAD_TO_PAY")
    private Double hadToPay;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @JsonIgnoreProperties({"userExpenses"})
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "EXPENSE_ID")
    private Expense expense;
}
