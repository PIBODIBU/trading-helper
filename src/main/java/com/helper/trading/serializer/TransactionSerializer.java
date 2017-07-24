package com.helper.trading.serializer;

import com.google.gson.*;
import com.helper.trading.model.CurrencyPair;
import com.helper.trading.model.Transaction;

import java.lang.reflect.Type;

public class TransactionSerializer implements JsonSerializer<Transaction> {
    private static UserSerializer userSerializer;
    private static StockSerializer stockSerializer;
    private static CurrencyPairSerializer currencyPairSerializer;
    private static TxTypeSerializer txTypeSerializer;

    static {
        userSerializer = new UserSerializer();
        stockSerializer = new StockSerializer();
        currencyPairSerializer = new CurrencyPairSerializer();
        txTypeSerializer = new TxTypeSerializer();
    }

    @Override
    public JsonElement serialize(Transaction transaction, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", transaction.getId());
        jsonObject.addProperty("total", transaction.getTotal());
        jsonObject.addProperty("tradePrice", transaction.getTradePrice());
        jsonObject.addProperty("quantity", transaction.getQuantity());
        jsonObject.addProperty("date", transaction.getTradeDate().toString());
        jsonObject.addProperty("notes", transaction.getNotes());
        jsonObject.add("user", userSerializer.serialize(transaction.getUser(), type, jsonSerializationContext));
        jsonObject.add("stock", stockSerializer.serialize(transaction.getStock(), type, jsonSerializationContext));
        jsonObject.add("currencyPair", currencyPairSerializer.serialize(transaction.getCurrencyPair(), type, jsonSerializationContext));
        jsonObject.add("txType", txTypeSerializer.serialize(transaction.getTxType(), type, jsonSerializationContext));
        jsonObject.addProperty("currencyPairInfo", new GsonBuilder().create().toJson(transaction.getCurrencyPairInfo()));

        return jsonObject;
    }
}
