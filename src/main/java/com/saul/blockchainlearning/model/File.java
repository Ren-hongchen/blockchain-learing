package com.saul.blockchainlearning.model;

import lombok.Data;

@Data
//value : 'f' + 4-byte file number
public class File {
    private int blockNumber;  //The number of blocks stored in the block file with that number.
    private Long fileSize; //The size of the block file with that number
    //private Long undo_fileSize;  The size of the undo file with that number
    private int lowestHeight; //The lowest and highest height of blocks stored in the block file with that number.
    private int highestHeight;

}
