package com.lalo.wallet.wallet.service;

import com.lalo.wallet.wallet.algorithm.Hash160;
import com.lalo.wallet.wallet.algorithm.Hash256;
import com.lalo.wallet.wallet.dto.*;
import com.lalo.wallet.wallet.globalvariable.GlobalVariable;
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
        transactionDTO.setOutputCounter(userTxDTO.getOutputs().size());

        double outputValue = 0.00d;
        List<UTXO> utxoList = new ArrayList<>();
        utxoList.add(getUTXO());
        double inputValue = utxoList.get(0).getAmount();
        while(inputValue - 0.001 <= outputValue) {
            UTXO utxo1 = getUTXO();
            inputValue += utxo1.getAmount();
            utxoList.add(utxo1);
        }

        List<UserOutputDTO> outputs = userTxDTO.getOutputs();
        List<OutputDTO> outputDTOs = new ArrayList<>();

        if(inputValue - 0.001 > outputValue) {
            UserOutputDTO unspentAmount = new UserOutputDTO();
            unspentAmount.setAddress(GlobalVariable.account.getPublic_key());
            unspentAmount.setAmount(inputValue - 0.001 - outputValue);
            outputs.add(unspentAmount);
        }

        for(UserOutputDTO output : outputs) {
            OutputDTO outputDTO = new OutputDTO();
            outputDTO.setValue(output.getAmount());
            outputValue += output.getAmount();

            String hash = Hash160.hash160(Hex.decode(output.getAddress()));
            String scriptPubKey_hex = "76a914" + hash + "88ac";
            ScriptPubKey scriptPubKey = new ScriptPubKey();
            scriptPubKey.setHex(scriptPubKey_hex);
            scriptPubKey.setAsm("OP_DUP OP_HASH160 " + hash + " OP_EQUALVERIFY OP_CHECKSIG");
            scriptPubKey.setType("pubkeyhash");
            scriptPubKey.setAddress(output.getAddress());
            outputDTO.setScript(scriptPubKey);

            outputDTOs.add(outputDTO);
        }

        transactionDTO.setOutput(outputDTOs);



        List<InputDTO> inputList = new ArrayList<>();
        List<InputDTO> inputList_copy = new ArrayList<>();
        for(UTXO utxo : utxoList) {
            InputDTO inputDTO = new InputDTO();
            inputDTO.setPrev_txid(utxo.getTxid());
            inputDTO.setPrev_vout(utxo.getVout());
            inputList_copy.add(inputDTO);
            inputDTO.setScript(utxo.getScriptPubKey());
            inputList.add(inputDTO);
        }

        transactionDTO.setInputCounter(inputList.size());

        for(int i = 0; i<inputList.size(); i++) {
            inputList_copy.get(i).setScript(inputList.get(i).getScript());
            transactionDTO.setInput(inputList_copy);
            String hash = Hash256.hash256(Serializer.serialize(transactionDTO));
            String scriptSig = ScriptUtil.getScriptSig(GlobalVariable.account.getPrivate_key(), GlobalVariable.account.getPublic_key(), hash);
            inputList.get(i).setScript(scriptSig);
            inputList_copy.get(i).setScript("");
        }

        transactionDTO.setInput(inputList);


        transactionDTO.setSize(Serializer.serialize(transactionDTO).length);
        transactionDTO.setTxid(Hash256.hash256(Serializer.serialize(transactionDTO)));

        return transactionDTO;
    }

    private UTXO getUTXO() {
        UTXO utxo =  GlobalVariable.utxoList.remove(GlobalVariable.utxoList.size() - 1);
        GlobalVariable.utxos_not_confirmed.put(utxo.getTxid(), utxo);
        return utxo;
    }
}
