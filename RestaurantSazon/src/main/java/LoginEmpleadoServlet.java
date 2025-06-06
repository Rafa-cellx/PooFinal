import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginEmpleadoServlet")
public class LoginEmpleadoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String empleado_id = request.getParameter("empleado_id");
        String contrasena = request.getParameter("contrasena");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://bytogqoyftf2dlcuaelb-mysql.services.clever-cloud.com:3306/bytogqoyftf2dlcuaelb",
                "ufijig3sshb19ywp",
                "6eM3Lcinv04fcPya4Ixe"
            );

            String sql = "SELECT nombre, apellido FROM empleados WHERE empleado_id = ? AND contrasena = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, empleado_id);
            ps.setString(2, contrasena);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nombreCompleto = rs.getString("nombre") + " " + rs.getString("apellido");

                HttpSession sesion = request.getSession();
                sesion.setAttribute("usuario", nombreCompleto);
                sesion.setAttribute("empleado_id", empleado_id); 

                response.sendRedirect("menu-empleados.html");
            } else {
                response.sendRedirect("LoginEmpleado.html?error=invalid");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html");
            response.getWriter().println("<h1>Error: " + e.getMessage() + "</h1>");
        }
    }
}
