package com.andrey.todolist.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "refresh_token")
public class RefreshToken extends BaseEntity {
    @Column(name = "token")
    private String token;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "expired_date")
    private Date expiryDate;
}
