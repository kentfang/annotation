package com.galanz.annotation.anno.controller;


import com.alibaba.fastjson.JSON;
import com.galanz.annotation.anno.common.Message;
import com.galanz.processors.anno.CMD;
import com.galanz.processors.anno.Controller;

@Controller("default")
public class DefautController {

    @CMD(3000)
    public Message defaultM(Message message){
        System.out.println("DefautController defaultM:"+ JSON.toJSONString(message));
        return null;
    }
}
