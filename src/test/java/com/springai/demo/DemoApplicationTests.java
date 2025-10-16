package com.springai.demo;

import com.springai.demo.helpers.Helper;
import com.springai.demo.services.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private ChatService chatService;

    @Test
    void saveDataToVectorDatabase(){
        System.out.println("save data to data base");
        this.chatService.saveData(Helper.getdata());
        System.out.println("data saved successfully");
    }

}
