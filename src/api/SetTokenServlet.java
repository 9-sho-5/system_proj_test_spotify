package api;

import java.io.Writer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Spotify SetToken Servlet
 */
@WebServlet("/api/setToken")
public class SetTokenServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        String code = (request.getParameter("token") == null) ? ""
        : (String) request.getParameter("token");

		Spotify spotify = Spotify.getInstance();
		spotify.setCode(code);

		StringBuilder builder = new StringBuilder();
		builder.append('{');
		builder.append("\"status\":\"").append(code).append("\"");
		builder.append('}');
		String json = builder.toString();
		System.out.println(json);

		Writer writer = response.getWriter();
		writer.append(json);
		writer.flush();
		
	}
}
