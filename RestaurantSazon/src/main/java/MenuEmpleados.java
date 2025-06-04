

public class MenuEmpleados {
    private String tipoUsuario;

    public MenuEmpleados(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void regresar() {
        if ("admin".equals(tipoUsuario)) {
            MenuAdmin menu = new MenuAdmin("admin");
            menu.mostrar();
        } else {
            MenuEmpleados menu = new MenuEmpleados("empleado");
            menu.mostrar();
        }
        cerrarVentana();
    }

    public void verPedidos() {
        ResumenPedidos pedidos = new ResumenPedidos("empleado");
        pedidos.mostrar();
        cerrarVentana();
    }

    public void cerrarVentana() {
        System.out.println("Ventana cerrada.");
        // Aquí podrías redirigir o invalidar la sesión si estás en web.
    }

    public void minimizarVentana() {
        System.out.println("Ventana minimizada.");
        // No aplica igual que en escritorio, pero podrías simularlo si lo necesitas.
    }

    public void irASoporte() {
        Soporte soporte = new Soporte();
        soporte.mostrar();
        cerrarVentana();
    }

    public void irAUsuario() {
        LoginView login = new LoginView();
        login.mostrar();
        cerrarVentana();
    }

    public void irAPersonal() {
        LoginEmpleado login = new LoginEmpleado();
        login.mostrar();
        cerrarVentana();
    }

    public void irAAdministrador() {
        InicioSesionAdmin login = new InicioSesionAdmin();
        login.mostrar();
        cerrarVentana();
    }

    public void mostrar() {
        System.out.println("Menú Empleados mostrado para: " + tipoUsuario);
    }
}
