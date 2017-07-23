package com.helper.trading.application.configuration.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.helper.trading.model.security.Permission;
import com.helper.trading.model.security.Role;
import com.helper.trading.model.user.User;
import com.helper.trading.model.user.UserData;
import com.helper.trading.serializer.PermissionSerializer;
import com.helper.trading.serializer.RoleSerializer;
import com.helper.trading.serializer.UserDataSerializer;
import com.helper.trading.serializer.UserSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GsonConfig {
    @Bean(name = "FutoriaGson")
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(Role.class, new RoleSerializer())
                .registerTypeAdapter(User.class, new UserSerializer())
                .registerTypeAdapter(Permission.class, new PermissionSerializer())
                .registerTypeHierarchyAdapter(UserData.class, new UserDataSerializer())
                .create();
    }
}
