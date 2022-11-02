package api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Spotify {

    private final String clientId = ENV.getClinetId();
	private final String redirectUri = ENV.getRedirectUri();
	private final String authorizeUrl = ENV.getAuthorizeUrl();
	private final String[] scope = {
		"playlist-read-private",
		"user-read-email",
		"user-read-private",
		"user-modify-playback-state",
		"user-read-playback-state",
		"user-read-currently-playing",
		"user-read-recently-played",
		"user-read-playback-position",
		"user-top-read",
	};
    
    public String authorize() throws UnsupportedEncodingException {
        return String.format("%s?client_id=%s&redirect_uri=%s&response_type=code&scope=%s", authorizeUrl, clientId, URLEncoder.encode(redirectUri, "utf-8"), String.join(" ", scope));
    }

}
