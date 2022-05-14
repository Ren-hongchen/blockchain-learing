package com.saul.blockchainlearning.serialization;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;


public class Serializer {

    public static byte[] serialize(Object obj) {
        return JSON.toJSONBytes(obj, SerializerFeature.DisableCircularReferenceDetect);
    }

    public static Object deserialize(byte[] bytes) {
        String str = new String(bytes);
        return JSON.parse(str);
    }
}
