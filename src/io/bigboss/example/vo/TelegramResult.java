package io.bigboss.example.vo;

public class TelegramResult<T> {

	private boolean ok;
	private T result;

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "TelegramResult [ok=" + ok + ", result=" + result + "]";
	}

}
