package com.hashedin.eurekaserver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EurekaServerApplicationTests {

	@Test
	void contextLoads() {
		int a = 2;
		int c = a/2;
		Assertions.assertEquals(1,c);
	}


}
