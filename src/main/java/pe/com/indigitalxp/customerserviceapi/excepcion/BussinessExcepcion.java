package pe.com.indigitalxp.customerserviceapi.excepcion;

public class BussinessExcepcion extends RuntimeException {

    public BussinessExcepcion(String message, Throwable cause){
        super(message, cause);
    }

    public BussinessExcepcion(String message) {
        super(message);
    }
}
