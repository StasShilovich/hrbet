package com.shilovich.hrbet.controller.filter;

import com.shilovich.hrbet.bean.PermissionEnum;
import com.shilovich.hrbet.bean.User;
import com.shilovich.hrbet.controller.Command;
import com.shilovich.hrbet.controller.CommandMap;
import com.shilovich.hrbet.controller.CommandType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

import static com.shilovich.hrbet.controller.CommandParameter.ATTR_USER_AUTH;
import static com.shilovich.hrbet.controller.CommandParameter.COMMAND_PARAMETER;
import static com.shilovich.hrbet.controller.CommandParameter.PAGE_403;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * The type Permission access filter.
 * <p>
 * A filter that takes care of user permissions when trying to access certain pages.
 */
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
        Set<PermissionEnum> permissions;
        if (user != null && user.getRole() != null && user.getRole().getPermissions() != null) {
            permissions = user.getRole().getPermissions();
        } else {
            permissions = EnumSet.of(PermissionEnum.QUEST_BASIC);
        }
        String commandName = request.getParameter(COMMAND_PARAMETER);
        if (isNotEmpty(commandName) && CommandType.isContains(commandName)) {
            CommandType commandType = CommandType.getCommand(commandName);
            Command command = CommandMap.getInstance().getCommand(commandType);
            boolean isAllowed = command.isAllowed(permissions);
            if (!isAllowed) {
                request.getRequestDispatcher(PAGE_403).forward(request, response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
