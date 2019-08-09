package com.galanz.annotation.anno.controller;


import com.alibaba.fastjson.JSON;
import com.galanz.annotation.anno.common.Message;
import com.galanz.processors.anno.CMD;
import com.galanz.processors.anno.Controller;

@Controller("user")
public class UserController {

    @CMD(2010)
    public Message register(Message message){
        System.out.println("UserController.register:"+ JSON.toJSONString(message));
        return message;
    }

    @CMD(2011)
    public Message login(Message message){
        System.out.println("UserController.login:"+ JSON.toJSONString(message));
        return message;
    }

    @CMD(2012)
    public Message logout(Message message){
        System.out.println("UserController.logout:"+ JSON.toJSONString(message));
        return null;
    }
}
