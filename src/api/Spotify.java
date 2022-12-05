package api;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.security.SecureRandom;
import java.util.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.charset.StandardCharsets;

public class Spotify {
	private static Spotify spotify = new Spotify();
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
	private String code;
	private String access_token;

	private Spotify(){
		code = null;
		access_token = null;
	}

    public static Spotify getInstance(){
        return spotify;
    }
    
    public String authorize() throws UnsupportedEncodingException {
        return String.format("%s?client_id=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s", authorizeUrl, clientId, URLEncoder.encode(redirectUri, "utf-8"), String.join(" ", scope), new SecureRandom());
    }
	
	public void crateAccessToken() throws UnirestException, UnsupportedEncodingException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://accounts.spotify.com/api/token"))
			.setHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ':' + clientSecret).getBytes(StandardCharsets.UTF_8)))
			.setHeader("Content-Type", "application/x-www-form-urlencoded")
			.POST(BodyPublishers.ofString(String.format("grant_type=%s&code=%s&redirect_uri=%s", URLEncoder.encode("authorization_code", "UTF-8"), URLEncoder.encode(getCode(), "UTF-8"), URLEncoder.encode(redirectUri, "UTF-8"))))
            .build();
		try {
			// リクエストを送信
			var response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
			// レスポンスボディからaccess_tokenの取得
			JSONObject json = new JSONObject(response.body());
			setAccessToken(json.getString("access_token"));

			// System.out.println("access_token: " + json.getString("access_token"));
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String serch(String keyword) throws UnsupportedEncodingException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(apiEndpoint + String.format("/search?q=%s&type=%s&limit=5", keyword, URLEncoder.encode("track", "utf-8"))))
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
			// System.out.println(json.getJSONObject("tracks"));
			JSONObject tracks = json.getJSONObject("tracks");
			
			
			JSONArray data = tracks.getJSONArray("items");
			System.out.println(data.getJSONObject(0));
			// レスポンス整形
			for(int i = 0; i < data.length(); i++){
				System.out.println("Album Images :" + data.getJSONObject(i).getJSONObject("album").getJSONArray("images"));
				System.out.println("Album Id :" + data.getJSONObject(i).getJSONObject("album").get("id"));
				System.out.println("Artist :" + data.getJSONObject(i).getJSONArray("artists"));
				System.out.println("Name :" + data.getJSONObject(i).get("name"));
				System.out.println("uri :" + data.getJSONObject(i).get("uri"));
			}
			builder = new StringBuilder();
			builder.append("{\"data\":");
			builder.append('[');
			for(int i = 0; i < data.length(); i++){
				builder.append('{');
				builder.append("\"Album_Images\":\"").append(data.getJSONObject(i).getJSONObject("album").getJSONArray("images").getJSONObject(0).get("url")).append("\",");
				builder.append("\"Album_Id\":\"").append(data.getJSONObject(i).getJSONObject("album").get("id")).append("\",");
				builder.append("\"Artist\":\"").append(data.getJSONObject(i).getJSONArray("artists").getJSONObject(0).get("name")).append("\",");
				builder.append("\"Name\":\"").append(data.getJSONObject(i).get("name")).append("\",");
				builder.append("\"uri\":\"").append(data.getJSONObject(i).get("uri")).append("\"");
				builder.append("}");
				if(i != data.length() - 1){
					builder.append(",");
				}
			}
			builder.append(']');
			builder.append('}');

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	public String addTrack(JSONObject uris) throws UnsupportedEncodingException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(apiEndpoint + String.format("/playlists/%s/tracks", ENV.getPlaylistId())))
			.setHeader("Authorization", "Bearer " + getAccessToken())
			.setHeader("Content-Type", "application/json")
			.POST(BodyPublishers.ofString(uris.toString()))
            .build();
			System.out.println(uris.toString());
			System.out.println(URLEncoder.encode(uris.toString(), "UTF-8"));
		
		StringBuilder builder = null;
		try {
			// リクエストを送信
			var response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
			// レスポンスボディからレスポンスの取得
			JSONObject json = new JSONObject(response.body());

			builder = new StringBuilder();
			builder.append('{');
			builder.append("\"snapshot\":").append(json).append("");
			builder.append('}');

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	public void setCode (String code) {
		this.code = code;
	}

	public void setAccessToken(String access_token) {
		this.access_token = access_token;
	}

	public String getAccessToken() {
		return access_token;
	}

	public String getCode() {
		return code;
	}


}