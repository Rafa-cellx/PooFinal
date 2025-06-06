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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONArray pedidosArray = new JSONArray();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/sazon_db", "root", "fer12320");

            String sql = "SELECT * FROM pedidos";  // Ajusta según tu tabla real
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
        }

        out.print(pedidosArray.toString());
    }
}
