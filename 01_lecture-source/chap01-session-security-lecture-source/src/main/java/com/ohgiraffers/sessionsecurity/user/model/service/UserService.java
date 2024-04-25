package com.ohgiraffers.sessionsecurity.user.model.service;

import com.ohgiraffers.sessionsecurity.user.model.dao.UserMapper;
import com.ohgiraffers.sessionsecurity.user.model.dto.LoginUserDTO;
import com.ohgiraffers.sessionsecurity.user.model.dto.SignupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

// 비밀번호 담겨져 있을 것임~!! 데이터 베이스에 직접 커넥션 열고 보내는 과정 따라서 아직 암호화 되어있지 않기 때문에
// 이 service 계층에서 암호화 진행 할 것임!!
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public int regist(SignupDTO signupDTO) {

        signupDTO.setUserPass(passwordEncoder.encode(signupDTO.getUserPass()));

        int result = 0;

        try {
            result = userMapper.regist(signupDTO);
        } catch (Exception e) {
            e.printStackTrace();    //  예외가 발생 했을 때, 발생한 위치와 상태를 반환하는 메소드
        }

        return result;       // Controller 한테~
    }

    public LoginUserDTO findByUsername(String username) {

        LoginUserDTO login = userMapper.findByUsername(username);

        if(!Objects.isNull(login)) {
            return login;
        } else {
            return null;
        }

    }
}
