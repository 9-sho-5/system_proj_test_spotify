package api;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/api/add_track")
public class AddToPlaylist extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String trackId = (request.getParameter("trackId") == null) ? "" : (String) request.getParameter("trackId");
        
        JSONObject data = new JSONObject();
        JSONArray urisArray = new JSONArray();
        urisArray.put(trackId);
        data.put("uris", urisArray);
        System.out.println(data);

        Spotify spotify = Spotify.getInstance();
        String json = spotify.addTrack(ENV.getPlaylistId(), data);

		response.setContentType("application/json");
		request.setCharacterEncoding("UTF-8");
        Writer writer = response.getWriter();
		writer.append(json);
		writer.flush();
	}
}