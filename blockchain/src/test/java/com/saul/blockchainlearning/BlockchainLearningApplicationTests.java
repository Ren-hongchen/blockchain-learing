package com.saul.blockchainlearning;

import com.saul.blockchainlearning.algorithm.ECDSA;
import com.saul.blockchainlearning.algorithm.Hash160;
import com.saul.blockchainlearning.algorithm.Hash256;
import com.lalo.wallet.wallet.service.AccountService;
import com.saul.blockchainlearning.test.Test1;
import com.saul.blockchainlearning.test.Test2;
import org.bouncycastle.util.encoders.Hex;
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
		accountService.setKeyPair();
		System.out.println(accountService.getPrivate_key());
	}

	@Test
	void getWIF() throws Exception {
		AccountService accountService = new AccountService();
		//System.out.println(accountService.getWIF("0c28fca386c7a227600b2fe50b7cae11ec86d3bf1fbe471be89827e19d72aa1d"));
	}

	@Test
	void getPublicKey() {
		//System.out.println(ECDSA.getPublicKey("56abac97d01f910f45e939c94e0f90f799b0d6474882502d0a7a0197e42a2291"));
	}

	@Test
	void getAddress() throws Exception {
		AccountService accountService = new AccountService();
		//System.out.println(accountService.getAddress("02d0de0aaeaefad02b8bdc8a01a1b8b11c696bd3d66a2c5f10780d95b7df42645c"));
	}

	@Test
	void signAndverify() throws Exception {
		byte[] hash = Hash256.SHA256("a secret message!".getBytes());
		String signature = ECDSA.sign(new String(Hex.encode(hash)),"0c28fca386c7a227600b2fe50b7cae11ec86d3bf1fbe471be89827e19d72aa1d");
		System.out.println(signature);
		System.out.println(ECDSA.verify(new String(Hex.encode(hash)), signature, "02d0de0aaeaefad02b8bdc8a01a1b8b11c696bd3d66a2c5f10780d95b7df42645c"));
	}

	@Test
	void test_pool() {
		Test1 test1 = new Test1();
		Test2 test2 = new Test2();
		test1.test_pool();
		test2.get();
	}
}
