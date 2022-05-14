package com.saul.blockchainlearning.serialization;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.stereotype.Component;

@Component
public class Serializer {

    public byte[] serialize(Object obj) {
        return JSON.toJSONBytes(obj, SerializerFeature.DisableCircularReferenceDetect);
    }

    public Object deserialize(byte[] bytes) {
        String str = new String(bytes);
        return JSON.parse(str);
    }
}
