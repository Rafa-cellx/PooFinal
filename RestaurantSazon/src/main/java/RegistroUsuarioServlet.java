package com.main.java;
import java.io.IOException;
import jakarta.servlet.ServletException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/RegistroUsuarioServlet")
public class RegistroUsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Constructor opcional
    public RegistroUsuarioServlet() {
        super();
    }

    // Datos de conexión a Clever Cloud
    private static final String DB_URL = "jdbc:mysql://bytogqoyftf2dlcuaelb-mysql.services.clever-cloud.com:3306/bytogqoyftf2dlcuaelb?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "ufijig3sshb19ywp";
    private static final String DB_PASSWORD = "6eM3Lcinv04fcPya4Ixe";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String celular = request.getParameter("celular");
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "INSERT INTO usuarios (nombre, apellido, celular, correo, contrasena) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, celular);
            ps.setString(4, correo);
            ps.setString(5, contrasena);

            ps.executeUpdate();
            con.close();

            response.sendRedirect("Menu.html");

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h1>Error: " + e.getMessage() + "</h1>");
        }
    }
}