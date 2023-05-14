package com.techacademy.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="authentication")
public class Authentication{

    /*管理権限用の列挙型 */
    public static enum Role {
        管理者, 一般
    }
    @Id
    @Column(length=20)
    private String code;
    @Column(length=255)
    private String password;
    @Column(length=10)
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne
    @JoinColumn(name="employee_id", referencedColumnName="id")
    private Employee employee;
}