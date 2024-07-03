package com.kesm.backend;

import com.kesm.backend.repositories.ContactTypeRepository;
import com.kesm.backend.repositories.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(classes = DemoApplicationTests.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class DemoApplicationTests {
	@Autowired
	private ContactTypeRepository contactTypeRepository;

	@Test
	public void shouldHaveContactTypeWhenTheApplicationIsStarted() {
		then(this.contactTypeRepository.count()).isGreaterThanOrEqualTo(2);
	}

	@Test
	public void shouldFindTwoPersonWithName() {
		then(this.contactTypeRepository.findByTitleStartsWithIgnoreCase("E-Mail")).hasSize(1);
		then(this.contactTypeRepository.findByTitleStartsWithIgnoreCase("Mobil")).hasSize(1);
	}
}
