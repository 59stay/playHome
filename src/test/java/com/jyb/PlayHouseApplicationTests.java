package com.jyb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)	
@SpringBootTest
public class PlayHouseApplicationTests {

	@Autowired
	private JavaMailSender mailSender;

	@Test
	public void sendSimpleMail() throws Exception {
		SimpleMailMessage message = new SimpleMailMessage();
        // 设定邮件参数
		message.setFrom("166768601@qq.com");
		message.setTo("2469874170@qq.com");
        message.setSubject("主题:邮件"); //主题
        message.setText("邮件内容"); //邮件内容
        // 发送邮件
		mailSender.send(message);
	}

}
