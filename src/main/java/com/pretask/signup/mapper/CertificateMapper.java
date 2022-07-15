package com.pretask.signup.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.pretask.signup.DTO.CertificateInfoDTO;

@Mapper
public interface CertificateMapper {
    void createCertificateInfo(CertificateInfoDTO certificateInfoDTO);
    CertificateInfoDTO getCertificateNumber(String phoneNumber);
    void updateCertificateInfo(CertificateInfoDTO certificateInfoDTO);
}
