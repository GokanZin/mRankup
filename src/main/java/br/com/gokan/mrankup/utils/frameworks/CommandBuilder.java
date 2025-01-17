package br.com.gokan.mrankup.utils.frameworks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.function.BiFunction;

public class CommandBuilder {
    private final CommandHandler command;
    private BiFunction<CommandSender, String[], Boolean> executor;
    private BiFunction<CommandSender, String[], List<String>> tabCompleter;

    public CommandBuilder(String name) {
        this.command = new CommandHandler(name) {
            @Override
            public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
                return tabCompleter.apply(commandSender, strings);
            }

            @Override
            public boolean onExecute(CommandSender sender, String label, String[] args) {
                return executor.apply(sender, args);
            }
        };
    }

    public CommandBuilder description(String description) {
        command.setDescription(description);
        return this;
    }

    public CommandBuilder aliases(String... aliases) {
        command.setAliases(aliases);
        return this;
    }

    public CommandBuilder permission(String permission) {
        command.setPermission(permission);
        return this;
    }

    public CommandBuilder permissionMessage(String message) {
        command.setPermissionMessage(message);
        return this;
    }

    public CommandBuilder executor(BiFunction<CommandSender, String[], Boolean> executor) {
        this.executor = executor;
        return this;
    }



    public CommandHandler build() {
        CommandRegistry.registerCommand(command, command.getName());
        return command;
    }

}
