package Controlador;

public class ParametroIncorrecto extends RuntimeException {

    public ParametroIncorrecto(String message) {
        super(message);
    }
}