package com.saul.blockchainlearning.mapper;

import com.saul.blockchainlearning.serialization.Serializer;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBFactory;
import org.iq80.leveldb.DBIterator;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class BlockMapper {

    private DB db = null;
    @Value("${leveldb_block.db_folder")
    private String db_folder;

    private final String charset = "utf-8";

    public void initLevelDB() {
        DBFactory factory = new Iq80DBFactory();
        Options options = new Options();
        options.createIfMissing(true);
        try {
            this.db = factory.open(new File(db_folder), options);
        } catch (IOException e) {
            System.out.println("failed when open db");
            e.printStackTrace();
        }
    }

    public void put(String key, Object obj) {
        try {
            this.db.put(key.getBytes(charset), Serializer.serialize(obj));
        } catch (UnsupportedEncodingException e) {
            System.out.println("failed when put");
            e.printStackTrace();
        }
    }

    public Object get(String key) {
        byte[] val = null;
        try {
            val = db.get(key.getBytes(charset));
        } catch (Exception e) {
            System.out.println("failed when get");
            e.printStackTrace();
            return null;
        }
        if(val == null) {
            return null;
        }
        return Serializer.deserialize(val);
    }


    public void delete(String key) {
        try {
            db.delete(key.getBytes(charset));
        } catch (Exception e) {
            System.out.println("failed when delete");
            e.printStackTrace();
        }
    }

    public void close() {
        if(db != null) {
            try {
                db.close();
            } catch (IOException e) {
                System.out.println("failed when close");
                e.printStackTrace();
            }
        }
    }

    public List<String> getAllKeys() {
        List<String> keys = new ArrayList<>();
        DBIterator iterator = null;
        try {
            iterator = db.iterator();
            while(iterator.hasNext()) {
                Map.Entry<byte[],byte[]> item = iterator.next();
                String key = new String(item.getKey(), charset);
                keys.add(key);
            }
        } catch(Exception e) {
            System.out.println("failed when get all keys");
            e.printStackTrace();
        } finally {
            if(iterator != null) {
                try {
                    iterator.close();
                } catch (IOException e) {
                    System.out.println("failed when close iterator");
                    e.printStackTrace();
                }
            }
        }
        return keys;
    }
}
