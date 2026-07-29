package com.example.springai;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"spring.ai.google.genai.api-key=mock-api-key-for-test-validation",
		"spring.ai.google.genai.project-id=mock-project"
})
class SpringaiApplicationTests {

	@Test
	void contextLoads() {
	}

}
