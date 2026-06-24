package com.trabajo.Trasladar;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class TrasladarApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	void mainClassExist(){
		assertNotNull(TrasladarApplication.class);
	}



}
