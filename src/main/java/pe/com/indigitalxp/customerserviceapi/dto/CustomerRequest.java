package pe.com.indigitalxp.customerserviceapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class CustomerRequest {

    private Integer results;
    private Integer page;
    private boolean pageable;

    @Valid
    @JsonProperty(required = true)
    @NotNull(message = "Paramter 'customerFilter' is invalid. Can't  be null")
    private CustomerRequestFilter customerFilter;
}
