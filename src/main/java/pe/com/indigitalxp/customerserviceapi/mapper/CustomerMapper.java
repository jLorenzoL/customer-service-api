package pe.com.indigitalxp.customerserviceapi.mapper;

import org.mapstruct.*;
import pe.com.indigitalxp.customerserviceapi.dto.CustomerDto;
import pe.com.indigitalxp.customerserviceapi.entity.CollCustomer;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mappings({
            @Mapping(target="strName", source="nameCustomer"),
            @Mapping(target="strLastname", source="lastnameCustomer"),
            @Mapping(target="strMail", source="mailCustomer"),
            @Mapping(target="strDocument", source="documentCustomer"),
            @Mapping(target="dtCreationDate", source="creationCustomer"),
            @Mapping(target="dtBornDate", source="bornCustomer"),
            @Mapping(target="state", source="state")
    })
    CollCustomer customDtoToEntity(CustomerDto customerDto);

    @IterableMapping(qualifiedByName = "extensionMap")
    public abstract List<CustomerDto> map(List<CollCustomer> collCustomer);


    @Named("extensionMap")
    @Mapping(target="nameCustomer", source="strName")
    @Mapping(target="lastnameCustomer", source="strLastname")
    @Mapping(target="mailCustomer", source="strMail")
    @Mapping(target="documentCustomer", source="strDocument")
    @Mapping(target="creationCustomer", source="dtCreationDate")
    @Mapping(target="bornCustomer", source="dtBornDate")
    CustomerDto customDtoToEntity(CollCustomer collCustomer);
}
