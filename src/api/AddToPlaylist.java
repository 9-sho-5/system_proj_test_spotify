package api;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/api/add_track")
public class AddToPlaylist extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String trackId = (request.getParameter("trackId") == null) ? "" : (String) request.getParameter("trackId");
        String track_name = (request.getParameter("track_name") == null) ? "" : (String) request.getParameter("track_name");
        String artist_name = (request.getParameter("artist_name") == null) ? "" : (String) request.getParameter("artist_name");
        String album_name = (request.getParameter("album_name") == null) ? "" : (String) request.getParameter("album_name");
        String album_image_url = (request.getParameter("album_image_url") == null) ? "" : (String) request.getParameter("album_image_url");
        
        JSONObject data = new JSONObject();
        JSONArray urisArray = new JSONArray();
        urisArray.put(trackId);
        data.put("uris", urisArray);
        System.out.println(data);

        Spotify spotify = Spotify.getInstance();
        String json = spotify.addTrack(data);

		CreateTable.createTable();
		CreateTable.insertData("track_name", "artist_name", "album_name", "album_image_url");

		response.setContentType("application/json");
		request.setCharacterEncoding("UTF-8");
        Writer writer = response.getWriter();
		writer.append(json);
		writer.flush();
	}
}