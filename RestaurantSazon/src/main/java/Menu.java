import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class Menu extends Application {
    
    private MenuViewModel viewModel = new MenuViewModel();
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        
        primaryStage.setTitle("Menú Completo");
        primaryStage.setScene(new Scene(root, 1200, 700));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    @FXML
    private void menuUsuarioClick(ActionEvent event) {
        LoginView usuario = new LoginView();
        usuario.show();
        ((Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow()).close();
    }
    
    @FXML
    private void menuPersonalClick(ActionEvent event) {
        LoginEmpleado personal = new LoginEmpleado();
        personal.show();
        ((Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow()).close();
    }
    
    @FXML
    private void menuAdministradorClick(ActionEvent event) {
        InicioSesionAdmin admin = new InicioSesionAdmin();
        admin.show();
        ((Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow()).close();
    }
    
    @FXML
    private void cerrarVentanaClick(ActionEvent event) {
        ((Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow()).close();
    }
    
    @FXML
    private void minimizarClick(ActionEvent event) {
        ((Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow()).setIconified(true);
    }
    
    @FXML
    private void soporteClick(ActionEvent event) {
        Soporte soporte = new Soporte();
        soporte.show();
        ((Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow()).close();
    }
    
    @FXML
    private void menuItemClick(ActionEvent event) {
        // Implementación vacía pero necesaria para el manejador de eventos
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

// Supporting classes that would need to be implemented
class MenuViewModel {
    // ViewModel implementation would go here
}

class LoginView extends Stage {
    public void show() {
        // Implementation for showing login view
    }
}

class LoginEmpleado extends Stage {
    public void show() {
        // Implementation for showing employee login
    }
}

class InicioSesionAdmin extends Stage {
    public void show() {
        // Implementation for showing admin login
    }
}

class Soporte extends Stage {
    public void show() {
        // Implementation for showing support window
    }
}