package com.lalo.wallet.wallet.serialization;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.reflect.Reflection;
import com.google.gson.Gson;
import org.springframework.util.ReflectionUtils;

import java.nio.charset.StandardCharsets;


public class Serializer {

    private static Gson gson = new Gson();

    public static byte[] serialize(Object obj) {
        return gson.toJson(obj).getBytes();
    }

    public static <T> Object deserialize(byte[] bytes, Class<T> clazz) {
        return gson.fromJson(new String(bytes), clazz);
    }
}
