package com.github.ringoame196_s_mcPlugin.packetListener

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.ListenerPriority
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import com.comphenix.protocol.wrappers.EnumWrappers
import com.github.ringoame196_s_mcPlugin.Data
import org.bukkit.plugin.Plugin

class PlayerInfoListener(plugin: Plugin) : PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.PLAYER_INFO) {
    override fun onPacketSending(e: PacketEvent?) {
        if (!Data.isActive) return

        val packet = e?.packet ?: return // パケット
        val sender = e.player // 送る相手

        // ADD_PLAYERの場合は処理を止める
        if (packet.handle.toString().contains("ADD_PLAYER")) return
        if (packet.playerInfoDataLists.size() < 1) return
        val playerInfoData = packet.playerInfoDataLists.read(1)

        // 複数プレイヤーに対応
        for (info in playerInfoData) {
            val gameMode = info.gameMode
            val playerUUID = info.profile.id

            // スペク 本人以外には パケットを飛ばさない
            if (gameMode == EnumWrappers.NativeGameMode.SPECTATOR && playerUUID != sender.uniqueId.toString()) {
                e.isCancelled = true
            }
        }
    }
}
