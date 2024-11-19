package com.example.statisticsservice.service;

import com.example.statisticsservice.repository.OTPRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.io.OutputStream;

@Service
public class OTPService {
    private final OTPRepository otpRepository;
    private final GoogleAuthenticator gAuth;

    public OTPService(OTPRepository otpRepository, GoogleAuthenticator gAuth) {
        this.otpRepository = otpRepository;
        this.gAuth = gAuth;
    }

    /**
     * 새로운 UserOTP 생성
     *
     * @param accountName 사용자 계정 이름
     * @param secreteKey  사용자 시크릿 키
     */
    @Transactional
    public void createOTP(String accountName, String secreteKey) {
        otpRepository.createUserOTP(accountName, secreteKey);
    }

    /**
     * accountName에 해당하는 secreteKey 조회
     *
     * @param accountName 사용자 계정 이름
     * @return secreteKey 값
     */
    public String getSecreteKey(String accountName) {
        return otpRepository.findSecreteKeyByAccountName(accountName);
    }

    /**
     * accountName에 해당하는 UserOTP 삭제
     *
     * @param accountName 삭제할 사용자 계정 이름
     */
    @Transactional
    public void deleteOTP(String accountName) {
        otpRepository.deleteByAccountName(accountName);
    }

    /**
     * Secret Key 생성
     *
     * @return 생성된 Secret Key
     */
    public String generateSecretKey() {
//        1. Secret Key 생성
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        return key.getKey();
    }

    /**
     * QR Content 생성
     *
     * @param accountName 사용자 계정 이름
     * @param secretKey   생성된 Secret Key
     * @param issuerName  발급 서비스 이름
     * @return QR 코드 내용
     */
    public String createQRContent(String accountName, String secretKey, String issuerName) {
        // 2. QR Code 내용 생성(아래 형태)
        // otpauth://totp/{accountName}?secret={secretKey}&issuer={issuerName}
        // accountName = 구글계정
        // secreteKey = 내가 임의로 정한 스트링 값
        // issuerName 보통 프로젝트이름
        return String.format("otpauth://totp/%s?secret=%s&issuer=%s", accountName, secretKey, issuerName);
    }

    /**
     * QR Code 이미지를 OutputStream에 작성
     *
     * @param qrContent    QR 코드 내용
     * @param outputStream HTTP 응답 스트림
     * @throws Exception QR 코드 생성 오류 발생 시
     */
    public void writeQRCodeToStream(String qrContent, OutputStream outputStream) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 300, 300);
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream, new MatrixToImageConfig());
    }
}
