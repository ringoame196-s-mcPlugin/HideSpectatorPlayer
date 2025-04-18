package com.github.ringoame196_s_mcPlugin.commands

import com.github.ringoame196_s_mcPlugin.Data
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class Command : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) return false
        val active = args[0]

        when (active) {
            CommandConst.ON_ACTIVE -> setActive(sender, true)
            CommandConst.OFF_ACTIVE -> setActive(sender, false)
            else -> return false
        }
        return true
    }

    private fun setActive(sender: CommandSender, active: Boolean) {
        Data.isActive = active
        correctState()
        sendMessage(sender, active)
    }

    private fun sendMessage(sender: CommandSender, active: Boolean) {
        val senderName = sender.name
        val message = if (active)
            "${ChatColor.GREEN}${senderName}が、スペクテイターの隠匿モードを有効にしました。"
        else
            "${ChatColor.RED}${senderName}が、スペクテイターの隠匿モードを無効にしました。"
        sendMessageToOps(message)
    }

    private fun sendMessageToOps(message: String) {
        Bukkit.getLogger().info(message)

        for (player in Bukkit.getOnlinePlayers()) {
            if (!player.isOp) continue
            player.sendMessage(message)
        }
    }

    private fun correctState() {
        for (player in Bukkit.getOnlinePlayers()) {
            // スペクテイターのプレイヤーのみ
            if (player.gameMode != GameMode.SPECTATOR) continue
            // ゲームモードを切り替えることで 正しい状況にする
            player.gameMode = GameMode.ADVENTURE
            player.gameMode = GameMode.SPECTATOR
        }
    }

    override fun onTabComplete(commandSender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return when (args.size) {
            1 -> mutableListOf(CommandConst.ON_ACTIVE, CommandConst.OFF_ACTIVE)
            else -> mutableListOf()
        }
    }
}
