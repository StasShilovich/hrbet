package com.shilovich.hrbet.controller.filter;

import com.shilovich.hrbet.bean.Permission;
import com.shilovich.hrbet.bean.Role;
import com.shilovich.hrbet.bean.User;
import com.shilovich.hrbet.controller.CommandPermissionAccess;
import com.shilovich.hrbet.controller.CommandType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

import static com.shilovich.hrbet.controller.CommandParameter.*;

public class PermissionAccessFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(PermissionAccessFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTR_USER_AUTH);
        if (user == null || user.getRole() == null) {
            Permission permission = new Permission();
            permission.setName(QUEST_PERMISSION);
            Role role = new Role();
            role.setPermissions(Set.of(permission));
            user = new User();
            user.setRole(role);
        }
        // TODO: 07.12.2020 check command not null
        String commandName = request.getParameter(COMMAND_PARAMETER);
        CommandType command = CommandType.getCommand(commandName);
        Set<Permission> permissions = user.getRole().getPermissions();
        boolean isAllowed = isPermissionAllowed(permissions, command);
        if (!isAllowed) {
            // TODO: 07.12.2020 403 page
        }
        filterChain.doFilter(request, response);
    }

    private boolean isPermissionAllowed(Set<Permission> permissions, CommandType command) {
        for (Permission permission : permissions) {
            CommandPermissionAccess permissionAccess = CommandPermissionAccess.getPermission(permission.getName());
            boolean commandAllowed = permissionAccess.isCommandAllowed(command);
            if (commandAllowed) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
    }
}
