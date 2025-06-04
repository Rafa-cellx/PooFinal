import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class SoporteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener mensaje del usuario
        String mensaje = request.getParameter("mensaje");

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        if (mensaje == null || mensaje.trim().isEmpty()) {
            out.print("Error: mensaje vacío");
            return;
        }

        // Aquí podrías agregar lógica real, pero por ahora simulamos respuesta
        String respuestaBot = "Bot: Gracias por tu mensaje. Un agente te atenderá enseguida.";

        // Responder con el mensaje de confirmación
        out.print(respuestaBot);
    }
}

//sale error pq no hay servlets, ookkk
