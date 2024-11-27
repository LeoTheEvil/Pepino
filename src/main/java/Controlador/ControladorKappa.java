package Controlador;

import Modelo.Kappa;
import Servicio.ExcepcionNoEncuentraKappa;
import Servicio.ServicioKappa;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kappa")
@RequiredArgsConstructor
public class ControladorKappa {

    private final ServicioKappa servicioKappa;
    private final Validador validador;
    @PostMapping
    public ResponseEntity contratarKappa(@RequestBody Kappa kappa) {
        try {validador.validar(kappa);
            return new ResponseEntity(servicioKappa.contratarKappa(kappa), HttpStatus.CREATED);
        } catch(ParametroIncorrecto e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    public ResponseEntity listarTodosKappas(@RequestParam(defaultValue = "0") String offset, @RequestParam(defaultValue = "10") String size) {
        try {
            return new ResponseEntity(servicioKappa.listarTodosKappas(Integer.valueOf(offset), Integer.valueOf(size)), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity("Offset debe ser un numero mayor o igual a 0, y Size debe ser un numero mayor a 0.",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity buscarKappa(@PathVariable("id") Long idKappa) {
        try {
            return new ResponseEntity(servicioKappa.buscarKappa(idKappa), HttpStatus.OK);
        } catch (ExcepcionNoEncuentraKappa e) {
            return new ResponseEntity("El Kappa "+idKappa+" no fue encontrado.",HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity actualizarKappa(@PathVariable("id") Long idKappa, @RequestBody Kappa kappa) {
        try {validador.validar(kappa);
            return new ResponseEntity(servicioKappa.kappaAModificar(idKappa,kappa), HttpStatus.OK);
        } catch(ParametroIncorrecto e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity despedirKappa(@PathVariable("id") Long idKappa) {

        boolean respuesta = servicioKappa.despedirKappa(idKappa);
        if (respuesta == true) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity("El Kappa "+idKappa+" no fue encontrado.",HttpStatus.NOT_FOUND);
        }
    }
}