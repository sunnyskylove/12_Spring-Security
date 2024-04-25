package com.ohgiraffers.sessionsecurity.auth.model.service;

import com.ohgiraffers.sessionsecurity.auth.model.AuthDetails;
import com.ohgiraffers.sessionsecurity.user.model.dto.LoginUserDTO;
import com.ohgiraffers.sessionsecurity.user.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService implements UserDetailsService {

    /* 필기.
     *   security 에서 사용자의 아이디를 인증하기 위한 interface 이다.
     *   loadUserByUsername 을 필수로 구현해야 하며, 로그인 인증 시 해당
     *   메소드에 login 요청 시 전달 된 사용자의 id 를 매개변수로
     *   DB 에서 조회를 한다.
     *  */

    @Autowired
    private UserService userService;    // 사용자의 아이디를 가지고 조회해야하니깐 여기에서 몰빵!(밑의 service)

    // username 을 select 해서 조회하는 곳!!!,
    // 여기서 기본적으로 구성되어 있는 "loadUserByUsername"는 사용자의 ID를 지칭한다(username 이 아닌!!)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        LoginUserDTO login = userService.findByUsername(username);

        if(Objects.isNull(login)) {           // 값이 없으면,

            throw new UsernameNotFoundException("해당하는 회원 정보가 존재하지 않습니다!!!");
        }

        //        return null;
        return new AuthDetails(login);
    }
}