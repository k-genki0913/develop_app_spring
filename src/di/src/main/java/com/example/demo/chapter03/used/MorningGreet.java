package com.example.demo.chapter03.used;

import org.springframework.stereotype.Component;

/**
 * Greet実装クラス<br>
 * 朝の挨拶を行う
 */
@Component
public class MorningGreet implements Greet {
    @Override
    public void greeting() {
        System.out.println("----------");
        System.out.println("おはようございます");
        System.out.println("----------");
    }
}
