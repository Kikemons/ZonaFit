package com.ZonaFit.SwingZona.gui;

import com.ZonaFit.SwingZona.Services.IClienteServices;
import com.ZonaFit.SwingZona.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

@Component
public class ZonaFit extends JFrame {
    IClienteServices clienteServices;
    private JPanel panelPrincipal;
    private JTextField txt_nombre;
    private JTextField txt_apellido;
    private JTextField txt_membresia;
    private JButton guardarButton;
    private JButton eliminarButton;
    private JButton limpiarButton;
    private JTable Jtable_clientes;
    private Integer id;
    public DefaultTableModel model ;
    public int clienteValido;
    public Integer membresia;
    public String nombre;
    public String apellido;
    public boolean valido;

    @Autowired
    public ZonaFit(IClienteServices clienteServices) {
        this.clienteServices = clienteServices;
        iniciarForma();
        mostrarClientes();


        guardarButton.addActionListener(e -> {
            verificarCliente();
            ListarClientes();
        });

        limpiarButton.addActionListener(e -> {
        limpiar();
        });

        eliminarButton.addActionListener(e -> {
            eliminarCliente();
            txt_nombre.setText("");
            txt_apellido.setText("");
            txt_membresia.setText("");
        });

        Jtable_clientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                guardarIdCliente();
            }
        });
    }

    private void guardarIdCliente() {
        int renglon = Jtable_clientes.getSelectedRow();
        if (renglon != -1) {
            id = Integer.parseInt(Jtable_clientes.getModel().getValueAt(renglon, 0).toString());
            //System.out.println("el id es: " + id);

            var nombre = Jtable_clientes.getModel().getValueAt(renglon, 1).toString();
            var apellido = Jtable_clientes.getModel().getValueAt(renglon, 2).toString();
            var membresia = Jtable_clientes.getModel().getValueAt(renglon, 3).toString();
            txt_nombre.setText(nombre);
            txt_apellido.setText(apellido);
            txt_membresia.setText(membresia);
        }
    }

    private void iniciarForma() {
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);//centra ventana

    }

    private void mostrarClientes() {
        model = new DefaultTableModel(0, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hace que todas las celdas sean no editables
            }
        };
        model.addColumn("");
        model.addColumn("NOMBRE");
        model.addColumn("APELLIDO");
        model.addColumn("MEMBRESIA");
        ListarClientes();

    }

    private void ListarClientes() {
        model.setRowCount(0);
        Jtable_clientes.setModel(model);
        var clientes = clienteServices.listar();
        clientes.forEach(cliente -> {
            Object[] fila = {
                    cliente.getId(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getMembresia()
            };

            model.addRow(fila);
        });
    }

    private void agregarCliente() {
        Cliente cliente = new Cliente();

        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setMembresia(membresia);
        clienteServices.agregar(cliente);
        txt_membresia.setBackground(Color.green);
        txt_nombre.setBackground(Color.green);
        txt_apellido.setBackground(Color.green);
        JOptionPane.showMessageDialog(null, "Cliente agregado");
        limpiar();


    }

    private void limpiar() {
        model.setRowCount(0);
        txt_membresia.setText("");
        txt_nombre.setText("");
        txt_apellido.setText("");
        txt_membresia.setBackground(Color.gray);
        txt_nombre.setBackground(Color.gray);
        txt_apellido.setBackground(Color.gray);
        id=null;
        ListarClientes();
    }

    private void eliminarCliente() {

        if (id != null) {
            clienteServices.eliminar(id);
            model.setRowCount(0);
            ListarClientes();
            id=null;
        } else {
            try {
                var eliminar = JOptionPane.showInputDialog(null,
                        "Ingrese el id del ususario que desea elimininar: ");
                if (eliminar != null) {
                    if (!eliminar.trim().isEmpty()) {
                        int delete = Integer.parseInt(eliminar);
                        Optional<Cliente> cl = clienteServices.buscar(delete);
                        if (cl.isPresent()) {
                            clienteServices.eliminar(delete);
                            model.setRowCount(0);
                            ListarClientes();
                        } else {
                            JOptionPane.showMessageDialog(null, "Ingrese un id  valido");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Ingrese un id  valido");
                    }
                }

            } catch (Exception ex) {
                System.err.println("Error al eliminar el ususario: " + ex.getMessage());
            }
        }
    }

    private void actualizarCliente() {
        Optional<Cliente> cl = clienteServices.buscar(id);
        var nombre = txt_nombre.getText().trim();
        var apellido = txt_apellido.getText().trim();


            System.out.println(cl);
            cl.get().setMembresia(membresia);
            cl.get().setNombre(nombre);
            cl.get().setApellido(apellido);
            clienteServices.modificar(cl.get());
            JOptionPane.showMessageDialog(null, "Cliente actualizado");
            txt_membresia.setText("");
            txt_nombre.setText("");
            txt_apellido.setText("");
            limpiar();



    }

    private void verificarCliente() {
        try {
            clienteValido = 0;
            nombre = txt_nombre.getText().trim();

            apellido = txt_apellido.getText().trim();

            if (nombre.isEmpty()) {
                txt_nombre.setBackground(Color.red);
                clienteValido++;
            }
            if (apellido.isEmpty()) {
                txt_apellido.setBackground(Color.red);
                clienteValido++;
            }

            if (!txt_membresia.getText().trim().isEmpty()) {
                membresia = Integer.parseInt(txt_membresia.getText().trim());
            } else {
                txt_membresia.setBackground(Color.red);
                membresia = -1;
                System.out.println("membresia: esta vacia");
            }
            if (membresia < 0 && membresia > 100) {
                txt_membresia.setBackground(Color.red);
                clienteValido++;
            }
            if (clienteValido == 0) {
                if (id != null) {
                    System.out.println("validacion 1");
                    Optional<Cliente> cl = clienteServices.buscar(id);

                    if (cl.get().getId() == id) {
                        actualizarCliente();
                        id = null;
                    }
                } else {
                    System.out.println("agregar cliente");
                    agregarCliente();
                    System.out.println(membresia);
                }
            } else {
                JOptionPane.showMessageDialog(null, "ingrese los datos correctamente");
                txt_membresia.setText("");
                txt_nombre.setText("");
                txt_apellido.setText("");
                txt_membresia.setBackground(Color.gray);
                txt_nombre.setBackground(Color.gray);
                txt_apellido.setBackground(Color.gray);
            }
        } catch (Exception e) {
            txt_membresia.setBackground(Color.red);
            txt_membresia.setText("");
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "ingrese una membresia que corresponda");
            txt_membresia.setBackground(Color.gray);
        }

    }
}



