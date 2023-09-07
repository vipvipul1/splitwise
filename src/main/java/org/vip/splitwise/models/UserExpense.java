package org.vip.splitwise.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "USER_EXPENSE")
public class UserExpense extends BaseModel {
    @Column(name = "PAID")
    private Long paid;

    @Column(name = "HAD_TO_PAY")
    private Long hadToPay;

    @OneToOne
    @JoinColumn(name = "USER")
    private User user;

    @JsonIgnoreProperties({"userExpenses"})
    @ManyToOne
    private Expense expense;
}
