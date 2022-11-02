package api;

import java.io.Writer;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Spotify Login Servlet
 */
@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
        
		String auhorizeUri = null;

		Spotify spotify = new Spotify();
		auhorizeUri = spotify.authorize();

		response.setContentType("application/json");
		StringBuilder builder = new StringBuilder();
		builder.append('{');
		builder.append("\"uri\":\"").append(auhorizeUri).append("\"");
		builder.append('}');
		String json = builder.toString();
		System.out.println(json);

		Writer writer = response.getWriter();
		writer.append(json);
		writer.flush();

    }
}
