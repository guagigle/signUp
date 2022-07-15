package com.pretask.signup.controller;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.pretask.signup.DTO.LoginInfoDTO;
import com.pretask.signup.DTO.PasswordDTO;
import com.pretask.signup.DTO.SignUpDTO;
import com.pretask.signup.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    
    @GetMapping("/signUp")
    public ModelAndView signUpForm(@RequestParam("phoneNumber") String phoneNumber, @RequestHeader("referer") Optional<String> referer) {
        ModelAndView mav = new ModelAndView();
        if(referer.isEmpty()) {
            mav.setViewName("redirect:/");
            return mav;
        }
        else {
            mav.addObject("phoneNumber", phoneNumber);
            mav.setViewName("signUp");
        }
        return mav;
    }

    @PostMapping("/signUp")
    public String signUp(SignUpDTO signUpDTO, RedirectAttributes re) throws Exception{
        re.addFlashAttribute("userInfo", signUpDTO);
        boolean result = userService.joinUser(signUpDTO);
        if(result) {
            return "redirect:/login";
        }
        else {
            return "signUpError";
        }
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/welcome")
    public String myPage(@RequestHeader("referer") Optional<String> referer) {
        if(referer.isEmpty()) {
            return "redirect:/";
        }
        else return "welcome";
    }

    @PostMapping("/login")
    public String login(SignUpDTO signUpDTO, RedirectAttributes re) throws Exception{
        LoginInfoDTO result = userService.login(signUpDTO);
        if(result.getStatus().equals("Success")) {
            re.addFlashAttribute("userInfo", result.getUserinfo());
            return "redirect:/welcome";
        }
        else {
            re.addFlashAttribute("error", result.getStatus());
            return "redirect:/login";
        }
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody PasswordDTO passwordDTO) throws Exception{
        if(userService.changePassword(passwordDTO)) {
            return ResponseEntity.ok("success");
        }
        else {
            return ResponseEntity.ok("fail");
        }
    }

    @GetMapping("/findPw")
    public String findPassword() {
        return "findPassword";
    }

    @GetMapping("/renamePassword")
    public ModelAndView renamePassword(@RequestParam("phoneNumber") String phoneNumber, @RequestHeader("referer") Optional<String> referer) {
        ModelAndView mav = new ModelAndView();
        if(referer.isEmpty()) {
            mav.setViewName("redirect:/");
            return mav;
        }
        else {
            mav.addObject("phoneNumber", phoneNumber);
            mav.setViewName("renamePassword");
        }
        return mav;
    }
}
