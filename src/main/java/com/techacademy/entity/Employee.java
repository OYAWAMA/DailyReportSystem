package com.techacademy.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="employee")
public class Employee{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 20,nullable = false)
    private String name;
    @Column(nullable = false)
    private int deleteFlag;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy="employee")
    private Authentication authentication;

    /** レコードが削除される前に行なう処理 */
    @PreRemove
    @Transactional
    private void preRemove() {
        // 認証エンティティからuserを切り離す
        if (authentication!=null) {
            authentication.setEmployee(null);
        }
    }


}