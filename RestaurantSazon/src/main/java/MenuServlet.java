import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Menu")
public class MenuServlet extends HttpServlet {

    // Datos de conexión a Clever Cloud
    private static final String DB_URL = "jdbc:mysql://bytogqoyftf2dlcuaelb-mysql.services.clever-cloud.com:3306/bytogqoyftf2dlcuaelb?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "ufijig3sshb19ywp";
    private static final String DB_PASSWORD = "6eM3Lcinv04fcPya4Ixe";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        List<Articulo> articulos = obtenerArticulosDesdeDB();

        if (articulos != null) {
            request.setAttribute("articulos", articulos);
            request.getRequestDispatcher("/Menu.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Error al obtener artículos de la base de datos");
        }
    }

    private List<Articulo> obtenerArticulosDesdeDB() {
        List<Articulo> articulos = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM articulos")) {

                while (rs.next()) {
                    Articulo art = new Articulo();
                    art.setId(rs.getInt("id"));
                    art.setNombre(rs.getString("nombre"));
                    art.setPrecio(rs.getDouble("precio"));
                    art.setCantidad(rs.getInt("cantidad"));

                    articulos.add(art);
                }
            }
            return articulos;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}


class Articulo {
    private int id;
    private String nombre;
    private double precio;
    private int cantidad;

    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    
  
}