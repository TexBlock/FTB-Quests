package dev.ftb.mods.ftbquests.net;

import dev.ftb.mods.ftbquests.FTBQuests;
import dev.ftb.mods.ftbquests.quest.ChangeProgress;
import me.shedaniel.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;

/**
 * @author LatvianModder
 */
public class MessageChangeProgressResponse extends MessageBase {
	private final UUID team;
	private final UUID player;
	private final long id;
	private final ChangeProgress type;
	private final boolean notifications;

	MessageChangeProgressResponse(FriendlyByteBuf buffer) {
		team = buffer.readUUID();
		player = buffer.readUUID();
		id = buffer.readLong();
		type = ChangeProgress.NAME_MAP.read(buffer);
		notifications = buffer.readBoolean();
	}

	public MessageChangeProgressResponse(UUID t, UUID p, long i, ChangeProgress ty, boolean n) {
		team = t;
		player = p;
		id = i;
		type = ty;
		notifications = n;
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		buffer.writeUUID(team);
		buffer.writeUUID(player);
		buffer.writeLong(id);
		ChangeProgress.NAME_MAP.write(buffer, type);
		buffer.writeBoolean(notifications);
	}

	@Override
	public void handle(NetworkManager.PacketContext context) {
		FTBQuests.NET_PROXY.changeProgress(team, player, id, type, notifications);
	}
}