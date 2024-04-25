package com.ohgiraffers.sessionsecurity.auth.model;

import com.ohgiraffers.sessionsecurity.user.model.dto.LoginUserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class AuthDetails implements UserDetails {        // 모두 선택해서~~완료, 오류 해결!!

    private LoginUserDTO loginUserDTO;

    public AuthDetails() {}

    public AuthDetails(LoginUserDTO loginUserDTO) {     // 위의  생성자까지 초기화하는 과정을 만들기!
        this.loginUserDTO = loginUserDTO;
    }

    public LoginUserDTO getLoginUserDTO() {
        return loginUserDTO;
    }

    public void setLoginUserDTO(LoginUserDTO loginUserDTO) {
        this.loginUserDTO = loginUserDTO;
    }

    /* 필기.
     *   권한 정보를 반환하는 메소드
     *   UsernamePasswordAuthenticationToken 에 사용자의 권한 정보(user 이면서 admin 일수도 있고, 여러개의 권한 넣을 수 있음)를 넣을 때 사용한다.
     *   다중 권한 처리 가능하다.
     *  */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        loginUserDTO.getRole().forEach(role -> authorities.add(() -> role));

        return authorities;
    }

    /* 필기.
     *   사용자의 비밀번호를 반환하는 메소드
     *   UsernamePasswordAuthenticationToken 과 사용자의 비밀번호를 비교할 때 사용한다.
     *  */
    @Override
    public String getPassword() {
        return loginUserDTO.getPassword();
    }

    /* 필기.
     *   사용자의 아이디를 반환하는 메소드이다.
     *   UsernamePasswordAuthenticationToken 과 사용자의 아이디를 비교할 때 사용한다.
     *  */
    @Override
    public String getUsername() {
        return loginUserDTO.getUserName();
    }

    /* 필기.
     *   계정 만료 여부를 표현하는 메소드로 false 이면 해당 계정을 사용할 수 없다.
     *  */
    @Override
    public boolean isAccountNonExpired() {
        //        return false;         // false 면 사용할 수 없으니 변경해준다!!
        return true;
    }

    /* 필기.
     *   잠겨있는 계정을 확인하는 메소드 false 이면 해당 계정을 사용할 수 없다.
     *   비밀번호 반복 실패로 일시적인 계정 lock 인 경우 혹은 오랜 시간
     *   비 접속으로 휴면 처리
     *  */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /* 필기.
     *   탈퇴 계정 여부를 표현하는 메소드 false 이면 해당 계정을 사용할 수 없다.
     *  */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /* 필기.
     *   계정 비활성화 여부로 사용자가 사용할 수 없는 상태
     *   false 이면 계정을 사용할 수 없다. 삭제 처리 같은 경우.
     *  */
    @Override
    public boolean isEnabled() {
        return true;
    }
}