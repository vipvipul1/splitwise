package org.vip.splitwise.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandExecutor {
    private List<Command> commands;

    @Autowired
    public CommandExecutor(List<Command> commands) {
        this.commands = commands;
    }

    public void routeCommand(String input) {
        for (Command command: commands) {
            if (command.matches(input)) {
                command.execute(input);
            }
        }
    }
}
