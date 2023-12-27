package com.jpa.board.model.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String code;

    @ManyToMany(fetch = LAZY)
    @JoinTable(
            name="account_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id")
    )

    private List<Account> accounts = new ArrayList<>();
}

