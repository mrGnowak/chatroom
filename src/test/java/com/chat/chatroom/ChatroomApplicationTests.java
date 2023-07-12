package com.chat.chatroom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase

class ChatroomApplicationTests {

	@Test
	void contextLoads() {
		assertEquals(2, 1 + 1);
	}

}
