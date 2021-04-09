package nl.Steffion.BlockHunt.Commands;

import nl.Steffion.BlockHunt.Arena;
import nl.Steffion.BlockHunt.Arena.ArenaState;
import nl.Steffion.BlockHunt.BlockHunt;
import nl.Steffion.BlockHunt.ConfigC;
import nl.Steffion.BlockHunt.Managers.MessageManager;
import nl.Steffion.BlockHunt.MemoryStorage;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CMDstart extends DefaultCMD {

    @Override
    public boolean execute(Player player, Command cmd, String label, String[] args) {
        if (player != null) {
            if (args.length <= 1) {
                MessageManager.sendFMessage(player, ConfigC.error_notEnoughArguments, "syntax-" + BlockHunt.CMDstart.usage);
            } else {
                Arena arena = null;
                for (Arena arena2 : MemoryStorage.arenaList) {
                    if (arena2.arenaName.equalsIgnoreCase(args[1])) {
                        arena = arena2;
                    }
                }

                if (arena != null) {
                    if (arena.gameState.equals(ArenaState.WAITING)) {
                        if (arena.playersInArena.size() >= 2) {
                            arena.timer = 11;
                            arena.gameState = ArenaState.STARTING;
                            MessageManager.sendFMessage(player, ConfigC.normal_startForced, "arenaname-" + arena.arenaName);
                        } else {
                            MessageManager.sendFMessage(player, ConfigC.warning_lobbyNeedAtleast, "1-2");
                        }
                    } else if (arena.gameState.equals(ArenaState.STARTING)) {
                        if (arena.playersInArena.size() < arena.maxPlayers) {
                            if (arena.timer >= 10) {
                                arena.timer = 11;
                            }
                        } else {
                            arena.timer = 1;
                        }

                        MessageManager.sendFMessage(player, ConfigC.normal_startForced, "arenaname-" + arena.arenaName);
                    }
                } else {
                    MessageManager.sendFMessage(player, ConfigC.error_noArena, "name-" + args[1]);
                }
            }
        } else {
            MessageManager.sendFMessage(player, ConfigC.error_onlyIngame);
        }
        return true;
    }
}
