package com.example.demo.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.chapter03.used.Greet;

/**
 * Springboot起動クラス
 */
@SpringBootApplication
public class DiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiApplication.class, args)
				.getBean(DiApplication.class).execute();
	}

	/**
	 * 注入される箇所(インターフェース)
	 */
	@Autowired(required=true)
	Greet greet;

	/**
	 * 実行メソッド
	 */
	private void execute() {
		greet.greeting();
	}
}
