package com.koirdsuzu.bungeeversion;

import com.koirdsuzu.bungeeversion.commands.BungeeVersionCommand;
import com.koirdsuzu.bungeeversion.commands.VersionCommand;
import net.md_5.bungee.api.plugin.Plugin;

public final class BungeeVersion extends Plugin {

    @Override
    public void onEnable() {
        // コマンドを登録
        getProxy().getPluginManager().registerCommand(this, new VersionCommand());
        getProxy().getPluginManager().registerCommand(this, new BungeeVersionCommand(this));
        getLogger().info("BungeeVersionプラグインが有効になりました！");
    }

    @Override
    public void onDisable() {
        getLogger().info("BungeeVersionプラグインが無効になりました！");
    }
}
