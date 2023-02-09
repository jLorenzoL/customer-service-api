package pe.com.indigitalxp.customerserviceapi.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import pe.com.indigitalxp.customerserviceapi.excepcion.BussinessExcepcion;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomJsonDateDeserializer extends JsonDeserializer<Date> {

    public static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {

        String date = jsonParser.getText();

        try {
            return format.parse(date);
        } catch (Exception e) {
            throw new BussinessExcepcion("Invalid format. Must be yyyy-MM-dd");
        }
    }
}
