package pe.com.indigitalxp.customerserviceapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import pe.com.indigitalxp.customerserviceapi.util.CustomJsonDateDeserializer;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class CustomerDto {

    @JsonProperty(value = "name")
    @NotEmpty(message = "Field 'name' is required.")
    private String nameCustomer;

    @JsonProperty(value = "lastname")
    @NotEmpty(message = "Field 'lastname' is required.")
    private String lastnameCustomer;

    @JsonProperty(value = "mail")
    @NotEmpty(message = "Field 'mail' is required.")
    private String mailCustomer;

    @JsonProperty(value = "document")
    @NotEmpty(message = "Field 'document' is required.")
    @Size(min = 8, message = "Field 'document' length is 8 characters")
    @Size(min = 8, message = "Field 'document' length is 8 characters")
    @Pattern(regexp = "^[0-9]*$" , message = "Field 'document' only accepts numbers")
    private String documentCustomer;

    @JsonProperty(value = "creation")
    private Date creationCustomer;

    @JsonProperty(value = "born")
    @NotNull(message = "Field 'born' is required.")
    //@Past(message = "The date of birth must be in the past.")
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date bornCustomer;

}
