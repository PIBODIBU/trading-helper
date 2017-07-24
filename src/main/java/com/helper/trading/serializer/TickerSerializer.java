package com.helper.trading.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.knowm.xchange.dto.marketdata.Ticker;

import java.lang.reflect.Type;

public class TickerSerializer implements JsonSerializer<Ticker> {
    @Override
    public JsonElement serialize(Ticker ticker, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("last", ticker.getLast());
        jsonObject.addProperty("volume", ticker.getVolume());
        jsonObject.addProperty("high", ticker.getHigh());
        jsonObject.addProperty("low", ticker.getLow());

        return jsonObject;
    }
}
