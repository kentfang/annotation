package com.galanz.annotation.anno.controller;


import com.alibaba.fastjson.JSON;
import com.galanz.annotation.anno.common.Message;
import com.galanz.processors.anno.CMD;
import com.galanz.processors.anno.Controller;

@Controller("device")
public class DeviceController {

    @CMD(1010)
    public Message register(Message message){
        System.out.println("DeviceController.register:"+ JSON.toJSONString(message));
        return null;
    }


    @CMD(1011)
    public Message login(Message message){
        System.out.println("DeviceController.login:"+ JSON.toJSONString(message));
        return null;
    }

    @CMD(1012)
    public Message logout(Message message){
        System.out.println("DeviceController.logout:"+ JSON.toJSONString(message));
        return null;
    }

    @CMD(1013)
    public Message stateUp(Message message){
        System.out.println("DeviceController.logout:"+ JSON.toJSONString(message));
        return null;
    }

    public void test(){

    }
}
