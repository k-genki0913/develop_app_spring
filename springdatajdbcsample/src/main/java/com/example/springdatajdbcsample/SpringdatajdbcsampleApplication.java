package com.example.springdatajdbcsample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.springdatajdbcsample.entity.Member;
import com.example.springdatajdbcsample.repository.MemberCrudRepository;

/**
 * SpringBoot起動クラス
 */
@SpringBootApplication
public class SpringdatajdbcsampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringdatajdbcsampleApplication.class, args)
				.getBean(SpringdatajdbcsampleApplication.class).execute();
	}

	@Autowired
	MemberCrudRepository repository;

	/**
	 * 登録と全件取得を実行
	 */
	private void execute() {
		// 登録
		executeInsert();
		// 全件取得
		executeSelect();
	}

	/**
	 * 登録
	 */
	private void executeInsert() {
		// エンティティの作成(idは自動連番で設定するためnullを設定)
		Member member = new Member(null, "花子");
		// リポジトリを使用して登録を実施、結果を取得
		member = repository.save(member);
		// 結果を表示
		System.out.println("登録したデータ:" + member);
	}

	/**
	 * 全件取得
	 */
	private void executeSelect() {
		System.out.println("--- 全件取得します ---");
		// リポジトリを使用して全件取得を実施、結果を取得
		Iterable<Member> members = repository.findAll();
		for (Member member : members) {
			System.out.println(member);
		}
	}
}
