package com.lalo.wallet.wallet.network;

import com.lalo.wallet.wallet.dto.TransactionDTO;
import com.lalo.wallet.wallet.serialization.Serializer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class TX_RPC {
    public static txid sendTransaction(TransactionDTO transactionDTO) throws Exception {
        Transaction transaction = Transaction.parseFrom(Serializer.serialize(transactionDTO));
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1",5555).usePlaintext().build();
        txRPCGrpc.txRPCBlockingStub blockingStub = txRPCGrpc.newBlockingStub(channel);
        txid id = blockingStub.sendTransaction(transaction);
        channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
        return id;
    }
}
