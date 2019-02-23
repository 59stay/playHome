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
import com.jyb.entity.Software;
import com.jyb.lucene.GameIndex;
import com.jyb.lucene.SoftwareIndex;
import com.jyb.service.GameInformationService;
import com.jyb.service.SoftwareService;

@RunWith(SpringRunner.class)	
@SpringBootTest
public class PlayHouseApplicationTests {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private GameIndex gameIndex;
	
	@Autowired
	private GameInformationService gameInformationService;
	
	@Autowired
	private SoftwareIndex softwareIndex;
	
	@Autowired
	private SoftwareService softwareService;
	
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
	public void generateIndex1(){
    	//GameInformation gameInformation = new GameInformation();
    	//gameInformation.setAuditStatus(2);//显示审核通过的
    	//gameInformation.setIsUseful(1);//显示未失效的资源
		List<GameInformation> gameList=gameInformationService.listAll(null,Sort.Direction.DESC, "gameBrowseFrequency");
		for(GameInformation game:gameList){
				gameIndex.addIndex(game);
		}
	}
    
    @Test
  	public void generateIndex2(){
  		List<Software> softwareList=softwareService.listAll(null,Sort.Direction.DESC, "browseFrequency");
  		for(Software software:softwareList){
  			softwareIndex.addIndex(software);
  		}
  	}

}
