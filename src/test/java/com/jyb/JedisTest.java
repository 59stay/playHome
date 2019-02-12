package com.jyb;

import redis.clients.jedis.Jedis;

public class JedisTest {

	public static void main(String[] args) {
		Jedis jedis=new Jedis("192.168.1.112",6379); // 创建客户端 设置IP和端口
        jedis.auth("123456"); // 设置密码
        //jedis.set("names", "java知识分享网"); // 设置值
        jedis.select(0);
        String value=jedis.get("um"); // 获取值
        System.out.println(value);
        jedis.close(); // 释放连接资源
	}
}
