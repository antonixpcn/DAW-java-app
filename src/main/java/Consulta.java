import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/con")
public class Consulta extends HttpServlet {
    
    /** 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Generar encabezado HTML con Bootstrap
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta http-equiv='X-UA-Compatible' content='IE=edge'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>Llibreria Resultats</title>");
            out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css' rel='stylesheet'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container mt-5'>");
            out.println("<h3 class='mb-4'>Resultats de la Llibreria</h3>");
            out.println("<table class='table table-bordered table-striped'>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>ID</th><th>Títol</th><th>ISBN</th><th>Any Publicació</th><th>ID Editorial</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");

            // Conexión a la base de datos
            Conexio conex = new Conexio();
            Connection conn = conex.getConexio();

            // Consulta a la base de datos
            String query = "SELECT * FROM llibres";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            // Procesar los resultados
            while (rs.next()) {
                int id = rs.getInt("id");
                String titol = rs.getString("titol");
                String isbn = rs.getString("isbn");
                int anyPublicacio = rs.getInt("any_publicacio");
                int idEditorial = rs.getInt("id_editorial");

                // Generar filas de la tabla
                out.println("<tr>");
                out.printf("<td>%d</td><td>%s</td><td>%s</td><td>%d</td><td>%d</td>%n", id, titol, isbn, anyPublicacio, idEditorial);
                out.println("</tr>");
            }

            // Cerrar recursos
            rs.close();
            st.close();

            // Cerrar HTML
            out.println("</tbody>");
            out.println("</table>");
            out.println("<a href='llibreria.jsp' class='btn btn-primary'>Volver al inicio</a>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            throw new ServletException("Error al procesar la consulta", e);
        }
    }
}
