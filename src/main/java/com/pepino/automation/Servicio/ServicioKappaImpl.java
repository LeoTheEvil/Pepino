package com.pepino.automation.Servicio;

import com.pepino.automation.Modelo.Kappa;
import com.pepino.automation.Repositorio.RepositorioKappa;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicioKappaImpl implements ServicioKappa{

    private final RepositorioKappa repositorioKappa;

    @Override
    public Kappa contratarKappa(Kappa kappa) {
        return repositorioKappa.save(kappa);
    }

    @Override
    public List<Kappa> listarTodosKappas(int offset, int size) {
        Pageable page = PageRequest.of(offset, size);
        return repositorioKappa.findAll();
    }

    @Override
    public Optional<Kappa> buscarKappa(Long idKappa) {
        return Optional.ofNullable(repositorioKappa.findById(idKappa).orElseThrow(() -> {throw new ExcepcionNoEncuentraKappa("Kappa no encontrado");}));
    }

    @Override
    public Kappa actualizarKappa(Long id, Kappa kappaAModificar) {
        Kappa kappaBuscado = repositorioKappa.findById(id).get();
        kappaBuscado.setNombre(kappaAModificar.getNombre());
        kappaBuscado.setRango(kappaAModificar.getRango());
        kappaBuscado.setClase(kappaAModificar.getClase());
        return repositorioKappa.save(kappaBuscado);
    }

    @Override
    public boolean despedirKappa(Long idKappa) {
        try {
            this.buscarKappa(idKappa);
        } catch (ExcepcionNoEncuentraKappa e) {
            return false;
        }
        repositorioKappa.deleteById(idKappa);
        return true;
    }
}