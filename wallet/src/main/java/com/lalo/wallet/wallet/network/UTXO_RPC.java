package com.lalo.wallet.wallet.network;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class UTXO_RPC {
    public static utxo_list getUTXO(String public_key) throws Exception {
        Key key = Key.newBuilder().setKey(public_key).build();
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1",5555).usePlaintext().build();
        utxoRPCGrpc.utxoRPCBlockingStub blockingStub = utxoRPCGrpc.newBlockingStub(channel);
        utxo_list result = blockingStub.getUTXObyKey(key);
        channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
        return result;
    }
}
