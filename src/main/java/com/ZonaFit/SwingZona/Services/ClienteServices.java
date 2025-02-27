package com.ZonaFit.SwingZona.Services;

import com.ZonaFit.SwingZona.Repository.ClienteRepository;
import com.ZonaFit.SwingZona.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ClienteServices implements IClienteServices {

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> buscar(int id) {
        return clienteRepository.findById(id);
    }

    @Override
    public void agregar(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Override
    public void eliminar(int id) {
    clienteRepository.deleteById(id);
    }

    @Override
    public void modificar(Cliente cliente) {
    clienteRepository.save(cliente);
    }
}
