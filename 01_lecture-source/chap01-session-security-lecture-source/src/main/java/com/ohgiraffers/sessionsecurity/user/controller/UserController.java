package com.ohgiraffers.sessionsecurity.user.controller;


import com.ohgiraffers.sessionsecurity.user.model.dto.SignupDTO;
import com.ohgiraffers.sessionsecurity.user.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")        // /user 라고 들어온것들은 얘가 다 가져갈 것임!!
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public void signup() {}

    @PostMapping("/signup")
    public ModelAndView signup(ModelAndView mv, @ModelAttribute SignupDTO signupDTO) {

        int result = userService.regist(signupDTO);

        String message = "";        // 빈공간 만들어줌~~

        if (result > 0) {       // 메세지 등록했다면!!
            message = "회원 가입이 정상적으로 완료되었습니다!!";

        } else {

            message = " 회원 가입이 실패하셨습니다. ";
        }

        mv.addObject("message", message);       // 담아준 message에 다 담아둘 것~!!

        return mv;

    }

}
