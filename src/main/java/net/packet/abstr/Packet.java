package net.packet.abstr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import net.packet.game.GameUpdatePacket;
import net.packet.game.InitSessionPacket;
import net.packet.game.PlayerOrderPacket;
import net.packet.account.CreateAccountRequestPacket;
import net.packet.account.CreateAccountResponsePacket;
import net.packet.account.LoginRequestPacket;
import net.packet.account.LoginResponsePacket;
import net.packet.account.UpdateAccountRequestPacket;
import net.packet.chat.ChatMessagePacket;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
		@JsonSubTypes.Type(value = ChatMessagePacket.class, name = "ChatMessage"),
		@JsonSubTypes.Type(value = CreateAccountRequestPacket.class, name = "CreateAccountRequest"),
		@JsonSubTypes.Type(value = CreateAccountResponsePacket.class, name = "CreateResponsePacket"),
		@JsonSubTypes.Type(value = GameUpdatePacket.class, name = "GameUpdatePacket"),
		@JsonSubTypes.Type(value = InitSessionPacket.class, name = "InitPacket"),
		@JsonSubTypes.Type(value = LoginRequestPacket.class, name = "LoginRequestPacket"),
		@JsonSubTypes.Type(value = LoginResponsePacket.class, name = "LoginResponsePacket"),
		@JsonSubTypes.Type(value = PlayerOrderPacket.class, name = "PlayerOrderPacket"),

		@JsonSubTypes.Type(value = UpdateAccountRequestPacket.class, name = "UpdateAccountRequestPacket")}
)

/**
 * Parent class for Packets
 *
 * @author tbuscher
 */
public abstract class Packet {


}
