package pe.com.indigitalxp.customerserviceapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StatisticDto {

    @JsonProperty(value = "name")
    private Integer _id;

    @JsonProperty(value = "value")
    private Integer count;
}
