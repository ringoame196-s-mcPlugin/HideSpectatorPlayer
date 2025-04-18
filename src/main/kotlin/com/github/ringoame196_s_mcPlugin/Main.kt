package com.github.ringoame196_s_mcPlugin

import com.comphenix.protocol.ProtocolLibrary
import com.github.ringoame196_s_mcPlugin.commands.Command
import com.github.ringoame196_s_mcPlugin.packetListener.PlayerInfoListener
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    private val plugin = this
    override fun onEnable() {
        super.onEnable()

        // コマンド
        val command = getCommand("hidespec")
        command!!.setExecutor(Command())

        // パケット
        val protocolLibraryManager = ProtocolLibrary.getProtocolManager()
        protocolLibraryManager.addPacketListener(PlayerInfoListener(plugin))
    }
}
