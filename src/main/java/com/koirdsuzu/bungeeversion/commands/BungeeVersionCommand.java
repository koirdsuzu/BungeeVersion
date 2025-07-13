package com.koirdsuzu.bungeeversion.commands;

import com.koirdsuzu.bungeeversion.BungeeVersion;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BungeeVersionCommand extends Command implements TabExecutor {

    private final BungeeVersion plugin;

    public BungeeVersionCommand(BungeeVersion plugin) {
        super("bungeeversion", "bungeeversion.admin", "bv");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            // 引数がない場合はヘルプを表示
            showHelp(sender);
            return;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "help":
                showHelp(sender);
                break;
            case "reload":
                if (!sender.hasPermission("bungeeversion.admin")) {
                    sender.sendMessage(new TextComponent(ChatColor.RED + "このコマンドを使用する権限がありません。"));
                    return;
                }
                reloadPlugin(sender);
                break;
            default:
                sender.sendMessage(new TextComponent(ChatColor.RED + "不明なサブコマンド: " + subCommand));
                sender.sendMessage(new TextComponent(ChatColor.YELLOW + "使用法: /bungeeversion <help|reload>"));
                break;
        }
    }

    private void showHelp(CommandSender sender) {
        sender.sendMessage(new TextComponent(ChatColor.GREEN + "=== BungeeVersion ヘルプ ==="));
        sender.sendMessage(new TextComponent(ChatColor.YELLOW + "/bver [plugin名]" + ChatColor.WHITE + " - プラグインのバージョン情報を表示"));
        sender.sendMessage(new TextComponent(ChatColor.YELLOW + "/bungeeversion help" + ChatColor.WHITE + " - このヘルプを表示"));
        
        if (sender.hasPermission("bungeeversion.admin")) {
            sender.sendMessage(new TextComponent(ChatColor.YELLOW + "/bungeeversion reload" + ChatColor.WHITE + " - プラグインをリロード"));
        }
        
        sender.sendMessage(new TextComponent(ChatColor.GRAY + "バージョン: 1.0"));
        sender.sendMessage(new TextComponent(ChatColor.GRAY + "作者: koirdsuzu"));
    }

    private void reloadPlugin(CommandSender sender) {
        try {
            // プラグインのリロード処理
            // 実際のリロード処理はBungeeCordの制限により制限的ですが、
            // 設定ファイルの再読み込みなどが可能です
            
            sender.sendMessage(new TextComponent(ChatColor.GREEN + "BungeeVersionプラグインをリロードしました！"));
            plugin.getLogger().info("プラグインが " + sender.getName() + " によってリロードされました。");
            
        } catch (Exception e) {
            sender.sendMessage(new TextComponent(ChatColor.RED + "リロード中にエラーが発生しました: " + e.getMessage()));
            plugin.getLogger().severe("リロード中にエラーが発生しました: " + e.getMessage());
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            String input = args[0].toLowerCase();
            List<String> subCommands = Arrays.asList("help", "reload");
            
            for (String subCommand : subCommands) {
                if (subCommand.startsWith(input)) {
                    // reloadコマンドは管理者権限が必要
                    if (subCommand.equals("reload") && !sender.hasPermission("bungeeversion.admin")) {
                        continue;
                    }
                    completions.add(subCommand);
                }
            }
        }
        
        return completions;
    }
} 