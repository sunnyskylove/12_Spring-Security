package com.ohgiraffers.sessionsecurity.config;

import com.ohgiraffers.sessionsecurity.common.UserRole;
import com.ohgiraffers.sessionsecurity.config.handler.AuthFailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


// 참고. 보안에 관련된 것 만들 것임!!
//  이 페이지 가장 중요하다!!
@Configuration
@EnableWebSecurity
// security 관련 dependency 추가했으니깐~~
/* spring security 의 기능을 활성화 시키는 어노테이션 */
public class SecurityConfig {

    @Autowired
    private AuthFailHandler authFailHandler;

    // # password 바꿔줄 알고리즘!!
    /* 필기.
     *   비밀번호를 인코딩 하기 위한 Bean
     *   Bcrypt : 비밀번호 해싱에 가장 많이 사용되고 있는 알고리즘.
     *  */
    /* 필기.
     *   1. 보안성 : 해시 함수에 무작위 솔트를 적용하여 생성해준다.
     *   2. 호환성 : 높은 보안 수준 및 데이터베이스에 저장하기 쉬운 특징
     *   3. 알고리즘 신뢰성 : 보안에 논의 평가를 거친 알고리즘으로 문제 없이 사용 가능
     *  */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* 필기.
     *   정적인 리소스에 대한 요청을 제외하는 설정을 하는 bean
     *  */
    // 권한 없으면, 해당 URL 을 접근도 못하게 막는 것!!(근데 필터가 걸리면 안됨! 그래서 정적인 리소스)

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        // 정적인 파일이 접근하는 것은 막는것! (정적인 security는 건들지 않겠다~)
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // 필터들이 연이어 있는것, 하지만 직접 설정할 것임!!

        http.authorizeHttpRequests( auth -> {   // HTTP 요청!에 대한 인가/권한이다.
            /* 참고. permitAll -> 내부에 전달한 http 요청에 대해 모두에게 허용한다. */
            auth.requestMatchers("/auth/login", "/user/signup", "/auth/fail", "/", "/main").permitAll();
            // ▲ 여기에서 URL을 시작하는 것!! 그리고 비회원도 사용할 수 있게 설정 한 것!!! permitAll은 권한 상관없이 누구라도 사용 가능하게 하는 것!

            /* 참고. hasAnyAuthority -> 전달 http 요청에 대해 권한이 있는 사람만 허용한다. */
            auth.requestMatchers("/admin/*").hasAnyAuthority(UserRole.ADMIN.getRole());
            auth.requestMatchers("/user/*").hasAnyAuthority(UserRole.USER.getRole());
            /* 참고. 인증이 된 사람들에게 요청을 허락한다. */
            auth.anyRequest().authenticated();   // 여기선 authenticated_인증이 된 사람들만(로그인 한 사람만) 들어갈 수 있도록 권한주기

        }).formLogin(login -> {
            /* 참고. 로그인 페이지에 해당되는 서블릿이 존재해야 한다..(즉, 이 페이지가 만들어져 있어야 한다!) */
            login.loginPage("/auth/login");
            login.usernameParameter("user");    // 사용자 id 입력 필드(input 태그의 name 속성과 일치)
            login.passwordParameter("pass");    // 사용자 pass 입력 필드(input 태그의 name 속성과 일치)
            login.defaultSuccessUrl("/", true); // 로그인 성공 시 이동할 페이지(서블릿이 존재해야 한다.)=> 메인페이지인 /으로 간다!

            login.failureHandler(authFailHandler);     // *로그인에 실패 했을 시 처리할 커스텀 핸들러

        }).logout( logout -> {
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"));
            logout.deleteCookies("JSESSIONID"); // 로그아웃 시 사용자의 JSESSIONID 삭제 (로그아웃 하면 세션 만료해줘야 해서 필요!)

            logout.invalidateHttpSession(true); // 세션 소멸 허용
            logout.logoutSuccessUrl("/");       // 로그아웃 시 이동할 페이지 설정

        }).sessionManagement( session -> {
            session.maximumSessions(1); // session 의 허용 갯수 제한, -> 한 사용자가 여러 창을 띄워 동시에 여러 개 세션 활성화 방지.
            session.invalidSessionUrl("/"); // 세션이 만료 되었을 때 이동할 페이지

        }).rememberMe(remember -> {
            remember.tokenValiditySeconds(604800); // 토큰의 유효 기간 설정(1주일), 나를 며칠동안 기억할지를 (일주일)에 시간 정하는 것!/ 하루 설정하고 싶으면, 60*60*24 설정해주면 됨~^^
            remember.key("mykey");  // remember-me 토큰을 생성하는데 사용되는 키
        }).csrf( csrf -> csrf.disable()); // 추가적인 구현이 필요하기 때문에 비활성화

        return http.build();    // 위에 작성한 것들 담겨져서 쌓아졌다.
    }

}