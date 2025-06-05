import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class LoginEmpleado extends Application {
    private TextField idField;
    private PasswordField passwordField;
    private Label errorUsuario;
    private Label errorPassword;

    private final String connectionString = "jdbc:sqlserver://localhost:1433;databaseName=GestionRestaurante;integratedSecurity=true;encrypt=true;trustServerCertificate=true";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inicio de Sesión - Empleado");

        idField = new TextField();
        idField.setPromptText("ID de empleado");

        passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");

        errorUsuario = new Label();
        errorPassword = new Label();
        errorUsuario.setStyle("-fx-text-fill: red;");
        errorPassword.setStyle("-fx-text-fill: red;");

        Button loginButton = new Button("Iniciar sesión");
        loginButton.setOnAction(e -> autenticar());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(idField, errorUsuario, passwordField, errorPassword, loginButton);

        primaryStage.setScene(new Scene(layout, 300, 250));
        primaryStage.show();
    }

    private void autenticar() {
        errorUsuario.setText("");
        errorPassword.setText("");

        String idEmpleado = idField.getText();
        String contraseña = passwordField.getText();

        if (idEmpleado.isEmpty()) {
            errorUsuario.setText("El ID de empleado es requerido");
            return;
        }

        if (contraseña.isEmpty()) {
            errorPassword.setText("La contraseña es requerida");
            return;
        }

        EmpleadoModel empleado = autenticarUsuario(idEmpleado, contraseña);
        if (empleado != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Inicio de sesión exitoso");
            alert.setHeaderText(null);
            alert.setContentText("¡Bienvenido, " + empleado.getNombre() + " " + empleado.getApellido() + "!");
            alert.showAndWait();

            // Aquí abrirías MenuEmpleado (otra ventana)
            MenuEmpleado menu = new MenuEmpleado(); 
            menu.start(new Stage());

            ((Stage) idField.getScene().getWindow()).close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de autenticación");
            alert.setHeaderText(null);
            alert.setContentText("Credenciales incorrectas.");
            alert.showAndWait();
        }
    }

    private EmpleadoModel autenticarUsuario(String idEmpleado, String contraseña) {
        String query = "SELECT ID_Empleado, Nombre, Apellido FROM Empleados WHERE ID_Empleado = ? AND Contra = ?";

        try (Connection conn = DriverManager.getConnection(connectionString);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, idEmpleado);
            stmt.setString(2, contraseña);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                EmpleadoModel emp = new EmpleadoModel();
                emp.setIdEmpleado(rs.getString("ID_Empleado"));
                emp.setNombre(rs.getString("Nombre"));
                emp.setApellido(rs.getString("Apellido"));
                return emp;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarError("Error al conectar con la base de datos:\n" + e.getMessage());
        }

        return null;
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Se produjo un error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}