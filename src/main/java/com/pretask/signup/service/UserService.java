package com.pretask.signup.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.crypto.spec.SecretKeySpec;

import com.pretask.signup.DTO.LoginInfoDTO;
import com.pretask.signup.DTO.PasswordDTO;
import com.pretask.signup.DTO.SignUpDTO;
import com.pretask.signup.mapper.UserMapper;
import com.pretask.signup.util.EncryptUtil;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public boolean joinUser(SignUpDTO signUpDTO) throws Exception{
        SignUpDTO userInfo = userMapper.getUserInfoByEmail(signUpDTO.getUserEmail());
        if(userInfo != null) {
            return false;
        }
        String password = signUpDTO.getUserPw();
        byte[] salt = new String("12345678").getBytes();
        int iterationCount = 20;
        int keyLength = 128;
        SecretKeySpec key = EncryptUtil.createSecretKey("12345678".toCharArray(), salt, iterationCount, keyLength);
        String encryptedPassword = EncryptUtil.encrypt(password, key);
        signUpDTO.setUserPw(encryptedPassword);
        userMapper.saveUser(signUpDTO);
        return true;
    }

    public LoginInfoDTO login(SignUpDTO signUpDTO) throws Exception {
        LoginInfoDTO result = new LoginInfoDTO();
        if(signUpDTO.getUserPw().equals("")) {
            result.setStatus("Fail - 비밀번호를 입력하세요."); 
        }
        else {
            SignUpDTO userInfo;
            if(signUpDTO.getUserEmail() != null) {
                userInfo = userMapper.getUserInfoByEmail(signUpDTO.getUserEmail());
            }
            else if(signUpDTO.getUserPhoneNumber() != null) {
                userInfo = userMapper.getUserInfoByPhoneNumber(signUpDTO.getUserPhoneNumber());
            }
            else {
                result.setStatus("Fail - 이메일이나 전화번호를 입력하세요."); 
                return result;
            }

            if(userInfo == null) {
                result.setStatus("Fail - 정확하지 않은 이메일이나 전화번호입니다."); 
                return result;
            }
            byte[] salt = new String("12345678").getBytes();
            int iterationCount = 20;
            int keyLength = 128;
            SecretKeySpec key = EncryptUtil.createSecretKey("12345678".toCharArray(), salt, iterationCount, keyLength);
            String decryptedPassword = EncryptUtil.decrypt(userInfo.getUserPw(), key);
            if(decryptedPassword.equals(signUpDTO.getUserPw())) {
                result.setStatus("Success");
                result.setUserinfo(userInfo);
            }
            else {
                result.setStatus("Fail - 비밀번호가 다릅니다.");
            }
        }
        return result;
    }

    public boolean changePassword(PasswordDTO passwordDTO) throws Exception {
        String password = passwordDTO.getPassword();
        byte[] salt = new String("12345678").getBytes();
        int iterationCount = 20;
        int keyLength = 128;
        SecretKeySpec key = EncryptUtil.createSecretKey("12345678".toCharArray(), salt, iterationCount, keyLength);
        String encryptedPassword = EncryptUtil.encrypt(password, key);
        passwordDTO.setPassword(encryptedPassword);
        userMapper.changePassword(passwordDTO);
        return true;
      }
}
