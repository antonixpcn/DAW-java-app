import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/insertar")
public class Insertar extends HttpServlet {
    
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
            out.println("<title>Insertar Nuevo Libro</title>");
            out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css' rel='stylesheet'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container mt-5'>");

            out.println("<h3 class='mb-4'>Insertar un Nuevo Libro</h3>");
            out.println("<form action='insertar' method='post' class='mb-4'>");
            out.println("<div class='mb-3'>");
            out.println("<label for='titol' class='form-label'>Títol</label>");
            out.println("<input type='text' name='titol' id='titol' class='form-control' required>");
            out.println("</div>");
            out.println("<div class='mb-3'>");
            out.println("<label for='isbn' class='form-label'>ISBN</label>");
            out.println("<input type='text' name='isbn' id='isbn' class='form-control' required>");
            out.println("</div>");
            out.println("<div class='mb-3'>");
            out.println("<label for='any_publicacio' class='form-label'>Any Publicació</label>");
            out.println("<input type='number' name='any_publicacio' id='any_publicacio' class='form-control' required>");
            out.println("</div>");
            out.println("<div class='mb-3'>");
            out.println("<label for='id_editorial' class='form-label'>ID Editorial</label>");
            out.println("<input type='number' name='id_editorial' id='id_editorial' class='form-control' required>");
            out.println("</div>");
            out.println("<button type='submit' class='btn btn-primary'>Insertar Libro</button>");
            out.println("</form>");

            // Si hubo una inserción exitosa, mostrar un mensaje
            if (request.getAttribute("mensaje") != null) {
                out.println("<div class='alert alert-info'>" + request.getAttribute("mensaje") + "</div>");
            }

            out.println("</div>"); // Cierre del contenedor
            out.println("</body>");
            out.println("</html>");
        }
    }

    
    /** 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Obtener parámetros del formulario
            String titol = request.getParameter("titol");
            String isbn = request.getParameter("isbn");
            int anyPublicacio = Integer.parseInt(request.getParameter("any_publicacio"));
            int idEditorial = Integer.parseInt(request.getParameter("id_editorial"));

            // Conexión a la base de datos
            Conexio conex = new Conexio();
            Connection conn = conex.getConexio();

            // Consulta SQL para insertar un nuevo libro
            String query = "INSERT INTO llibres (titol, isbn, any_publicacio, id_editorial) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, titol);
            ps.setString(2, isbn);
            ps.setInt(3, anyPublicacio);
            ps.setInt(4, idEditorial);

            int result = ps.executeUpdate(); // Ejecutar la actualización

            // Verificar si la operación fue exitosa
            if (result > 0) {
                request.setAttribute("mensaje", "Libro insertado correctamente");
            } else {
                request.setAttribute("mensaje", "Error al insertar el libro");
            }

            // Redirigir al mismo servlet para mostrar el mensaje
            doGet(request, response);

            // Cerrar recursos
            ps.close();
            conn.close();
        } catch (Exception e) {
            throw new ServletException("Error al insertar el libro", e);
        }
    }
}
