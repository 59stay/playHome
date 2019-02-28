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
import com.jyb.util.CheckUrlUtil;

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
    
    @Test
  	public void checkLink() {
		List<GameInformation> gameInformation= gameInformationService.listAll(null,Sort.Direction.DESC, "gameBrowseFrequency");
		  for(GameInformation game:gameInformation){
			  System.out.println(game.getGameName());
		  }
		/* for(GameInformation game:gameInformation){
        	if(game.getDownloadType()==1){//验证百度云链接
  				try {
					if(!CheckUrlUtil.checkBDY(game.getGameDownloadAddress())){
						game.setAuditStatus(1);
						game.setIsUseful(2);
						gameInformationService.save(game);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					  continue;   
				}
  			}
  			 if(game.getDownloadType()==2){//验证其他链接地址
               if(!CheckUrlUtil.checkUrl(game.getGameDownloadAddress(), 1000)){
            	        game.setAuditStatus(1);
            	        game.setIsUseful(2);
            	        gameInformationService.save(game);
               }
    	   }
        }
        
        List<Software> softwareList=softwareService.listAll(null,Sort.Direction.DESC, "browseFrequency");
        for(Software software:softwareList){
  			if(software.getDownloadType()==1){//验证百度云链接
  				try {
					if(!CheckUrlUtil.checkBDY(software.getDownloadAddress())){
						software.setAuditStatus(1);
						software.setIsUseful(2);
						softwareService.save(software);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
				    continue;  
				}
  			}
  			 if(software.getDownloadType()==2){//验证其他链接地址
               if(!CheckUrlUtil.checkUrl(software.getDownloadAddress(), 1000)){
    					software.setAuditStatus(1);
    					software.setIsUseful(2);
    					softwareService.save(software);
               }
    	   }
  		}*/
  		
  	}

}
