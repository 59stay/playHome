/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: UserMessageController
 * Author:   jyb
 * Date:     2019/1/6 0:20
 * Description: 用户留言板控制层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.jyb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 〈一句话功能简述〉<br>
 * 〈用户留言板控制层〉
 *
 * @author jyb
 * @create 2019/1/6
 * @since 1.0.0
 */
@Controller
@RequestMapping("userMessage")
public class UserMessageController {

    @RequestMapping("index")
    public ModelAndView userMessageShow(){
             ModelAndView mv  = new ModelAndView();
             mv.setViewName("messageBoard/userMessage");
             return mv;
    }
}