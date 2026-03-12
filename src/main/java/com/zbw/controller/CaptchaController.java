package com.zbw.controller;

import com.zbw.utils.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class CaptchaController {

    @RequestMapping("/captcha")
    public void getCaptcha(HttpSession session, HttpServletResponse response) throws IOException {
        String captchaCode = CaptchaUtil.generateRandomCode();
        session.setAttribute("captchaCode", captchaCode);
        
        BufferedImage image = CaptchaUtil.generateCaptchaImage(captchaCode);
        
        response.setContentType("image/png");
        OutputStream out = response.getOutputStream();
        ImageIO.write(image, "png", out);
        out.flush();
        out.close();
    }
}
