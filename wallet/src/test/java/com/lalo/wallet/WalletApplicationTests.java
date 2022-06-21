package com.lalo.wallet;

import com.lalo.wallet.wallet.algorithm.ECDSA;
import com.lalo.wallet.wallet.algorithm.Hash256;
import com.lalo.wallet.wallet.entity.ServerInfo;
import com.lalo.wallet.wallet.serialization.Serializer;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WalletApplicationTests {
	@Value("${PrivateKey}")
	private String private_key;

	@Value("${PublicKey}")
	private String public_key;

	@Test
	void contextLoads() {
		System.out.println(private_key);
		System.out.println(public_key);
	}

	@Test
	void SerializerTest() throws Exception {
		ServerInfo serverInfo = new ServerInfo();
		serverInfo.setName("bitcoin-wallet");
		serverInfo.setPublic_key("03508817f4864bb2d44361a03420ea42512f14c6f281d7fdeb62040e5b2119661b");
		byte[] aa = Hash256.SHA256(Serializer.serialize(serverInfo));
		String hash = Hex.toHexString(aa);
		System.out.println(hash);
		System.out.println(private_key);
		String sign = ECDSA.sign(hash, private_key);
		System.out.println(sign);

	}
}
