package com.saul.blockchainlearning.controller;

import com.saul.blockchainlearning.algorithm.Hash256;
import com.saul.blockchainlearning.dto.InputDTO;
import com.saul.blockchainlearning.dto.OutputDTO;
import com.saul.blockchainlearning.dto.TransactionDTO;
import com.saul.blockchainlearning.mapper.TransactionMapper;
import com.saul.blockchainlearning.model.Transaction;
import com.saul.blockchainlearning.pool.TxwithPoolFormat;
import com.saul.blockchainlearning.scripts.ScriptInterpreter;
import com.saul.blockchainlearning.serialization.Serializer;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionMapper transactionMapper;

    @PostMapping("/transaction/initiate")
    public void initiateTransaction(TransactionDTO transactionDTO) throws Exception {
        double input_value = 0.00d;
        double output_value = 0.00d;
        List<InputDTO> input_list = transactionDTO.getInput();
        for(InputDTO input : input_list) {
            String unlock_script = input.getScript();

            String index_length = String.valueOf(input.getPrev_vout().length());
            String index = new String(Hex.encode(input.getPrev_vout().getBytes()));
            String key = input.getPrev_txid() + index_length + index;

            Transaction tx = (Transaction)transactionMapper.get(key);
            String pre_lock_script = tx.getLockScript();

            String message = new String(Hex.encode(Hash256.SHA256(Serializer.serialize(transactionDTO))));
            if(!ScriptInterpreter.execute(pre_lock_script, unlock_script, message)) {
                return;
            }
            input_value += tx.getAmount();
        }

        List<OutputDTO> output_list = transactionDTO.getOutput();
        for(OutputDTO output : output_list) {
            output_value += output.getValue();
        }

        double fee = input_value - output_value;
        int bytes = Serializer.serialize(transactionDTO).length;

        TxwithPoolFormat tx = new TxwithPoolFormat();
        tx.setTransactionDTO(transactionDTO);
        tx.setAge(1);
        tx.setBytes(bytes);
        tx.setFee(fee);
        tx.setPriority((fee * 100000000 * 1)/bytes);


    }
}
