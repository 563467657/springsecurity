package com.securitytest.security.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class CustomLoginController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
	
	@Autowired
	private DefaultKaptcha defaultKaptcha;
	
	@RequestMapping("/login/page")
	public String toLoginPage() {
		return "login";
	}
	
	/**
	 * 获取图形验证码
	 */
	@RequestMapping("/code/image")
	public void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//1.获取验证码字符串
		String code = defaultKaptcha.createText();
		logger.info("生成的图形验证码：" + code);
		//2.将字符串放到session中
		request.getSession().setAttribute(SESSION_KEY, code);
		//3.获取验证码图片
		BufferedImage image = defaultKaptcha.createImage(code);
		//4.写出验证码
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "png", out);
	}
}
