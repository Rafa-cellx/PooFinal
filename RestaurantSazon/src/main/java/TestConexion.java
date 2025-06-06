import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConexion {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/sazon_db";
        String usuario = "root";
        String contrasena = "MXVN#1champion5";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(url, usuario, contrasena);
            System.out.println("✅ Conexión exitosa a la base de datos.");
            conexion.close();
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Error: No se encontró el driver JDBC.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Error de conexión con la base de datos.");
            e.printStackTrace();
        }
    }
}