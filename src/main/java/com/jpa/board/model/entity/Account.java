package com.jpa.board.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
public class Account {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String email;
    private String password;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    private String userName;
    private String rank;
    private String phoneNo;
    private String mobileNo;
    private String address;
    private String photoPath;
    private Integer enabled;

    private long writerId;
    private LocalDateTime writeDate;

    private LocalDateTime lastModifyDate;

    @ManyToMany(fetch = LAZY)
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    public void setPassword(String password) {
        this.password = password;
    }
}
