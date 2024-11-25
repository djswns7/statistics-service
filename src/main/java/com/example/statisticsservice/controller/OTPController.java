package com.example.statisticsservice.controller;

import com.example.statisticsservice.service.OTPService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/otp")
public class OTPController {
    private final OTPService otpService;

    public OTPController(OTPService otpService) {
        this.otpService = otpService;
    }

    //
    @PostMapping("/qr-codes")
    public void generateQRCode(
            @RequestParam(name = "email") String email,
            HttpServletResponse response
    ) {
        if (email == null || email.isBlank()) {
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "AccountName parameter is required and cannot be blank.");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            String issuerName = "statistics-service";

            // Secret Key 생성 및 저장 (동시성 문제 방지)
            String secretKey = otpService.findOrCreateSecretKey(email);

            // QR Content 생성
            String qrContent = otpService.createQRContent(email, secretKey, issuerName);

            response.setContentType("image/png");
            try (OutputStream outputStream = response.getOutputStream()) {
                otpService.writeQRCodeToStream(qrContent, outputStream); // QR Code 전송
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // TODO
    // 세션에서 email 을 유지할 경우 RequestParam 으로 email 을 전달받지 않는 형태도 구현하면 좋겠음
    //
    // 구현하고자 하는 유저 플로우: 1. 이메일을 통한 로그인 -> 2. OTP 코드 입력
    // 1. 이메일을 세션에 저장 ( 로그인 핸들러: 요청인자로 이메일과 비밀번호 )
    // 2. 세션에 저장된 이메일과 코드를 보고 인가 결정 ( verifyQRCode 핸들러: 요청인자로 otp 코드 하나만 )
    @GetMapping("/qr-codes/verify")
    public ResponseEntity<String> verifyQRCode(
            @RequestParam String email,
            @RequestParam int code
    ) {
        try {
            boolean isValid = otpService.verifyCode(email, code);

            if (isValid) {
                return ResponseEntity.ok("OTP 인증 성공.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효한 코드가 아닙니다. OTP 인증 실패");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while verifying the code.");
        }
    }

}
