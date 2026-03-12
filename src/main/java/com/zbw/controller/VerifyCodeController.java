package com.zbw.controller;

import com.zbw.utils.VerifyCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class VerifyCodeController {

    @RequestMapping("/getVerifyCode")
    public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            VerifyCodeUtils.generateVerifyCode(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
