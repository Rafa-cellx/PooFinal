import java.io.IOException;
import jakarta.servlet.ServletException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/registroEmpleado")
public class RegistroEmpleadoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String empleadoId = request.getParameter("empleadoId");
        String contrasena = request.getParameter("contrasena");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
<<<<<<< HEAD
                "jdbc:mysql://localhost:3306/sazon_db", "root", "MXVN#1champion5");
=======
                "jdbc:mysql://localhost:3306/sazon_db", "root", "Madafaker2005");
>>>>>>> branch 'master' of https://github.com/Rafa-cellx/PooFinal.git

            String sql = "INSERT INTO empleados (nombre, apellido, empleado_id, contrasena) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, empleadoId);
            ps.setString(4, contrasena); // en producción, encripta esto

            ps.executeUpdate();
            con.close();

            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h1>Empleado registrado exitosamente</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h1>Error: " + e.getMessage() + "</h1>");
        }
    }
}
