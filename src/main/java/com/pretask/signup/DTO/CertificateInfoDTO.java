package com.pretask.signup.DTO;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CertificateInfoDTO {
    private String phoneNumber;
    private String certificateNumber;
    private Timestamp created;
}
