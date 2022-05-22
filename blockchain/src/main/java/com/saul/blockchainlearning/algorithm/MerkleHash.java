package com.saul.blockchainlearning.algorithm;

import com.saul.blockchainlearning.model.Transaction;
import com.saul.blockchainlearning.serialization.Serializer;
import org.bouncycastle.util.encoders.Hex;

import java.util.ArrayList;
import java.util.List;

public class MerkleHash {
    private static String do_hash(List<byte[]> list) throws Exception{
        while(true) {
            int size =list.size();
            if(size % 2 != 0) {
                list.add(list.get(size - 1));
                size += 1;
            }
            for(int i = 0; i < size - 1; i++) {
                String hash_1 = Hash256.hash256(list.get(i));
                String hash_2 = Hash256.hash256(list.get(i + 1));
                String hash = Hash256.hash256((hash_1.concat(hash_2)).getBytes());
                list.set(i, hash.getBytes());
            }
            list = list.subList(0,size / 2);
            if(list.size() == 1){
                break;
            }
        }
        return new String(Hex.encode(list.get(0)));
    }
    public static String merkle_hash(List<Transaction> transactionList) throws Exception {
        List<byte[]> list = new ArrayList<>();
        for(Transaction transaction : transactionList) {
            byte[] data = Serializer.serialize(transaction);
            list.add(data);
        }
        return do_hash(list);
    }

}
