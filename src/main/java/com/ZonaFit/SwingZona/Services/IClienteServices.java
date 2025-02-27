package com.ZonaFit.SwingZona.Services;

import com.ZonaFit.SwingZona.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IClienteServices {
    public List<Cliente> listar();
    public Optional<Cliente> buscar(int id);
    public void agregar(Cliente cliente);
    public void eliminar(int id);
    public void modificar(Cliente cliente);

}
