package pe.com.indigitalxp.customerserviceapi.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pe.com.indigitalxp.customerserviceapi.dto.CustomerDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CustomerControllerTest {

    @Autowired
    CustomerController customerController;

    @Test
    void validateController() {
        Assertions.assertThat(customerController).isNotNull();
    }

    @Test
    void testGetData() {
        CustomerDto customer = new CustomerDto();

        //Assertions.assertThat(customerController.getCustomers(null,null)).isNotNull();
    }
}