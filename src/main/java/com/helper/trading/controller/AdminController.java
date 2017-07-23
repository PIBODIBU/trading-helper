package com.helper.trading.controller;

import com.helper.trading.application.configuration.security.UserDetails;
import com.helper.trading.model.security.Permission;
import com.helper.trading.model.security.Role;
import com.helper.trading.model.user.User;
import com.helper.trading.serializer.PermissionSerializer;
import com.helper.trading.serializer.RoleSerializer;
import com.helper.trading.serializer.UserSerializer;
import com.helper.trading.service.PermissionService;
import com.helper.trading.service.RoleService;
import com.helper.trading.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private RoleService roleService;
    private PermissionService permissionService;
    private static Gson gson;

    static {
        gson = new GsonBuilder()
                .registerTypeAdapter(Role.class, new RoleSerializer())
                .registerTypeAdapter(User.class, new UserSerializer())
                .registerTypeAdapter(Permission.class, new PermissionSerializer())
                .create();
    }

    @Autowired
    public void setUserService(@Qualifier("UserService") UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(@Qualifier("RoleService") RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPermissionService(@Qualifier("PermissionService") PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @RequestMapping(value = "/perm", method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView("admin.jsp");
        User user = ((UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser();

        modelAndView.addObject("user", gson.toJson(user));
        modelAndView.addObject("roles", gson.toJson(user.getRoles()));
        modelAndView.addObject("my_permissions", gson.toJson(permissionService.getMyPermissions()));
        //modelAndView.addObject("other_permissions", gson.toJson(permissionService.getUserPermissions(2L)));

        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/role/add", method = RequestMethod.GET)
    public String addUser(@RequestParam(name = "role_name") String name) {
        Role role = new Role();
        HashSet<Permission> permissions = new HashSet<>();

        // Prepare role's permissions
        permissions.add(permissionService.get(1L));
        permissions.add(permissionService.get(2L));

        // Prepare role model
        role.setRoleName(name);
        role.setPermissions(permissions);

        // Save model to db
        roleService.create(role);

        return "DONE";
    }
}