package project.commands;

import java.util.ArrayList;
import java.util.List;

public class Invoker {
    private final List<Command> commandList = new ArrayList<>();

    public void addCommand(Command command) {
        commandList.add(command);
    }

    public void executeCommands() {
        commandList.forEach(Command::execute);
        commandList.clear();
    }
}
