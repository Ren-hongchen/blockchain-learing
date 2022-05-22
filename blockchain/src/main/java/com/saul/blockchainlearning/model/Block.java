package com.saul.blockchainlearning.model;

import lombok.Data;

@Data
//value : 'b' + 32-byte block hash
public class Block {
    private BlockHeader blockHeader; //The block header.
    private int height;  //The height.
    private int transactionsCount;  //The number of transactions.
    //private int validateExtent;   To what extent this block is validated.
    private String fileNumber; //4-byte file number; In which file, and where in that file, the block data is stored.
    //private String undo_fileNumber; In which file, and where in that file, the undo data is stored.
}
