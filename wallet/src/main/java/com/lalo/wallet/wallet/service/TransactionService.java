package com.lalo.wallet.wallet.service;

import com.lalo.wallet.wallet.algorithm.Hash160;
import com.lalo.wallet.wallet.algorithm.Hash256;
import com.lalo.wallet.wallet.dto.*;
import com.lalo.wallet.wallet.scripts.ScriptUtil;
import com.lalo.wallet.wallet.serialization.Serializer;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    public TransactionDTO getDTO(UserTxDTO userTxDTO) throws Exception {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setVersion(0);
        transactionDTO.setLocktime(0);
        transactionDTO.setInputCounter(userTxDTO.getInputs().size());
        transactionDTO.setOutputCounter(userTxDTO.getOutputs().size());

        List<UserOutputDTO> outputs = userTxDTO.getOutputs();
        List<OutputDTO> outputDTOs = new ArrayList<>();
        for(UserOutputDTO output : outputs) {
            OutputDTO outputDTO = new OutputDTO();
            outputDTO.setValue(output.getAmount());
            String scriptPubKey = "76a914" + Hash160.hash160(Hex.decode(output.getAddress())) + "88ac";
            outputDTO.setScript(scriptPubKey);
            outputDTOs.add(outputDTO);
        }

        transactionDTO.setOutput(outputDTOs);

        List<InputDTO> inputs = userTxDTO.getInputs();
        List<InputDTO> inputs_copy = new ArrayList<>();
        for(InputDTO inputDTO : inputs) {
            InputDTO input = new InputDTO();
            input.setPrev_txid(inputDTO.getPrev_txid());
            input.setPrev_vout(inputDTO.getPrev_vout());
            input.setScript("");
            inputs_copy.add(input);
        }

        for(int i = 0; i < userTxDTO.getInputs().size(); i++) {
            inputs_copy.set(i,inputs.get(i));
            transactionDTO.setInput(inputs_copy);
            String hash = Hash256.hash256(Serializer.serialize(transactionDTO));
            String scriptSig = ScriptUtil.getScriptSig(userTxDTO.getPrivate_key(), userTxDTO.getPublic_key(), hash);
            inputs.get(i).setScript(scriptSig);
            inputs_copy.get(i).setScript("");
        }

        transactionDTO.setInput(inputs);
        return transactionDTO;
    }
}
