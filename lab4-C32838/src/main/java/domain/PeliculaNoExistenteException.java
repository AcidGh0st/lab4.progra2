package domain;

public class PeliculaNoExistenteException extends Exception {

    public PeliculaNoExistenteException() {
        super("Un empleado con esta identificación ya existe");
    }
    public PeliculaNoExistenteException(String message) {
        super(message);
    }

}
