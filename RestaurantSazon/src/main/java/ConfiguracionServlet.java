import java.io.IOException;
import jakarta.servlet.ServletException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.sql.SQLException;

@WebServlet(name = "ConfiguracionServlet", urlPatterns = {"/config"})
public class ConfiguracionServlet extends HttpServlet {
    
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        

    	try (Connection conn = DriverManager.getConnection(
    	        "jdbc:mysql://bytogqoyftf2dlcuaelb-mysql.services.clever-cloud.com:3306/bytogqoyftf2dlcuaelb?useSSL=false&serverTimezone=UTC",
    	        "ufijig3sshb19ywp",
    	        "6eM3Lcinv04fcPya4Ixe")) {

    	    response.getWriter().println("¡Conexión a la DB exitosa!");
    	} catch (SQLException e) {
    	    response.getWriter().println("Error de conexión: " + e.getMessage());
    	}


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

    }
}