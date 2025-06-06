import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PagoServlet")
public class PagoServlet extends HttpServlet {
    
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
     
        String direccion = request.getParameter("direccion");
        String metodoPago = request.getParameter("metodoPago");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
          //CONEXIÓN CON LA DB LOCAL
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sazon_db,root,MMXVN#1champion5")) {
                conn.setAutoCommit(false); // Iniciar transacción
                
                // 3. Actualizar inventario para cada item del carrito
                // (En un caso real, recibirías los items del carrito)
                actualizarInventario(conn, 1, 2); // Ejemplo: ID 1, cantidad 2
                actualizarInventario(conn, 2, 1); // Ejemplo: ID 2, cantidad 1
                
                // 4. Registrar el pedido en la base de datos
                registrarPedido(conn, direccion, metodoPago, 90.00); // Total fijo de ejemplo
                
                conn.commit(); // Confirmar transacción
                response.getWriter().write("Pedido confirmado con éxito");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                "Error al procesar el pedido: " + e.getMessage());
        }
    }
    
    private void actualizarInventario(Connection conn, int idArticulo, int cantidadVendida) 
            throws SQLException {
        String sql = "UPDATE articulos SET cantidad = cantidad - ? WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cantidadVendida);
            stmt.setInt(2, idArticulo);
            stmt.executeUpdate();
        }
    }
    
    private void registrarPedido(Connection conn, String direccion, String metodoPago, double total) 
            throws SQLException {
        String sql = "INSERT INTO pedidos (fecha, direccion, metodo_pago, total) VALUES (NOW(), ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, direccion);
            stmt.setString(2, metodoPago);
            stmt.setDouble(3, total);
            stmt.executeUpdate();
            
        }
    }
}