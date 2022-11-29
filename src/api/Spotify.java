package api;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.net.QCodec;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Spotify {

    private final String clientId = ENV.getClinetId();
	private final String clientSecret = ENV.getClientSecret();
	private final String redirectUri = ENV.getRedirectUri();
	private final String authorizeUrl = ENV.getAuthorizeUrl();
	private final String apiEndpoint = ENV.getApiEndpoint();
	private final String[] scope = {
		"playlist-read-private",
		"playlist-modify-private",
		"playlist-read-collaborative",
		"playlist-modify-public",
		"user-read-email",
		"user-read-private",
		"user-modify-playback-state",
		"user-read-playback-state",
		"user-read-currently-playing",
		"user-read-recently-played",
		"user-read-playback-position",
		"user-top-read",
	};
	private String code = null;
	private String access_token = null;
    
    public String authorize() throws UnsupportedEncodingException {
        return String.format("%s?client_id=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s", authorizeUrl, clientId, URLEncoder.encode(redirectUri, "utf-8"), String.join(" ", scope), new SecureRandom());
    }
	
	public void crateAccessToken() throws UnirestException, UnsupportedEncodingException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://accounts.spotify.com/api/token"))
			.setHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ':' + clientSecret).getBytes(StandardCharsets.UTF_8)))
			.setHeader("Content-Type", "application/x-www-form-urlencoded")
			.POST(BodyPublishers.ofString(String.format("grant_type=%s&code=%s&redirect_uri=%s", URLEncoder.encode("authorization_code", "UTF-8"), URLEncoder.encode(code, "UTF-8"), URLEncoder.encode(redirectUri, "UTF-8"))))
            .build();
		try {
			// リクエストを送信
			var response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
			// レスポンスボディからaccess_tokenの取得
			JSONObject json = new JSONObject(response.body());
			access_token = json.getString("access_token");

			// System.out.println("access_token: " + json.getString("access_token"));
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String serch(String keyword) throws UnsupportedEncodingException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(apiEndpoint + String.format("/search?q=%s&type=%s&limit=2", keyword, URLEncoder.encode("track", "utf-8"))))
			.setHeader("Authorization", "Bearer " + getAccessToken())
			.setHeader("Content-Type", "application/json")
			.GET()
            .build();

		StringBuilder builder = null;
		try {
			// リクエストを送信
			var response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
			// レスポンスボディからaccess_tokenの取得
			JSONObject json = new JSONObject(response.body());

			JSONArray data = json.getJSONObject("tracks").getJSONArray("items");
			// レスポンス整形
			for(int i = 0; i < data.length(); i++){
				System.out.println("Album Images :" + data.getJSONObject(i).getJSONObject("album").getJSONArray("images"));
				System.out.println("Album Id :" + data.getJSONObject(i).getJSONObject("album").get("id"));
				System.out.println("Artist :" + data.getJSONObject(i).getJSONArray("artists"));
				System.out.println("Name :" + data.getJSONObject(i).get("name"));
			}
			builder = new StringBuilder();
			builder.append('{');
			for(int i = 0; i < data.length(); i++){
				builder.append("\"Album_Images\":\"").append(data.getJSONObject(i).getJSONObject("album").getJSONArray("images")).append("\"");
				builder.append("\"Album_Id\":\"").append(data.getJSONObject(i).getJSONObject("album").get("id")).append("\"");
				builder.append("\"Artist\":\"").append(data.getJSONObject(i).getJSONArray("artists")).append("\"");
				builder.append("\"Name\":\"").append(data.getJSONObject(i).get("name")).append("\"");
			}
			builder.append('}');

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	public void setCode (String code) {
		this.code = code;
	}

	public void setToken(String access_token) {
		this.access_token = access_token;
	}

	public String getAccessToken() {
		return access_token;
	}

	public String getCode() {
		return code;
	}


}