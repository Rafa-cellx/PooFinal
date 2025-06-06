import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/PedidosServlet")
public class PedidosServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://bytogqoyftf2dlcuaelb-mysql.services.clever-cloud.com:3306/bytogqoyftf2dlcuaelb?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "ufijig3sshb19ywp";
    private static final String DB_PASSWORD = "6eM3Lcinv04fcPya4Ixe";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONArray pedidosArray = new JSONArray();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT * FROM pedidos";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                JSONObject pedido = new JSONObject();
                pedido.put("id", rs.getInt("id_pedido"));
                pedido.put("cliente", rs.getString("cliente"));
                pedido.put("direccion", rs.getString("direccion"));
                pedido.put("metodoPago", rs.getString("metodo_pago"));
                pedido.put("total", rs.getDouble("total"));
                pedido.put("fecha", rs.getDate("fecha").toString());

                pedidosArray.put(pedido);
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al obtener los pedidos: " + e.getMessage());
        }

        out.print(pedidosArray.toString());
    }
}