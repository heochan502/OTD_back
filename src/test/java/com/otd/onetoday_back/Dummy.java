package com.otd.onetoday_back;

import net.datafaker.Faker;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.util.Locale;


// tdd, 마이바티스만 객체화 사용하겠다. (스프링부트 컨테이너는 안 올린다)
// Slice Text(레이어 테스트), 예를 들어 컨트롤러만 테스트, 서비스만 테스트
@MybatisTest
// 원래 경량 데이터 베이스로 테스트가 진행되는데 그걸 비활성화
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// 테스트 끝나면 롤백되는데 그럼 더미 데이터 생성이 안 돼서 비활성화
@Rollback(false)
public class Dummy {
//    test 에서는 객체생성이 안 됨.
    @Autowired  // DI 받는 애노테이션 (필드주입 방식)
    protected SqlSessionFactory sqlSessionFactory;
    protected Faker faker = new Faker();
    protected Faker koFaker = new Faker(new Locale("ko")); // 한글
    protected Faker enFaker = new Faker(new Locale("en")); // 영어

}
