package com.saul.blockchainlearning;

import com.saul.blockchainlearning.algorithm.Hash160;
import com.saul.blockchainlearning.algorithm.Hash256;
import com.saul.blockchainlearning.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

@SpringBootTest
class BlockchainLearningApplicationTests {

	@Test
	void contextLoads() {
	}


	@Test
	void hash256() throws Exception{
		String s = Hash256.hash256("bitcoin is awesome".getBytes(StandardCharsets.UTF_8));
		System.out.println(s);
	}

	@Test
	void hash160() throws Exception {
		String s = Hash160.hash160("bitcoin is awesome".getBytes(StandardCharsets.UTF_8));
		System.out.println(s);
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
