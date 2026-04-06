package com.example.springdatajdbcsample.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Memberテーブル: エンティティ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    /** Member番号 */
    @Id
    private Integer id;
    /** Member指名 */
    private String name;
}
