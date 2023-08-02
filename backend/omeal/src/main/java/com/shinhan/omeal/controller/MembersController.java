package com.shinhan.omeal.controller;

import com.shinhan.omeal.dto.members.CardDTO;
import com.shinhan.omeal.dto.members.MembersDTO;
import com.shinhan.omeal.service.MembersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.shinhan.omeal.dto.members.MyPageUserInfoDTO;
import com.shinhan.omeal.dto.members.ResultUserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class MembersController {

    private final MembersService membersService;

//     // 회원 가입
//     @PostMapping("/sign-up")
//     public String signUp(@RequestBody HashMap<String, String> obj) {
//         return membersService.signUp(obj);
//     }

    // 닉네임 중복 체크
    @GetMapping("/sign-up/nick-check")
    public int isNickDuplicated(String memberNick) {
        return membersService.isNickDuplicated(memberNick);
    }

    // 아이디 중복 체크
    @GetMapping("/sign-up/id-check")
    public int isIdDuplicated(@RequestParam String memberId) {
        return membersService.isIdDuplicated(memberId);
    }

    // 로그인
    @PostMapping(value = "/sign-in", consumes = "application/json", produces = "text/plain;charset=utf-8")
    public String signIn(@RequestBody MembersDTO membersDto) {
        log.info("로그인 버튼 클릭 : " + membersDto);
        String answer = membersService.signIn(membersDto); // 성공시 "success"

        return answer;
    }

    // 회원가입
    @PostMapping(value = "/sign-up", consumes = "application/json", produces = "text/plain;charset=utf-8")
    public String signUp(@RequestBody CardDTO cardDto) {
        log.info("회원 가입 완료 클릭 : " + cardDto.toString());
        String answer = membersService.signUp(cardDto); // 성공시 "success"

        return answer;
    }

    // 회원정보 요청
    @GetMapping("/userInfo")
    public ResponseEntity<ResultUserInfoDTO> userInfoGet(String memId) {
        ResultUserInfoDTO member = membersService.getInfo(memId);
        return ResponseEntity.ok(member);
    }

    // 회원 정보 수정
    @PostMapping("/userInfo")
    public String userInfoUpdate(MyPageUserInfoDTO dto) {
        membersService.update(dto);
        return "success";
    }
}
