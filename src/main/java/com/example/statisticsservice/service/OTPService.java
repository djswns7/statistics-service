package com.example.statisticsservice.service;

import com.example.statisticsservice.domain.User;
import com.example.statisticsservice.repository.OTPRepository;
import com.example.statisticsservice.repository.UserRepository;
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
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OTPService {
    private final OTPRepository otpRepository;
    private final GoogleAuthenticator gAuth;
    private final UserRepository userRepository;

    public OTPService(OTPRepository otpRepository, GoogleAuthenticator gAuth, UserRepository userRepository) {
        this.otpRepository = otpRepository;
        this.gAuth = gAuth;
        this.userRepository = userRepository;
    }

    /**
     * 새로운 UserOTP 생성
     *
     * @param email      사용자 이메일
     * @param secreteKey 사용자 시크릿 키
     * @param expiry     OTP 만료 시간
     */
    @Transactional
    public void createOTP(String email, String secreteKey, LocalDateTime expiry) {
        otpRepository.createUserOTP(email, secreteKey, expiry);
    }

    /**
     * email에 해당하는 secreteKey 조회
     *
     * @param email 사용자 이메일
     * @return secreteKey 값
     */
    public String getSecreteKey(String email) {
        return otpRepository.findSecreteKeyByEmail(email);
    }

    /**
     * email에 해당하는 UserOTP 삭제
     *
     * @param email 삭제할 사용자 이메일
     */
    @Transactional
    public void deleteOTP(String email) {
        otpRepository.deleteByEmail(email);
    }

    /**
     * Secret Key 생성
     *
     * @return 생성된 Secret Key
     */
    public String generateSecretKey() {
        // Secret Key 생성 (저장 아님!)
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        return key.getKey();
    }

    /**
     * 동일 email에 대한 Secret Key 중복 여부를 확인 후
     * 존재하는 값 또는 생성한 값을 리턴
     *
     * @param email 사용자 이메일
     * @return 생성 또는 불러온 Secret Key
     */
    @Transactional
    public String findOrCreateSecretKey(String email) {
        // user 테이블에서 이메일 확인
        Optional<User> user = userRepository.findByEmail(email);

        // 사용자가 존재하지 않을 경우 예외를 던짐
        if (user.isEmpty()) {
            throw new IllegalArgumentException("생성되지 않은 유저에 대한 QR 코드 생성 요청입니다: " + email);
        }

        String existingSecretKey = otpRepository.findSecreteKeyByEmail(email);

        if (existingSecretKey != null) {
            return existingSecretKey;
        }

        // Secret Key 생성 및 저장
        String newSecretKey = generateSecretKey();

        // TODO
        // 현재 만료된 OTP에 대한 제거 로직은 없음
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(10); // OTP 만료 시간 10분 후
        createOTP(email, newSecretKey, expiry);

        return newSecretKey;
    }

    /**
     * QR Content 생성
     *
     * @param email      사용자 이메일
     * @param secretKey  생성된 Secret Key
     * @param issuerName 발급 서비스 이름
     * @return QR 코드 내용
     */
    public String createQRContent(String email, String secretKey, String issuerName) {
        // QR Code 내용 생성
        return String.format("otpauth://totp/%s?secret=%s&issuer=%s", email, secretKey, issuerName);
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

    /**
     * 사용자 코드 검증
     *
     * @param email 사용자 이메일
     * @param code  사용자 입력 2차 인증 코드
     * @return 코드 유효 여부
     */
    public boolean verifyCode(String email, int code) {
        // email에 해당하는 Secret Key 조회
        String secretKey = otpRepository.findSecreteKeyByEmail(email);

        if (secretKey == null || secretKey.isBlank()) {
            throw new IllegalArgumentException("Invalid email: Secret Key not found");
        }

        // Google Authenticator의 코드 검증
        return gAuth.authorize(secretKey, code);
    }
}
