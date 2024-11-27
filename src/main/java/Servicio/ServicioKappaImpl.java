package Servicio;

import Modelo.Kappa;
import Repositorio.RepositorioKappa;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
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
        return repositorioKappa.findAll(page).getContent();
    }

    @Override
    public Kappa buscarKappa(Long idKappa) {
        return repositorioKappa.findById(idKappa).orElseThrow(() -> {throw new ExcepcionNoEncuentraKappa();});
    }

    @Override
    public Kappa kappaAModificar(Long id, Kappa kappaAModificar) {
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