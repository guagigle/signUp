package com.pretask.signup.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

/* SMS 전송 */
// import java.util.HashMap;
// import org.json.simple.JSONObject;
// import net.nurigo.java_sdk.api.Message;
// import net.nurigo.java_sdk.exceptions.CoolsmsException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pretask.signup.DTO.CertificateInfoDTO;
import com.pretask.signup.DTO.PhoneCeritificateDTO;
import com.pretask.signup.DTO.SignUpDTO;
import com.pretask.signup.mapper.CertificateMapper;
import com.pretask.signup.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificateService {
    private final UserMapper userMapper;
    private final CertificateMapper certificateMapper;
    
    public String  getRandomNumberString() {
      Random rnd = new Random();
      int number = rnd.nextInt(999999);
  
      return String.format("%06d", number);
  }
    @Transactional
    public String sendMsg(String phoneNumber, boolean findPw) {
      //가입정보 확인
      if(findPw) {
        SignUpDTO userInfo = userMapper.getUserInfoByPhoneNumber(phoneNumber);
        if(userInfo == null) {
          return "Fail - 가입 정보가 없습니다.";
        }
      }
      else {
        SignUpDTO userInfo = userMapper.getUserInfoByPhoneNumber(phoneNumber);
        if(userInfo != null) {
          return "Fail - 이미 가입하셨습니다. 비밀번호 찾기를 이용해주세요.";
        }
      }
      
      //인증번호 발급
      String certificateNumber;
      //발급 여부 확인
      CertificateInfoDTO certificateInfoDTO = certificateMapper.getCertificateNumber(phoneNumber);
      if(certificateInfoDTO == null) {
        //발급 된적 없으면
        certificateNumber = getRandomNumberString();
        certificateInfoDTO = new CertificateInfoDTO();
        certificateInfoDTO.setPhoneNumber(phoneNumber);
        certificateInfoDTO.setCertificateNumber(certificateNumber);
        certificateInfoDTO.setCreated(new Timestamp(System.currentTimeMillis()));
        certificateMapper.createCertificateInfo(certificateInfoDTO);
      }
      else {
        //5분 이내라면 번호를 새로 발급하지 않는다.
        LocalDateTime created = certificateInfoDTO.getCreated().toLocalDateTime();
        created = created.plusMinutes(5);
        LocalDateTime now = LocalDateTime.now();
        if(now.isAfter(created)) {
          certificateNumber = getRandomNumberString();
          certificateInfoDTO.setCertificateNumber(certificateNumber);
          certificateInfoDTO.setCreated(new Timestamp(System.currentTimeMillis()));
          certificateMapper.updateCertificateInfo(certificateInfoDTO);
        }
        else {
          certificateNumber = certificateInfoDTO.getCertificateNumber();
        }
      }
    /* SMS 전송 */
    //     String api_key = "[[api_key]]";
    //     String api_secret = "[[api_secret]]";
    //     Message coolsms = new Message(api_key, api_secript);
    //     HashMap<String, String> params = new HashMap<String, String>();
    //     params.put("to","phoneNumber");
    //     params.put("from", "[[fromNubmer]]");
    //     params.put("type", "SMS");
    //     params.put("text", "인증번호는 " + certificateNumber + "입니다.");
    //     params.put("app_version", "test app 1.2");
    
    //    try {
    //     JSONObject obj = (JSONObject) coolsms.send(params);
    //     System.out.println(obj.toString());
    //   } catch (CoolsmsException e) {
    //     System.out.println(e.getMessage());
    //     System.out.println(e.getCode());
    //   }
      return "인증번호는 " + certificateNumber + "입니다.";
    }

    public boolean checkCertificateNumber(PhoneCeritificateDTO phoneCeritificateDTO) {
      CertificateInfoDTO certificateInfoDTO = certificateMapper.getCertificateNumber(phoneCeritificateDTO.getPhoneNumber());
      if(certificateInfoDTO.getCertificateNumber().equals(phoneCeritificateDTO.getCertificateNumber())) {
        return true;
      }
      else return false;
    }
}
