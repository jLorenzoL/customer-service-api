package pe.com.indigitalxp.customerserviceapi.util;

import java.util.HashMap;
import java.util.Map;

public class JsonManagerResponse {

    private Map<String, Object> response = new HashMap<>();

    public JsonManagerResponse(String message, boolean state) {
        response.put("message", message);
        response.put("state", state);
    }

    public static JsonManagerResponse correctProcess(){
        return new JsonManagerResponse("The process is correct.", Boolean.TRUE);
    }

    public static JsonManagerResponse processError(Exception e){
        return new JsonManagerResponse(e.getMessage(), Boolean.FALSE);
    }

    public static JsonManagerResponse processError(String message){
        return new JsonManagerResponse(message, Boolean.FALSE);
    }

    public Map<String, Object> getResponse(){
        return response;
    }

    public JsonManagerResponse buildResponse(String name, Object value){
        response.put(name, value);
        return this;
    }

    public JsonManagerResponse buildResponse(Map<String, Object> response){
        this.response.putAll(response);
        return this;
    }
}
