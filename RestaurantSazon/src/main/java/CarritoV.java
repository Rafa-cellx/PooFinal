package restaurant.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import restaurant.viewmodel.CarritoViewModel;
import restaurant.model.Platillo;

public class CarritoVController {

    private CarritoViewModel viewModel;

    // Referencia a la ventana actual para cerrarla/minimizarla
    private Stage stage;

    // Botón Pagar (suponiendo que lo tienes en tu FXML y vinculado)
    @FXML
    private Button btnPagar;

    // Constructor (o método init) recibe el carrito de platillos
    public void initData(ObservableList<Platillo> carrito, Stage stage) {
        this.stage = stage;
        this.viewModel = new CarritoViewModel(carrito);

        // Aquí enlazarías el ViewModel con tu UI
        // Por ejemplo, binds con propiedades de JavaFX, no mostrado aquí por simplicidad

        // Habilitar o deshabilitar botón pagar según estado (simulado)
        btnPagar.setDisable(viewModel.getCarrito().isEmpty());
    }

    @FXML
    private void pagarClick() {
        // Abrir ventana de pago (Pago.java)
        Pago pagoWindow = new Pago(viewModel.getCarrito());

        // Mostrar la ventana de pago y esperar resultado (modal)
        boolean resultado = pagoWindow.showAndWait();

        if (resultado) {
            viewModel.getCarrito().clear();
            stage.close();
        }
    }

    @FXML
    private void cerrarVentanaClick() {
        stage.close();
    }

    @FXML
    private void minimizarClick() {
        stage.setIconified(true);
    }

    @FXML
    private void soporteClick() {
        Soporte soporteWindow = new Soporte();
        soporteWindow.show();
        stage.close();
    }

    @FXML
    private void menuUsuarioClick() {
        LoginView loginUsuario = new LoginView();
        loginUsuario.show();
        stage.close();
    }

    @FXML
    private void menuPersonalClick() {
        LoginEmpleado loginPersonal = new LoginEmpleado();
        loginPersonal.show();
        stage.close();
    }

    @FXML
    private void menuAdministradorClick() {
        InicioSesionAdmin loginAdmin = new InicioSesionAdmin();
        loginAdmin.show();
        stage.close();
    }

    // Este método puede estar vacío como en C#
    @FXML
    private void menuItemClick() {
        // No hace nada
    }
}