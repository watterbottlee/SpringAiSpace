package com.springai.demo;

import com.springai.demo.services.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

    @Autowired
    private ChatService chatService;
    @Test
    void testTemplateRenderer() throws IOException {
        System.out.println("Template renderer");

        var output = this.chatService.chat("helllo","hi");
        System.out.println(output);

    }

}
