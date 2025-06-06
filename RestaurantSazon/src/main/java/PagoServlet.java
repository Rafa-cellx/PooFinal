import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PagoServlet")
public class PagoServlet extends HttpServlet {

    // Datos de conexión a Clever Cloud
    private static final String DB_URL = "jdbc:mysql://bytogqoyftf2dlcuaelb-mysql.services.clever-cloud.com:3306/bytogqoyftf2dlcuaelb?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "ufijig3sshb19ywp";
    private static final String DB_PASSWORD = "6eM3Lcinv04fcPya4Ixe";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String direccion = request.getParameter("direccion");
        String metodoPago = request.getParameter("metodoPago");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                conn.setAutoCommit(false); // Iniciar transacción

                // Actualizar inventario (ejemplos)
                actualizarInventario(conn, 1, 2);
                actualizarInventario(conn, 2, 1);

                // Registrar el pedido (ejemplo)
                registrarPedido(conn, direccion, metodoPago, 90.00);

                conn.commit(); // Confirmar cambios
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