package taba.tabaServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {

		System.out.println("안녕하세요 타바 팀 입니다.");
		SpringApplication.run(JpashopApplication.class, args);
	}
}
