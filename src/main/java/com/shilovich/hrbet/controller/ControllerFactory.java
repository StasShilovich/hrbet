package com.shilovich.hrbet.controller;

import com.shilovich.hrbet.controller.impl.*;
import com.shilovich.hrbet.controller.model.CommandEnum;

import java.util.EnumMap;
import java.util.Map;

public class ControllerFactory {
    private static final ControllerFactory instance = new ControllerFactory();
    private static final Map<CommandEnum, Command> commands = new EnumMap<>(CommandEnum.class);

    private ControllerFactory() {
    }

    static {
        commands.put(CommandEnum.RACES, new RacesPageCommandImpl());
        commands.put(CommandEnum.LOGIN, new LogInCommandImpl());
        commands.put(CommandEnum.LOGOUT, new LogOutCommandImpl());
        commands.put(CommandEnum.REGISTRATION, new RegistrationCommandImpl());
        commands.put(CommandEnum.FORWARD_REGISTRATION, new ForwardRegistrationCommandImpl());
        commands.put(CommandEnum.RACE, new RacePageCommandImpl());
        commands.put(CommandEnum.PROFILE, new ProfileCommandImpl());
        commands.put(CommandEnum.COOKIE, new CookieCommandImpl());
    }

    public Command getCommand(CommandEnum commandEnum) {
        return commands.get(commandEnum);
    }

    public static ControllerFactory getInstance() {
        return instance;
    }
}