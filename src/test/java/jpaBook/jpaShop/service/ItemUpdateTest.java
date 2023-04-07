package jpaBook.jpaShop.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.persistence.EntityManager;

@SpringBootTest
@ActiveProfiles("test")
public class ItemUpdateTest {
	
	@Autowired
	private EntityManager em;
	
	@Test
	void updateTest() {
		
	}
}
