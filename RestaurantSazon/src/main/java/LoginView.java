package restaurant.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import restaurant.model.UserModel;

import java.sql.*;

public class LoginView {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private CheckBox chkRecordar;

    @FXML
    private Button btnIniciarSesion;

    private final String connectionString = "jdbc:sqlserver://LAPTOP-MKB9NF10\\RAFAGESTION;databaseName=GestionRestaurante;integratedSecurity=true;";

    @FXML
    public void initialize() {
        txtUsuario.setText("Nombre");
        txtContrasena.setPromptText("Contraseña");
    }

    @FXML
    private void onUsuarioClick(MouseEvent event) {
        if (txtUsuario.getText().equals("Nombre")) {
            txtUsuario.clear();
        }
    }

    @FXML
    private void onContrasenaClick(MouseEvent event) {
        txtContrasena.clear();
    }

    @FXML
    private void onIniciarSesion(ActionEvent event) {
        String usuario = txtUsuario.getText().trim();
        String contrasena = txtContrasena.getText().trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarAlerta("Error", "Por favor, ingresa tu nombre de usuario y contraseña.", Alert.AlertType.WARNING);
            return;
        }

        UserModel user = autenticarUsuario(usuario, contrasena);

        if (user != null) {
            mostrarAlerta("Inicio de sesión exitoso", "¡Bienvenido, " + user.getNombre() + "!", Alert.AlertType.INFORMATION);
            abrirMenuPrincipal();
            cerrarVentana();
        } else {
            mostrarAlerta("Error", "Datos inválidos, intenta de nuevo.", Alert.AlertType.ERROR);
        }
    }

    private UserModel autenticarUsuario(String usuario, String contrasena) {
        UserModel user = null;

        try (Connection conn = DriverManager.getConnection(connectionString);
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT ID, Nombre, Apellido, Num_cel, Email, Contra FROM [User] " +
                     "WHERE (Nombre = ? OR Email = ? OR Num_cel = ?) AND Contra = ?")
        ) {
            stmt.setString(1, usuario);
            stmt.setString(2, usuario);
            stmt.setString(3, usuario);
            stmt.setString(4, contrasena);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new UserModel(
                        rs.getString("ID"),
                        rs.getString("Nombre"),
                        rs.getString("Apellido"),
                        rs.getString("Num_cel"),
                        rs.getString("Email"),
                        rs.getString("Contra")
                );
            }

        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al conectar con la base de datos: " + e.getMessage(), Alert.AlertType.ERROR);
        }

        return user;
    }

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minimizarVentana() {
        Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void abrirRegistroUsuario(ActionEvent event) {
        // Abrir ventana de registro (FXMLLoader)
        // Ejemplo: Main.cambiarEscena("RegistroUsuario.fxml");
    }

    @FXML
    private void abrirMenuPrincipal() {
        // Lógica para abrir la ventana principal del menú
        // Ejemplo: Main.cambiarEscena("Menu.fxml");
    }

    @FXML
    private void cambiarAVistaUsuario(ActionEvent event) {
        // Recargar la vista actual
    }

    @FXML
    private void cambiarAVistaPersonal(ActionEvent event) {
        // Ejemplo: Main.cambiarEscena("LoginEmpleado.fxml");
    }

    @FXML
    private void cambiarAVistaAdministrador(ActionEvent event) {
        // Ejemplo: Main.cambiarEscena("InicioSesionAdmin.fxml");
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}