import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/sazon_db", "root", "100394");
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT * FROM pedidos";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                JSONObject pedido = new JSONObject();
                pedido.put("id", rs.getInt("id"));
                pedido.put("cliente", rs.getString("cliente"));
                pedido.put("direccion", rs.getString("direccion"));
                pedido.put("metodoPago", rs.getString("metodo_pago"));
                pedido.put("total", rs.getDouble("total"));
                pedido.put("fecha", rs.getDate("fecha").toString());

                pedidosArray.put(pedido);
            }

            out.print(pedidosArray.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"mensaje\": \"Error al obtener pedidos\"}");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        StringBuilder sb = new StringBuilder();
        String line;

        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al obtener los pedidos: " + e.getMessage());
        }

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            JSONObject pedidoJson = new JSONObject(sb.toString());

            String cliente = pedidoJson.getString("cliente");
            String direccion = pedidoJson.getString("direccion");
            String metodoPago = pedidoJson.getString("metodoPago");
            double total = pedidoJson.getDouble("total");
            String fecha = pedidoJson.getString("fecha");

            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sazon_db", "root", "100394");
                 PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO pedidos(cliente, direccion, metodo_pago, total, fecha) VALUES (?, ?, ?, ?, ?)")) {

                ps.setString(1, cliente);
                ps.setString(2, direccion);
                ps.setString(3, metodoPago);
                ps.setDouble(4, total);
                ps.setDate(5, java.sql.Date.valueOf(fecha));

                int filas = ps.executeUpdate();

                if (filas > 0) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.write("{\"mensaje\": \"Pedido guardado exitosamente\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.write("{\"mensaje\": \"Error al guardar pedido\"}");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"mensaje\": \"JSON inválido o error\"}");
        }
    }
}