package com.helper.trading.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.helper.trading.model.CurrencyPair;

import java.lang.reflect.Type;

public class CurrencyPairSerializer implements JsonSerializer<CurrencyPair> {
    @Override
    public JsonElement serialize(CurrencyPair currencyPair, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", currencyPair.getId());
        jsonObject.addProperty("name", currencyPair.getName());

        return jsonObject;
    }
}