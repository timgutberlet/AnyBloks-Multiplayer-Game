package net.packet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ChatMessagePacket.class, name = "ChatMessage"),
    @JsonSubTypes.Type(value = CreateAccountRequestPacket.class, name = "CreateAccountRequest"),
    @JsonSubTypes.Type(value = CreateAccountResponsePacket.class, name = "CreateResponsePacket"),
    @JsonSubTypes.Type(value = GameUpdatePacket.class, name = "GameUpdatePacket"),
    @JsonSubTypes.Type(value = InitPacket.class, name = "InitPacket"),
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
