package com.example.MyProject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class CaseRepositoryTest {
	
	@Test
	void findByLawyerOab() {
		
	}

}
