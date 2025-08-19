package com.otd.onetoday_back.health;

import com.otd.onetoday_back.Dummy;
import com.otd.onetoday_back.health.model.PostExerciseLogDto;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;


class ExerciseLogDummy extends Dummy {

    final int ADD_ROW_COUNT = 365;
    final int MEMBER_ID = 17;

    @Test
    void generate() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        ExerciseLogMapper exerciseLogMapper = sqlSession.getMapper(ExerciseLogMapper.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


        for (int i = 0; i < ADD_ROW_COUNT; i++) {

            // 운동 소요 시간: 10~120분
            int duration = faker.random().nextInt(10, 121);

            // 활동 에너지: 시간당 5~12 kcal
            int exerciseKcal = (int) Math.round(duration * (5 + faker.random().nextDouble() * 7));

            PostExerciseLogDto dto = PostExerciseLogDto.builder()
                    .memberId(MEMBER_ID)
                    .exerciseKcal(exerciseKcal)
                    .exerciseDatetime(LocalDateTime.ofInstant(
                            faker.timeAndDate().past(366, 0, TimeUnit.DAYS),
                            ZoneId.systemDefault()
                    ).format(formatter))
                    .exerciseDuration(duration)
                    .effortLevel(faker.random().nextInt(1, 11))
                    .exerciseId(faker.random().nextInt(1, 376))
                    .build();

            exerciseLogMapper.saveExerciseLog(dto);
            sqlSession.flushStatements();
        }
    }
}