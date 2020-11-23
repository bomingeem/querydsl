package study.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;

@SpringBootApplication
public class QuerydslApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuerydslApplication.class, args);
	}

	// 다음과 같이 JPAQueryFactory 를 스프링 빈으로 등록해서 주입받아 사용해도 된다
	//	@Bean
	//	JPAQueryFactory jpaQueryFactory(EntityManager em) {
	//		return new JPAQueryFactory(em);
	//	}

}
