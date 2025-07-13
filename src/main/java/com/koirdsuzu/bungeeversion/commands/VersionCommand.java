package com.koirdsuzu.bungeeversion.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.md_5.bungee.api.ProxyServer;

import java.util.ArrayList;
import java.util.List;

public class VersionCommand extends Command implements TabExecutor {

    public VersionCommand() {
        super("bver", "bungeeversion.version", "bungeever");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            // 引数がない場合は、実際のプロキシ情報を表示
            showProxyInfo(sender);
            return;
        }

        String pluginName = args[0];
        // BungeeCordのプロキシインスタンスからプラグインマネージャーを取得
        PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
        Plugin targetPlugin = null;
        
        // プラグインを検索
        for (Plugin plugin : pluginManager.getPlugins()) {
            if (plugin.getDescription().getName().equalsIgnoreCase(pluginName)) {
                targetPlugin = plugin;
                break;
            }
        }

        if (targetPlugin == null) {
            // プラグインが見つからない場合
            sender.sendMessage(new TextComponent(ChatColor.RED + "プラグイン '" + pluginName + "' が見つかりません。"));
            return;
        }

        // プラグイン情報を表示
        sender.sendMessage(new TextComponent(ChatColor.GREEN + targetPlugin.getDescription().getName() + 
                                           ChatColor.WHITE + " version " + 
                                           ChatColor.GREEN + targetPlugin.getDescription().getVersion()));
        
        if (targetPlugin.getDescription().getDescription() != null) {
            sender.sendMessage(new TextComponent(ChatColor.WHITE + targetPlugin.getDescription().getDescription()));
        }
        
        if (targetPlugin.getDescription().getAuthor() != null) {
            sender.sendMessage(new TextComponent(ChatColor.WHITE + "Author: " + 
                                               ChatColor.GREEN + targetPlugin.getDescription().getAuthor()));
        }
    }

    private void showProxyInfo(CommandSender sender) {
        // プロキシの実際の情報を取得
        String proxyName = getProxyName();
        String proxyVersion = getProxyVersion();
        String proxyAuthor = getProxyAuthor();
        String proxyDescription = getProxyDescription();
        
        sender.sendMessage(new TextComponent(ChatColor.GREEN + "=== " + proxyName + " ==="));
        sender.sendMessage(new TextComponent(ChatColor.YELLOW + "バージョン: " + ChatColor.WHITE + proxyVersion));
        sender.sendMessage(new TextComponent(ChatColor.YELLOW + "作者: " + ChatColor.WHITE + proxyAuthor));
        sender.sendMessage(new TextComponent(ChatColor.YELLOW + "説明: " + ChatColor.WHITE + proxyDescription));
    }

    private String getProxyName() {
        String serverName = ProxyServer.getInstance().getName();
        String serverVersion = ProxyServer.getInstance().getVersion();
        
        // サーバー名とバージョンからプロキシの種類を判定
        if (serverName.toLowerCase().contains("flamecord") || serverVersion.toLowerCase().contains("flamecord")) {
            return "FlameCord";
        } else if (serverName.toLowerCase().contains("waterfall") || serverVersion.toLowerCase().contains("waterfall")) {
            return "Waterfall";
        } else if (serverName.toLowerCase().contains("travertine") || serverVersion.toLowerCase().contains("travertine")) {
            return "Travertine";
        } else if (serverName.toLowerCase().contains("velocity") || serverVersion.toLowerCase().contains("velocity")) {
            return "Velocity";
        } else if (serverName.toLowerCase().contains("bungeecord") || serverVersion.toLowerCase().contains("bungeecord")) {
            return "BungeeCord";
        } else {
            return serverName;
        }
    }

    private String getProxyVersion() {
        return ProxyServer.getInstance().getVersion();
    }

    private String getProxyAuthor() {
        String proxyName = getProxyName();
        switch (proxyName) {
            case "FlameCord":
                return "FlameCord Team";
            case "Waterfall":
                return "Waterfall Team";
            case "Travertine":
                return "Paper Team";
            case "Velocity":
                return "Velocity Team";
            case "BungeeCord":
                return "BungeeCord Team";
            default:
                return "Unknown";
        }
    }

    private String getProxyDescription() {
        String proxyName = getProxyName();
        switch (proxyName) {
            case "FlameCord":
                return "A fork of BungeeCord with additional features and optimizations.";
            case "Waterfall":
                return "A fork of BungeeCord that adds features and optimizations.";
            case "Travertine":
                return "A fork of BungeeCord that adds 1.7.x support.";
            case "Velocity":
                return "A modern, high-performance proxy for Minecraft servers.";
            case "BungeeCord":
                return "BungeeCord is a proxy server for Minecraft servers.";
            default:
                return "A Minecraft proxy server.";
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            // プラグイン名のTabComplete
            PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
            String input = args[0].toLowerCase();
            
            for (Plugin plugin : pluginManager.getPlugins()) {
                String pluginName = plugin.getDescription().getName();
                if (pluginName.toLowerCase().startsWith(input)) {
                    completions.add(pluginName);
                }
            }
        }
        
        return completions;
    }
} 