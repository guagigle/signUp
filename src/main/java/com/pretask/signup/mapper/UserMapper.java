package com.pretask.signup.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.pretask.signup.DTO.PasswordDTO;
import com.pretask.signup.DTO.SignUpDTO;

@Mapper
public interface UserMapper {
    void saveUser(SignUpDTO signUpDTO);
    SignUpDTO getUserInfoByEmail(String userEmail);
    SignUpDTO getUserInfoByPhoneNumber(String userPhoneNumber);
    void changePassword(PasswordDTO passwordDTO);
}
