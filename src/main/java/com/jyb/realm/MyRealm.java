package com.jyb.realm;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.jyb.entity.UserInformation;
import com.jyb.service.UserInformationService;


/**
 * 自定义Realm
 *
 *
 */
public class MyRealm extends AuthorizingRealm{

	@Resource
	private UserInformationService userInformationService;
	
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
		/*	String userName=(String) SecurityUtils.getSubject().getPrincipal();
		UserInformation user=userInformationService.findByUserName(userName);
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		Set<String> roles=new HashSet<String>();
		if(user.getUserRole()==0){
			roles.add("0");
			info.addStringPermission("进入管理员主页");
			info.addStringPermission("生成所有帖子索引");
			info.addStringPermission("分页查询资源帖子信息");
			info.addStringPermission("跳转到修改帖子页面");
			info.addStringPermission("修改帖子");
			info.addStringPermission("图片上传");
			info.addStringPermission("删除帖子");
			info.addStringPermission("跳转到帖子审核页面");
			info.addStringPermission("修改状态");
			info.addStringPermission("修改热门状态");
			
			info.addStringPermission("分页查询评论信息");
			info.addStringPermission("修改评论状态");
			info.addStringPermission("删除评论");
			
			info.addStringPermission("分页查询用户信息");
			info.addStringPermission("修改用户VIP状态");
			info.addStringPermission("修改用户状态");
			info.addStringPermission("重置用户密码");
			info.addStringPermission("用户积分充值");
			
			info.addStringPermission("分页查询资源类别信息");
			info.addStringPermission("添加或者修改类别信息");
			info.addStringPermission("删除类别信息");
			info.addStringPermission("根据id查询资源类别实体");
			
			info.addStringPermission("分页查询友情链接");
			info.addStringPermission("添加或者修改友情链接");
			info.addStringPermission("删除友情链接");
			info.addStringPermission("根据id查询友情链接实体");
			
			info.addStringPermission("修改管理员密码"); 
			info.addStringPermission("安全退出");
		}
		info.setRoles(roles);
		return info;*/
	}

	/**
	 * 权限认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName=(String)token.getPrincipal();
		UserInformation user=userInformationService.findByUserName(userName);
		if(user!=null){
			AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(user.getUserName(),user.getUserPassword(),"xxx");
			return authcInfo;
		}else{
			return null;			
		}
	}

}
