package com.saul.blockchainlearning.network;

import com.saul.blockchainlearning.mapper.TransactionMapper;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;

public class UTXOService extends utxoRPCGrpc.utxoRPCImplBase {

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public void getUTXObyKey(Key public_key, StreamObserver<utxo_list> responseObserver) {

    }
}
