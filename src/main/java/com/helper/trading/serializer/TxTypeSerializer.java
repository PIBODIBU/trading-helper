package com.helper.trading.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.helper.trading.model.TxType;

import java.lang.reflect.Type;

public class TxTypeSerializer implements JsonSerializer<TxType> {
    @Override
    public JsonElement serialize(TxType txType, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", txType.getId());
        jsonObject.addProperty("name", txType.getName());

        return jsonObject;
    }
}
