package com.saul.blockchainlearning.scripts;

import com.saul.blockchainlearning.algorithm.ECDSA;
import com.saul.blockchainlearning.algorithm.Hash160;
import org.bouncycastle.util.encoders.Hex;

import java.util.Stack;

public class ScriptInterpreter {

    //P2PKH
    public static boolean execute(String lockScript, String unlockScript, String message) throws Exception {
        Stack<String> stack = new Stack<>();
        int index1 = Integer.parseInt(unlockScript.substring(0,2)) * 2;
        stack.push(unlockScript.substring(2, index1));   //push signature
        int index2 = Integer.parseInt(unlockScript.substring(index1,index1+2)) * 2;
        stack.push(unlockScript.substring(index1+2, index2)); //push public_key

        if(lockScript.startsWith("76a9")) {
            stack.push(stack.peek());  //OP_DUP 76
            String value = stack.pop();
            stack.push(Hash160.hash160(Hex.decode(value))); //OP_HASH160 a9

            int index3 = Integer.parseInt(lockScript.substring(4,6)) * 2;
            if(stack.pop().equals(lockScript.substring(6,index3))) {  //OP_EQUALVERIFY
                String public_key = stack.pop();
                String signature = stack.pop();
                if(ECDSA.verify(message,signature,public_key)) {    //OP_CHECKSIG
                    stack.push("1");
                } else {
                    stack.push("0");
                }
            }
        }

        return stack.peek().equals("1");
    }

}
