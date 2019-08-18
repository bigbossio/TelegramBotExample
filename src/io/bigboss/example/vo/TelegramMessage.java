package io.bigboss.example.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TelegramMessage {

	@JsonProperty("message_id")
	private int messageId;

	private TelegramChat chat;

	private String text;

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public TelegramChat getChat() {
		return chat;
	}

	public void setChat(TelegramChat chat) {
		this.chat = chat;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "TelegramMessage [messageId=" + messageId + ", chat=" + chat + ", text=" + text + "]";
	}

}
