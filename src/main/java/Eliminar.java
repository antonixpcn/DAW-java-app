import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
@WebServlet("/eliminar")
public class Eliminar extends HttpServlet {
    
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
            out.println("<title>Eliminar Libro</title>");
            out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css' rel='stylesheet'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container mt-5'>");

            out.println("<h3 class='mb-4'>Eliminar un Libro</h3>");
            out.println("<form action='eliminar' method='post' class='mb-4'>");
            out.println("<div class='mb-3'>");
            out.println("<label for='id' class='form-label'>ID del Libro</label>");
            out.println("<input type='number' name='id' id='id' class='form-control' required>");
            out.println("</div>");
            out.println("<button type='submit' class='btn btn-danger'>Eliminar Libro</button>");
            out.println("</form>");

            // Mostrar mensaje si existe
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
            // Obtener el ID del libro a eliminar
            int id = Integer.parseInt(request.getParameter("id"));

            // Conexión a la base de datos
            Conexio conex = new Conexio();
            Connection conn = conex.getConexio();

            // Iniciar transacción
            conn.setAutoCommit(false);

            try {
                // Eliminar relaciones en la tabla llibre_autor
                String deleteAutoresQuery = "DELETE FROM llibre_autor WHERE id_llibre = ?";
                try (PreparedStatement psAutores = conn.prepareStatement(deleteAutoresQuery)) {
                    psAutores.setInt(1, id);
                    psAutores.executeUpdate();
                }

                // Eliminar relaciones en la tabla llibre_genere
                String deleteGeneresQuery = "DELETE FROM llibre_genere WHERE id_llibre = ?";
                try (PreparedStatement psGeneres = conn.prepareStatement(deleteGeneresQuery)) {
                    psGeneres.setInt(1, id);
                    psGeneres.executeUpdate();
                }

                // Eliminar el libro en la tabla llibres
                String deleteLibroQuery = "DELETE FROM llibres WHERE id = ?";
                try (PreparedStatement psLibro = conn.prepareStatement(deleteLibroQuery)) {
                    psLibro.setInt(1, id);
                    int result = psLibro.executeUpdate();

                    if (result > 0) {
                        request.setAttribute("mensaje", "Libro eliminado correctamente");
                    } else {
                        request.setAttribute("mensaje", "Error: No se encontró un libro con el ID proporcionado");
                    }
                }

                // Confirmar transacción
                conn.commit();
            } catch (Exception ex) {
                // Revertir transacción en caso de error
                conn.rollback();
                throw new ServletException("Error al eliminar el libro y sus relaciones", ex);
            } finally {
                conn.setAutoCommit(true);
                conn.close();
            }

            // Redirigir al mismo servlet para mostrar el mensaje
            doGet(request, response);
        } catch (Exception e) {
            throw new ServletException("Error al eliminar el libro", e);
        }
    }
}

