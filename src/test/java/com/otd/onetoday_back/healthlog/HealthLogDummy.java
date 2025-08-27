//package com.otd.onetoday_back.healthlog;
//
//import com.otd.onetoday_back.Dummy;
//import com.otd.onetoday_back.health.HealthLogMapper;
//import com.otd.onetoday_back.health.model.PostHealthLogDto;
//import org.apache.ibatis.session.ExecutorType;
//import org.apache.ibatis.session.SqlSession;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.util.concurrent.TimeUnit;
//
//class HealthLogDummy extends Dummy {
//
//    final int ADD_ROW_COUNT = 365;
//    final int MEMBER_ID = 37;
//
//    @Test
//    void generate() {
//        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
//        HealthLogMapper healthLogMapper = sqlSession.getMapper(HealthLogMapper.class);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//
//        for(int i = 0; i < ADD_ROW_COUNT; i++) {
//
//            PostHealthLogDto dto = PostHealthLogDto.builder()
//                    .memberId(MEMBER_ID)
//                    .weight(faker.random().nextInt(55, 62))
//                    .height(faker.random().nextInt(160, 161))
//                    .systolicBp(faker.random().nextInt(100,130))
//                    .diastolicBp(faker.random().nextInt(60,90))
//                    .sugarLevel(faker.random().nextInt(85,140))
//                    .moodLevel(faker.random().nextInt(1,7))
//                    .sleepQuality(faker.random().nextInt(1,5))
//                    .healthlogDatetime( LocalDateTime.ofInstant(
//                            faker.timeAndDate().past(365, 0, TimeUnit.DAYS),
//                            ZoneId.systemDefault()
//                    ).format(formatter))
//                    .build();
//            healthLogMapper.saveHealthLog(dto);
//            sqlSession.flushStatements();
//        }
//    }
//}