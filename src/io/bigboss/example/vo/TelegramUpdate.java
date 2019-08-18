package io.bigboss.example.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TelegramUpdate {

	@JsonProperty("update_id")
	private int updateId;
	
	private TelegramMessage message;

	public int getUpdateId() {
		return updateId;
	}

	public void setUpdateId(int updateId) {
		this.updateId = updateId;
	}

	public TelegramMessage getMessage() {
		return message;
	}

	public void setMessage(TelegramMessage message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "TelegramUpdate [updateId=" + updateId + ", message=" + message + "]";
	}

}
