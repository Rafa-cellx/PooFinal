package restaurant.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class InicioSesionAdmin extends Application {

    private static final String ADMIN_ID_VALIDO = "38DE4092-BE44-41CB-B5C5-1E3059AD0CEF";
    private static final String CONTRASENA_VALIDA = "arribala4T";
    private static final String NOMBRE_ADMIN = "Claudia Sheinbaum";

    private TextField idAdmin;
    private PasswordField contrasena;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inicio de Sesión - Administrador");

        idAdmin = new TextField("ID de administrador");
        idAdmin.setOnMouseClicked(e -> {
            if (idAdmin.getText().equals("ID de administrador")) idAdmin.clear();
        });

        contrasena = new PasswordField();
        contrasena.setPromptText("Contraseña");

        Button iniciarSesion = new Button("INICIAR SESIÓN");
        iniciarSesion.setOnAction(e -> manejarInicioSesion(primaryStage));

        Button cerrar = new Button("✖");
        cerrar.setOnAction(e -> primaryStage.close());

        Button minimizar = new Button("-");
        minimizar.setOnAction(e -> primaryStage.setIconified(true));

        HBox botonesVentana = new HBox(5, minimizar, cerrar);
        botonesVentana.setAlignment(Pos.TOP_RIGHT);

        VBox vbox = new VBox(10,
                botonesVentana,
                new Label("Administrador"),
                idAdmin,
                contrasena,
                iniciarSesion
        );
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void manejarInicioSesion(Stage stage) {
        String idIngresado = idAdmin.getText().trim();
        String contrasenaIngresada = contrasena.getText().trim();

        if (idIngresado.isEmpty() || idIngresado.equals("ID de administrador")) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo requerido", "Por favor ingrese su ID de administrador");
            return;
        }

        if (contrasenaIngresada.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo requerido", "Por favor ingrese su contraseña");
            return;
        }

        if (idIngresado.equals(ADMIN_ID_VALIDO) && contrasenaIngresada.equals(CONTRASENA_VALIDA)) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Inicio de sesión exitoso", "¡Bienvenida, " + NOMBRE_ADMIN + "!");
            // Aquí podrías abrir otra ventana
            // new MenuAdmin().start(new Stage());
            stage.close();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de autenticación", "ID o contraseña incorrectos");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}