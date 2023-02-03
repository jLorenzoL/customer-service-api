package pe.com.indigitalxp.customerserviceapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CustomerDto {

    @JsonProperty(value = "name", required = true)
    private String nameCustomer;
    @JsonProperty(value = "lastname", required = true)
    private String lastnameCustomer;
    @JsonProperty(value = "mail", required = true)
    private String mailCustomer;
    @JsonProperty(value = "document", required = true)
    private String documentCustomer;
    @JsonProperty(value = "creation", required = false)
    private Date creationCustomer;
    @JsonProperty(value = "born", required = true)
    private Date bornCustomer;
}
