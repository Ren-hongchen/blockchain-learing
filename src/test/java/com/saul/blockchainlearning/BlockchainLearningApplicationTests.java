package com.saul.blockchainlearning;

import com.saul.blockchainlearning.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlockchainLearningApplicationTests {

	@Test
	void contextLoads() {
	}


	@Test
	void getPrivateKey() throws Exception{
		AccountService accountService = new AccountService();
		System.out.println(accountService.getPrivateKey());
	}

	@Test
	void getWIF() throws Exception {
		AccountService accountService = new AccountService();
		System.out.println(accountService.getWIF("0c28fca386c7a227600b2fe50b7cae11ec86d3bf1fbe471be89827e19d72aa1d"));
	}
}
