package com.helper.trading.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.helper.trading.model.Stock;

import java.lang.reflect.Type;

public class StockSerializer implements JsonSerializer<Stock>{
    @Override
    public JsonElement serialize(Stock stock, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", stock.getId());
        jsonObject.addProperty("name", stock.getName());

        return jsonObject;
    }
}
