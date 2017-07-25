package com.helper.trading.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.helper.trading.model.CurrencyRate;

import java.lang.reflect.Type;

public class CurrencyRateSerializer implements JsonSerializer<CurrencyRate> {
    private static CurrencyPairSerializer currencyPairSerializer;
    private static StockSerializer stockSerializer;

    static {
        currencyPairSerializer = new CurrencyPairSerializer();
        stockSerializer = new StockSerializer();
    }

    @Override
    public JsonElement serialize(CurrencyRate currencyRate, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", currencyRate.getId());
        jsonObject.addProperty("rate", currencyRate.getRate());

        jsonObject.add("currencyPair", currencyPairSerializer.serialize(currencyRate.getCurrencyPair(), type, jsonSerializationContext));
        jsonObject.add("stock", stockSerializer.serialize(currencyRate.getStock(), type, jsonSerializationContext));

        return jsonObject;
    }
}
