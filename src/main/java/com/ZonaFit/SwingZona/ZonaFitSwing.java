package com.ZonaFit.SwingZona;


import com.ZonaFit.SwingZona.gui.ZonaFit;
import com.formdev.flatlaf.FlatDarculaLaf;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import javax.swing.*;

@SpringBootApplication
public class ZonaFitSwing {
    public static void main(String[] args) {
        // Instancia la fabrica de spring
        FlatDarculaLaf.setup();
        ConfigurableApplicationContext contextoSpring =
                new SpringApplicationBuilder(ZonaFitSwing.class)
                        .headless(false)
                        .web(WebApplicationType.NONE)
                        .run(args);


        // Crear un objeto de Swing
        SwingUtilities.invokeLater(()-> {
            ZonaFit zonaFit = contextoSpring.getBean(ZonaFit.class);
            zonaFit.setVisible(true);
        });
    }
}
