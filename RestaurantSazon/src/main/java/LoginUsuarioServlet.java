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

@WebServlet("/LoginUsuarioServlet")
public class LoginUsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String contrasena = request.getParameter("contrasena");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://bytogqoyftf2dlcuaelb-mysql.services.clever-cloud.com:3306/bytogqoyftf2dlcuaelb",
                "ufijig3sshb19ywp",
                "6eM3Lcinv04fcPya4Ixe"
            );
        

            String sql = "SELECT * FROM usuarios WHERE nombre = ? AND contrasena = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, contrasena);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession sesion = request.getSession();
                sesion.setAttribute("usuario", nombre);

                response.sendRedirect("Menu.html");
            } else {
                response.sendRedirect("LoginUsuario.html?error=invalid");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h1>Error: " + e.getMessage() + "</h1>");
        }
        
        if (loginValido) {
            response.sendRedirect("menu-empleados.html");
        }

    }
}
