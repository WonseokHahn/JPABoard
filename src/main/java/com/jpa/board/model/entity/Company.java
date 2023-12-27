package com.jpa.board.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Getter
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String name;
    private String roleType;

    private String status;
    private String telNo;
    private boolean isDisplay;

    private long writerId;
    private LocalDateTime writeDate;

    private long lastModifierId;
    private LocalDateTime lastModifyDate;
}

