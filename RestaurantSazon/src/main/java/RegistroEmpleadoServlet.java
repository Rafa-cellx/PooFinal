import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegistroUsuarioServlet")
public class RegistroUsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String celular = request.getParameter("celular");
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
<<<<<<< HEAD

            		"jdbc:mysql://123.45.67.89:3306/sazon_db", "root", "MXVN#1champion5");
=======
                "jdbc:mysql://localhost:3306/sazon_db", "root", "Madafaker2005");
>>>>>>> branch 'master' of https://github.com/Rafa-cellx/PooFinal.git

            String sql = "INSERT INTO usuarios (nombre, apellido, celular, correo, contrasena) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, celular);
            ps.setString(4, correo);
            ps.setString(5, contrasena);

            ps.executeUpdate();
            con.close();

            response.sendRedirect("LoginView.html");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h1>Error: " + e.getMessage() + "</h1>");
        }
    }
}