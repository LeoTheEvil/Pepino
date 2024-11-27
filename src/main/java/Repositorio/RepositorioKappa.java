package Repositorio;

import Modelo.Kappa;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioKappa extends JpaRepository<Kappa,Long> {

}