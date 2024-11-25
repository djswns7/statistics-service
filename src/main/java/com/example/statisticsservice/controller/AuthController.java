package com.example.statisticsservice.controller;

import com.example.statisticsservice.request.LoginReq;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final HttpSession httpSession;

    public AuthController(AuthenticationManager authenticationManager, HttpSession httpSession) {
        this.authenticationManager = authenticationManager;
        this.httpSession = httpSession;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq loginRequest) {
        try {
            // 이메일과 비밀번호로 사용자 인증
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            loginRequest.getEmail(),
//                            loginRequest.getPassword()
//                    )
//            );


            // 인증 성공 시 처리
            return ResponseEntity.ok(Map.of("message", "로그인 성공"));
        } catch (AuthenticationException e) {
            // 인증 실패 시 응답
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Unauthorized", "message", "잘못된 이메일 또는 비밀번호"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        httpSession.invalidate();
        return ResponseEntity.ok("로그아웃 성공: 세션 제거됨");
    }
}
