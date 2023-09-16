package org.vip.splitwise.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "GROUP_EXPENSE")
public class GroupExpense implements Serializable {
    @Id
    @GeneratedValue(generator = "group-expense-generator")
    @GenericGenerator(name = "group-expense-generator",
            parameters = @Parameter(name = "prefix", value = "ge"),
            type = SplitwiseIdGenerator.class)
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "EXPENSE_ID")
    private Expense expense;
}
