package com.jyb;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import com.jyb.entity.GameInformation;
import com.jyb.lucene.GameIndex;
import com.jyb.service.GameInformationService;

@RunWith(SpringRunner.class)	
@SpringBootTest
public class PlayHouseApplicationTests {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private GameIndex gameIndex;
	
	@Autowired
	private GameInformationService gameInformationService;
	

	@Test
	public void sendSimpleMail() throws Exception {
		SimpleMailMessage message = new SimpleMailMessage();
        // 设定邮件参数
		message.setFrom("166768601@qq.com");
		message.setTo("1990009696@qq.com");
        message.setSubject("主题:邮件"); //主题
        message.setText("邮件内容"); //邮件内容
        // 发送邮件
		mailSender.send(message);
	}
	
   /**
    * 生成帖子索引
    * @return
    */
    @Test
	public void generateIndex(){
    	//GameInformation gameInformation = new GameInformation();
    	//gameInformation.setAuditStatus(2);//显示审核通过的
    	//gameInformation.setIsUseful(1);//显示未失效的资源
		List<GameInformation> gameList=gameInformationService.listAll(null,Sort.Direction.DESC, "gameBrowseFrequency");
		System.out.println(gameList.size());
		for(GameInformation game:gameList){
				gameIndex.addIndex(game);
		}
	}

}
