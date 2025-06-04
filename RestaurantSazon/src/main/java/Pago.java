import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Pago {

    static class Platillo {
        String nombre;
        double subtotal;

        Platillo(String nombre, double subtotal) {
            this.nombre = nombre;
            this.subtotal = subtotal;
        }

        @Override
        public String toString() {
            return nombre + " - $" + subtotal;
        }
    }

    private List<Platillo> platillos;
    private double total;

    public Pago(List<Platillo> platillos) {
        this.platillos = platillos;
        this.total = platillos.stream().mapToDouble(p -> p.subtotal).sum();
    }

    public boolean validarDireccion(String direccion) {
        direccion = direccion.trim();

        if (direccion.isEmpty()) {
            System.out.println("Por favor, ingresa una dirección de entrega.");
            return false;
        }
        if (direccion.length() < 10) {
            System.out.println("La dirección debe tener al menos 10 caracteres.");
            return false;
        }
        if (!Pattern.matches("^[a-zA-Z0-9\\s,\\.\\#\\-]+$", direccion)) {
            System.out.println("La dirección contiene caracteres inválidos.");
            return false;
        }
        return true;
    }

    public boolean validarMetodoPago(String metodoPago) {
        if (metodoPago == null || metodoPago.trim().isEmpty() || metodoPago.equalsIgnoreCase("Selecciona uno")) {
            System.out.println("Por favor, selecciona un método de pago válido.");
            return false;
        }
        return true;
    }

    public void mostrarInfoMetodoPago(String metodoPago) {
        switch (metodoPago) {
            case "Efectivo":
                System.out.println("Recuerda tener el monto exacto al momento de la entrega.");
                break;
            case "Tarjeta de débito":
                System.out.println("El repartidor llevará una terminal para el cobro.");
                break;
            case "Tarjeta de crédito":
                System.out.println("Aceptamos todas las tarjetas principales.");
                break;
            case "Transferencia bancaria":
                System.out.println("Se te enviarán los datos al confirmar el pedido.");
                break;
            default:
                System.out.println();
        }
    }

    public void confirmarPedido(String direccion, String metodoPago) {
        System.out.println("\n¿Confirmar pedido?");
        System.out.println("Dirección: " + direccion);
        System.out.println("Método de pago: " + metodoPago);
        System.out.printf("Total: $%.2f\n", total);

        System.out.println("\nEscribe 'sí' para confirmar o cualquier otra cosa para cancelar:");
        Scanner sc = new Scanner(System.in);
        String respuesta = sc.nextLine();

        if (respuesta.equalsIgnoreCase("sí") || respuesta.equalsIgnoreCase("si")) {
            System.out.println("\n¡Pedido confirmado!");
            System.out.println("Dirección: " + direccion);
            System.out.println("Método de pago: " + metodoPago);
        } else {
            System.out.println("\nPedido cancelado.");
        }
    }

    public static void main(String[] args) {
        // Ejemplo de platillos en el carrito
        List<Platillo> platillos = new ArrayList<>();
        platillos.add(new Platillo("Tacos", 120.50));
        platillos.add(new Platillo("Agua fresca", 25.00));

        Pago pago = new Pago(platillos);

        if (platillos.isEmpty()) {
            System.out.println("Tu carrito está vacío. Agrega al menos un platillo para continuar.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        String direccion;
        do {
            System.out.println("Ingresa la dirección de entrega:");
            direccion = scanner.nextLine();
        } while (!pago.validarDireccion(direccion));

        String metodoPago;
        do {
            System.out.println("Selecciona método de pago (Efectivo, Tarjeta de débito, Tarjeta de crédito, Transferencia bancaria):");
            metodoPago = scanner.nextLine();
        } while (!pago.validarMetodoPago(metodoPago));

        pago.mostrarInfoMetodoPago(metodoPago);

        pago.confirmarPedido(direccion, metodoPago);
    }
}
