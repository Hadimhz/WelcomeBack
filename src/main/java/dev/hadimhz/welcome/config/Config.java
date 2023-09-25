package dev.hadimhz.welcome.config;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class Config {

    public int duration = 15;

    public String welcomePartyExpired = "That welcome party has expired!";
    public String welcomeYourselfError = "You cannot welcome yourself!";
    public String alreadyWelcomed = "You've already welcomed this player!";


    public String onPlayerJoin = "&7A player has joined run the command &d/wb &7to welcome them!";

    public String firstJoin = "Welcome %player%! Hope you enjoy your stay";
    public String joinBack = "Welcome back %player%!";

    public List<String> commandsToExecute = ImmutableList.of("eco give %player% 50");
    public List<String> CommandsToExecuteOnFirstJoin = ImmutableList.of("eco give %player% 100");
}
