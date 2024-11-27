package Servicio;

import Modelo.Kappa;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServicioKappa {
    Kappa contratarKappa(Kappa kappa);
    List<Kappa> listarTodosKappas(int offset, int size);
    Kappa buscarKappa(Long idKappa);
    Kappa actualizarKappa(Long id, Kappa kappaAModificar);
    boolean despedirKappa(Long idKappa);
}