import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MenuAdmin extends Application {

    private String tipoUsuario;

    public static void main(String[] args) {
        launch(args);
    }

    public MenuAdmin(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public void start(Stage primaryStage) {
        // Main container with background
        StackPane root = new StackPane();
        ImageView background = new ImageView(new Image(getClass().getResourceAsStream("/Imagenes/fondo.png")));
        background.setFitWidth(550);
        background.setFitHeight(500);
        root.getChildren().add(background);

        // Main layout
        BorderPane mainPane = new BorderPane();
        mainPane.setPrefSize(550, 500);

        // Top bar with menu
        HBox topBar = createTopBar(primaryStage);
        mainPane.setTop(topBar);

        // Window controls
        HBox windowControls = createWindowControls(primaryStage);
        mainPane.setRight(windowControls);

        // Center content with buttons
        VBox centerContent = createCenterContent(primaryStage);
        mainPane.setCenter(centerContent);

        // Bottom content
        HBox bottomContent = createBottomContent(primaryStage);
        mainPane.setBottom(bottomContent);

        root.getChildren().add(mainPane);

        // Scene setup
        Scene scene = new Scene(root, 550, 500);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Menú Administrador");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createTopBar(Stage stage) {
        HBox topBar = new HBox();
        topBar.setStyle("-fx-background-color: #80FFA500; -fx-border-radius: 0 0 10 10;");
        topBar.setPrefHeight(20);

        Text title = new Text("El Sazón de Cantera y Plata");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        title.setFill(Color.WHITE);

        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: transparent;");

        // Support menu
        Menu supportMenu = new Menu("Chat con Soporte");
        supportMenu.setOnAction(e -> openSoporte(stage));

        // Register menu
        Menu registerMenu = new Menu("Regístrate");
        MenuItem userItem = new MenuItem("Usuario");
        userItem.setOnAction(e -> openLoginView(stage));
        MenuItem staffItem = new MenuItem("Personal");
        staffItem.setOnAction(e -> openLoginEmpleado(stage));
        MenuItem adminItem = new MenuItem("Administrador");
        adminItem.setOnAction(e -> openInicioSesionAdmin(stage));

        registerMenu.getItems().addAll(userItem, staffItem, adminItem);
        menuBar.getMenus().addAll(supportMenu, registerMenu);

        topBar.getChildren().addAll(title, menuBar);
        return topBar;
    }

    private HBox createWindowControls(Stage stage) {
        HBox controls = new HBox(5);
        controls.setStyle("-fx-padding: 5;");

        Button minimizeBtn = new Button("–");
        minimizeBtn.setStyle("-fx-background-color: #c86d05; -fx-text-fill: white; -fx-font-weight: bold;");
        minimizeBtn.setOnAction(e -> stage.setIconified(true));

        Button closeBtn = new Button("✖");
        closeBtn.setStyle("-fx-background-color: #c86d05; -fx-text-fill: white; -fx-font-weight: bold;");
        closeBtn.setOnAction(e -> stage.close());

        controls.getChildren().addAll(minimizeBtn, closeBtn);
        return controls;
    }

    private VBox createCenterContent(Stage stage) {
        VBox centerBox = new VBox(20);
        centerBox.setStyle("-fx-alignment: center; -fx-padding: 50;");

        Button pedidosBtn = createAdminButton("Revisar Pedidos");
        pedidosBtn.setOnAction(e -> openResumenPedidos(stage));

        Button empleadosBtn = createAdminButton("Gestionar Empleados");
        empleadosBtn.setOnAction(e -> gestionarEmpleados());

        centerBox.getChildren().addAll(pedidosBtn, empleadosBtn);
        return centerBox;
    }

    private HBox createBottomContent(Stage stage) {
        HBox bottomBox = new HBox();
        bottomBox.setStyle("-fx-alignment: center; -fx-padding: 10;");

        Button backBtn = new Button("Regresar");
        backBtn.setStyle("-fx-background-color: #80FFA500; -fx-text-fill: white; -fx-font-weight: bold;");
        backBtn.setOnAction(e -> goBack(stage));

        Text footerText = new Text("Menú de Administración");
        footerText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        footerText.setFill(Color.web("#b96300"));

        bottomBox.getChildren().addAll(backBtn, footerText);
        return bottomBox;
    }

    private Button createAdminButton(String text) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: #80FFA500; -fx-text-fill: white; -fx-font-weight: bold; "
                + "-fx-font-size: 14; -fx-min-width: 250; -fx-min-height: 60; "
                + "-fx-background-radius: 10;");
        return btn;
    }

    private void goBack(Stage currentStage) {
        currentStage.close();
        if ("admin".equals(tipoUsuario)) {
            new MenuAdmin("admin").start(new Stage());
        } else {
            new MenuEmpleados("empleado").start(new Stage());
        }
    }

    private void openSoporte(Stage currentStage) {
        currentStage.close();
        new Soporte().start(new Stage());
    }

    private void openLoginView(Stage currentStage) {
        currentStage.close();
        new LoginView().start(new Stage());
    }

    private void openLoginEmpleado(Stage currentStage) {
        currentStage.close();
        new LoginEmpleado().start(new Stage());
    }

    private void openInicioSesionAdmin(Stage currentStage) {
        currentStage.close();
        new InicioSesionAdmin().start(new Stage());
    }

    private void openResumenPedidos(Stage currentStage) {
        currentStage.close();
        new ResumenPedidos("admin").start(new Stage());
    }

    private void gestionarEmpleados() {
        System.out.println("Gestionar empleados clicked");
        // Implementar lógica para gestión de empleados
    }
}

// Supporting classes implementations
class MenuEmpleados extends Application {
    private String tipoUsuario;
    
    public MenuEmpleados(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    @Override
    public void start(Stage primaryStage) {
        // Similar implementation to MenuAdmin but for employees
        StackPane root = new StackPane();
        primaryStage.setScene(new Scene(root, 550, 500));
        primaryStage.setTitle("Menú Empleados");
        primaryStage.show();
    }
}

class Soporte extends Application {
    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.setTitle("Soporte");
        primaryStage.show();
    }
}

class LoginView extends Application {
    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.setTitle("Login Usuario");
        primaryStage.show();
    }
}

class LoginEmpleado extends Application {
    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.setTitle("Login Empleado");
        primaryStage.show();
    }
}

class InicioSesionAdmin extends Application {
    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.setTitle("Login Administrador");
        primaryStage.show();
    }
}

class ResumenPedidos extends Application {
    private String tipoUsuario;
    
    public ResumenPedidos(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setTitle("Resumen de Pedidos");
        primaryStage.show();
    }
}