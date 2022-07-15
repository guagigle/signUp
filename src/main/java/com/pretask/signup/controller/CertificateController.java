package com.pretask.signup.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.pretask.signup.DTO.PhoneCeritificateDTO;
import com.pretask.signup.service.CertificateService;

@Controller
@RequiredArgsConstructor
public class CertificateController {
    private final CertificateService certificateService;

    @PostMapping("/phoneCertificate")
    public ResponseEntity<String> certificate(@RequestParam("phoneNumber") String phoneNumber) {
        String certificateMessage = certificateService.sendMsg(phoneNumber, false);
        return ResponseEntity.ok(certificateMessage);
    }

    @PostMapping("/findpw/phoneCertificate")
    public ResponseEntity<String> certificateForFindPw(@RequestParam("phoneNumber") String phoneNumber) {
        String certificateMessage = certificateService.sendMsg(phoneNumber, true);
        return ResponseEntity.ok(certificateMessage);
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirm(@RequestBody PhoneCeritificateDTO phoneCeritificateDTO) {
        if(certificateService.checkCertificateNumber(phoneCeritificateDTO)) {
            return ResponseEntity.ok("success");
        }
        else {
            return ResponseEntity.ok("fail");
        }
    }
}
