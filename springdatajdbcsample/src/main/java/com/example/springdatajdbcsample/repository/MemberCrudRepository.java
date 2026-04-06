package com.example.springdatajdbcsample.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.springdatajdbcsample.entity.Member;

/**
 * Memberテーブル: リポジトリ
 */
public interface MemberCrudRepository extends CrudRepository<Member, Integer> {

}
