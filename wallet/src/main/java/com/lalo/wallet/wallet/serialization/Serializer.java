package com.lalo.wallet.wallet.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Serializer {

    private static final Gson gson = new GsonBuilder().serializeNulls().create();

    public static byte[] serialize(Object obj) {
        return gson.toJson(obj).getBytes();
    }

    public static <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return gson.fromJson(new String(bytes), clazz);
    }
}
