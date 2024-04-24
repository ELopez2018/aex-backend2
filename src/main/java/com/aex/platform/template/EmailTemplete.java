package com.aex.platform.template;

import com.aex.platform.entities.User;
import org.springframework.stereotype.Service;

@Service
public class EmailTemplete {
    String platformUrl = System.getenv("PLATFORM_URL");

    public String newTask(User user, Integer totalTask) {
        String tareas = totalTask > 1 ? " se han registrado " + totalTask + " tareas en la plataforma!" : " se ha registrado " + totalTask + " tarea en la plataforma!";
        return "<html>"
                + "<head>"
                + "<style>"
                + "    body { font-family: Arial, sans-serif; }"
                + "    h1 { color: #007bff; }"
                + "    p { color: #333; }"
                + "    .aex { color: #007bff; font-size: 18px; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<h1>¡Hola " + user.getFullName() + tareas + "</h1>"
                + "<p>Date prisa y revisa la plataforma haciendo click <a href='" + platformUrl + "'> aqui </a>.</p>"
                + "<p>Atentamente,</p>"
                + "<p class='aex' >AEX</p>"
                + "</body>"
                + "</html>";
    }

    public String toBenficiaryInProgress(User user) {
        return "<html>"
                + "<head>"
                + "<style>"
                + "    body { font-family: Arial, sans-serif; }"
                + "    h2 { color: #007bff; }"
                + "    p { color: #333; }"
                + "    .aex { color: #007bff; font-size: 22px; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<h2>¡Hola " + user.getFullName() + " buenas noticias, has recibido un pago!</h2>"
                + "<p>Has recibido un envio de dinero, estamos procesandolo lo mas rápido posible te volveremos a escribir cuando se halla completado. "
                + "Mientras puedes seguir el estado de la operacion haciendo click <a href='http://ec2-52-23-71-203.compute-1.amazonaws.com/'> aqui </a>.</p>"
                + "<p>Atentamente,</p>"
                + "<p class='aex' >AEX</p>"
                + "</body>"
                + "</html>";
    }
}
