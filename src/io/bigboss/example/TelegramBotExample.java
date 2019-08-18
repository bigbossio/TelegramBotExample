package io.bigboss.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.bigboss.example.vo.TelegramMessage;
import io.bigboss.example.vo.TelegramResult;
import io.bigboss.example.vo.TelegramUpdate;

public class TelegramBotExample {

	private static String TOKEN = "YOUR_TELEGRAM_TOKEN";
	private static int POLLING_TIMEOUT = 30;

	private int lastUpdateId = -1;

	public void run() {
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			while (true) {
				TelegramResult<List<TelegramUpdate>> updates = getUpdates(httpClient);

				if (updates != null && updates.isOk()) {
					List<TelegramUpdate> updateList = updates.getResult();

					if (updateList != null) {
						for (TelegramUpdate update : updateList) {
							sendMessage(httpClient, update);
							lastUpdateId = update.getUpdateId();
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private TelegramResult<List<TelegramUpdate>> getUpdates(CloseableHttpClient httpClient) {
		HttpGet httpGet = new HttpGet("https://api.telegram.org/bot" + TOKEN + "/getUpdates?offset="
				+ (lastUpdateId + 1) + "&timeout=" + POLLING_TIMEOUT);

		try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
			String entityString = EntityUtils.toString(response.getEntity());

			ObjectMapper objectMapper = new ObjectMapper();
			TelegramResult<List<TelegramUpdate>> result = objectMapper.readValue(entityString,
					new TypeReference<TelegramResult<List<TelegramUpdate>>>() {
					});

			System.out.println("<getUpdates> " + result);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void sendMessage(CloseableHttpClient httpClient, TelegramUpdate update) throws IOException {
		String orgMessageText = update.getMessage().getText();

		if ("/start".equals(orgMessageText)) {
			return;
		}

		String newMessageText = new StringBuilder(orgMessageText).reverse().toString();

		Map<String, Object> messageMap = new HashMap<>();
		messageMap.put("chat_id", update.getMessage().getChat().getId());
		messageMap.put("text", newMessageText);

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(messageMap);

		HttpPost httpPost = new HttpPost("https://api.telegram.org/bot" + TOKEN + "/sendMessage");
		HttpEntity stringEntity = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
		httpPost.setEntity(stringEntity);

		try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
			String entityString = EntityUtils.toString(response.getEntity());

			TelegramResult<TelegramMessage> result = objectMapper.readValue(entityString,
					new TypeReference<TelegramResult<TelegramMessage>>() {
					});

			System.out.println("<sendMessage> " + result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new TelegramBotExample().run();
	}

}
