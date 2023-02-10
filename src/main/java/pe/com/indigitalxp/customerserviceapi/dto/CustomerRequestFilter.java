package pe.com.indigitalxp.customerserviceapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class CustomerRequestFilter {

//    @Size(min = 8, message = "Field 'documentNumber' length is 8 characters")
//    @Size(max = 9, message = "Field 'documentNumber' length is 8 characters")
    @Pattern(regexp = "^[0-9]*$" , message = "Field 'documentNumber' only accepts numbers")
    private String documentNumber;
    private String email;
}
