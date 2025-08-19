package com.otd.onetoday_back.account;


import com.otd.onetoday_back.account.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccountService {

    private final AccountMapper accountMapper;

    public int join(AccountJoinReq req) {
        String hashedPw = BCrypt.hashpw(req.getMemberPw(), BCrypt.gensalt());
        AccountJoinReq changedReq = new AccountJoinReq(
                req.getMemberId(),
                hashedPw,
                req.getEmail(),
                req.getName(),
                req.getBirthDate(),
                req.getMemberNick(),
                req.getGender()
        );
        log.info(" changed2  : {}" ,req.getMemberId());
        return accountMapper.save(changedReq);
    }

    public AccountLoginRes login(AccountLoginReq req)
    {
        AccountLoginRes res = accountMapper.findByLogin(req);
        log.info("AccountLoginRes: memberNoLogin={}, memberPw={}", res.getMemberNoLogin(), res.getMemberPw());
        log.info("id:" + req.getMemberId());
        if( res == null ||!BCrypt.checkpw(req.getMemberPw(), res.getMemberPw()))
        {
            return null;
        }
        return res;
    }
    public AccountProfileRes updateProfile(int memberNoLogin, memberUpdateDto dto) {
        AccountProfileRes currentProfile = accountMapper.findProfileById(memberNoLogin);
        if (currentProfile == null) {
            return null;
        }
        dto.setMemberNoLogin(memberNoLogin);

        int result = accountMapper.updateProfile(dto);
        if (result > 0) {
            return accountMapper.findProfileById(memberNoLogin);
        }
        return null;
    }
    public AccountProfileRes getProfile(int memberNoLogin) {
        return accountMapper.findProfileById(memberNoLogin);
    }

    public boolean existsByMemberId(String memberId) {
        return accountMapper.existsByMemberId(memberId) > 0;
    }

    public boolean existsByEmail(String email) {
        return accountMapper.existsByEmail(email) > 0;
    }

    public boolean existsByMemberNick(String memberNick) {
        return accountMapper.existsByMemberNick(memberNick) > 0;
    }
    public int changePassword(int memberNoLogin, PasswordChangeDto dto) {
        try {
            log.info("=== 비밀번호 변경 시작 ===");
            log.info("memberNoLogin: {}", memberNoLogin);
            log.info("입력받은 현재 비밀번호: '{}'", dto.getCurrentPassword());
            log.info("입력받은 새 비밀번호: '{}'", dto.getNewPassword());

            String currentHashedPw = accountMapper.findPasswordByMemberNo(memberNoLogin);
            log.info("DB에서 조회한 해시: '{}'", currentHashedPw);

            if (currentHashedPw == null) {
                log.warn("사용자를 찾을 수 없음");
                return 0;
            }


            log.info("BCrypt 검증 시작...");
            boolean passwordMatch = BCrypt.checkpw(dto.getCurrentPassword(), currentHashedPw);
            log.info("BCrypt.checkpw('{}', '{}') = {}",
                    dto.getCurrentPassword(), currentHashedPw, passwordMatch);


            log.info("=== 추가 테스트 ===");
            log.info("입력 비밀번호 길이: {}", dto.getCurrentPassword().length());
            log.info("해시 길이: {}", currentHashedPw.length());

            if (!passwordMatch) {
                log.warn("현재 비밀번호 불일치");
                return -1;
            }

            String hashedNewPw = BCrypt.hashpw(dto.getNewPassword(), BCrypt.gensalt());
            log.info("새 비밀번호 해시 생성 완료");


            Map<String, Object> params = new HashMap<>();
            params.put("memberNoLogin", memberNoLogin);
            params.put("newPassword", hashedNewPw);

            log.info("DB 업데이트 시작...");
            int result = accountMapper.updatePassword(params);
            log.info("업데이트 결과: {}", result);

            return result;

        } catch (Exception e) {
            log.error("비밀번호 변경 중 오류 발생", e);
            return 0;
        }
    }

    @Transactional
    public int deleteById(int memberNoLogin) {
        accountMapper.deleteMemberLocationByMemberId(memberNoLogin);
        return accountMapper.deleteById(memberNoLogin);
    }


}