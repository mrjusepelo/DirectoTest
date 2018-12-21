package co.devpenguin.directotest.utils;

import java.io.Serializable;

/**
 * Esta clase representa un paso del evento que tenga alguna conexión, ya sea con el servidor o el surtidor
 * Tiene como variables de clase llos pasos para identificar los diferentes Web Services de conexión.
 * Tiene como variables de instancia el evento, la url del web service y el paso actual de conexión.
 */

public class APIRequest implements Serializable {

    public final static int LOGIN = 1;
    public final static int PROSPECTUS = 2;


    public int step;
    public RestClient.RequestMethod method;

    public APIRequest(int step, RestClient.RequestMethod method) {
        this.step = step;
        this.method = method;
    }
}
