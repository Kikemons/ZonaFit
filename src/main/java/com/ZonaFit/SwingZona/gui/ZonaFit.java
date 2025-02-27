package com.ZonaFit.SwingZona.gui;

import com.ZonaFit.SwingZona.Services.ClienteServices;
import com.ZonaFit.SwingZona.Services.IClienteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class ZonaFit extends JFrame {
    IClienteServices clienteServices;
    private JPanel panelPrincipal;

    @Autowired
    public ZonaFit(ClienteServices clienteServices) {
    this.clienteServices = clienteServices;
    iniciarForma();
    }

    private void iniciarForma() {
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,700);
        setLocationRelativeTo(null);//centra ventana

    }
}


