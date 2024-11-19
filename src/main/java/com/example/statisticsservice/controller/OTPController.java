package com.example.statisticsservice.controller;

import com.example.statisticsservice.service.OTPService;
import jakarta.servlet.http.HttpServletResponse;
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

    // non-save test 함수
    @PostMapping("/qr-codes/non-save")
    public void generateQRCode(
            @RequestParam(name = "accountName") String accountName,
            HttpServletResponse response
    ) {
        if (accountName == null || accountName.isBlank()) {
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "AccountName parameter is required and cannot be blank.");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            String issuerName = "statistics-service";
            String secretKey = otpService.generateSecretKey(); // Secret Key 생성
            String qrContent = otpService.createQRContent(accountName, secretKey, issuerName); // QR Content 생성

            response.setContentType("image/png");
            try (OutputStream outputStream = response.getOutputStream()) {
                otpService.writeQRCodeToStream(qrContent, outputStream); // QR Code 전송
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
