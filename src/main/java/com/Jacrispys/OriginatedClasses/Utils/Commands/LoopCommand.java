package com.Jacrispys.OriginatedClasses.Utils.Commands;

import com.Jacrispys.OriginatedClasses.OriginatedClassesMain;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;


import java.util.Objects;

import static com.Jacrispys.OriginatedClasses.Utils.Chat.ColorChat.color;

public class LoopCommand implements CommandExecutor {


    @SuppressWarnings("unused")
    private final Plugin plugin;

    public LoopCommand(OriginatedClassesMain main) {
        this.plugin = main;

        Objects.requireNonNull(main.getCommand("loop")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if ((args.length < 3)) {
            sender.sendMessage(color("&cLoop Command Usage: /loop <command, chat> <amt> <optional-boolean-console> <command/chat>"));
            return false;
        } else {
            boolean consoleSender = false;
            boolean optionPresent = false;

            if (args.length >= 4 && (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false"))) {
                consoleSender = Boolean.parseBoolean(args[2]);
                optionPresent = true;
            } else if (!(sender instanceof Player)) {
                consoleSender = true;
            }

            try {
                @SuppressWarnings("unused")
                int test = Integer.parseInt(args[1]);

            } catch (NullPointerException | NumberFormatException e) {
                sender.sendMessage(color("&cError: Cannot loop a non-integer amount."));
                return false;
            }

            int delay;
            StringBuilder sb = new StringBuilder();
            if (optionPresent) {
                delay = 3;
            } else {
                delay = 2;
            }
            for (int a = delay; a <= args.length - 1; a++) {
                sb.append(args[a]);
                sb.append(" ");
            }
            String command = String.valueOf(sb);


            switch (args[0]) {
                case ("command"):
                case ("1"):

                    if (consoleSender) {
                        for (int i = 0; i < Integer.parseInt(args[1]); i++) {
                            BukkitRunnable commandDelay = new BukkitRunnable() {
                                @Override
                                public void run() {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                                    sender.sendMessage(color("Complete!"));
                                }
                            };
                            commandDelay.runTaskLater(plugin, 5);
                        }
                    } else {
                        for (int i = 0; i < Integer.parseInt(args[1]); i++) {
                            BukkitRunnable commandDelay = new BukkitRunnable() {
                                @Override
                                public void run() {
                                    Player p = (Player) sender;
                                    Bukkit.dispatchCommand(p, command);
                                    sender.sendMessage(color("Complete!"));
                                }
                            };
                            commandDelay.runTaskLater(plugin, 5);
                        }
                    }
                    return false;
                case ("chat"):
                case ("2"):
                    if (consoleSender) {
                        for (int i = 0; i < Integer.parseInt(args[1]); i++) {
                            BukkitRunnable commandDelay = new BukkitRunnable() {
                                @Override
                                public void run() {
                                    Bukkit.getConsoleSender().sendMessage(color(command));
                                    sender.sendMessage(color("Complete!"));
                                }
                            };
                            commandDelay.runTaskLater(plugin, 5);

                        }
                    } else {
                        for (int i = 0; i < Integer.parseInt(args[1]); i++) {
                            BukkitRunnable commandDelay = new BukkitRunnable() {
                                @Override
                                public void run() {
                                    Player p = (Player) sender;
                                    p.chat(color(command));
                                    sender.sendMessage(color("Complete!"));
                                }
                            };
                            commandDelay.runTaskLater(plugin, 5);
                        }
                    }
                    return false;
                default:
                    sender.sendMessage(color("&cError: Argument '&4" + args[0] + "&c' unrecognized. Please Use either command(1) or chat(2)!"));
                    return false;


            }


        }
    }
}
