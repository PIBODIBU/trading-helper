package com.helper.trading.application.configuration.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.helper.trading.model.CurrencyPair;
import com.helper.trading.model.Stock;
import com.helper.trading.model.Transaction;
import com.helper.trading.model.TxType;
import com.helper.trading.model.security.Permission;
import com.helper.trading.model.security.Role;
import com.helper.trading.model.user.User;
import com.helper.trading.model.user.UserData;
import com.helper.trading.serializer.*;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GsonConfig {
    @Bean(name = "Gson")
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(Role.class, new RoleSerializer())
                .registerTypeAdapter(User.class, new UserSerializer())
                .registerTypeAdapter(Permission.class, new PermissionSerializer())
                .registerTypeHierarchyAdapter(UserData.class, new UserDataSerializer())
                .registerTypeHierarchyAdapter(Ticker.class, new TickerSerializer())
                .registerTypeHierarchyAdapter(Transaction.class, new TransactionSerializer())
                .registerTypeHierarchyAdapter(Stock.class, new StockSerializer())
                .registerTypeHierarchyAdapter(CurrencyPair.class, new CurrencyPairSerializer())
                .registerTypeHierarchyAdapter(TxType.class, new TxTypeSerializer())
                .create();
    }
}
