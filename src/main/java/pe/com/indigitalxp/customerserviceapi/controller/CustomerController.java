package pe.com.indigitalxp.customerserviceapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.com.indigitalxp.customerserviceapi.dto.CustomerDto;
import pe.com.indigitalxp.customerserviceapi.excepcion.BussinessExcepcion;
import pe.com.indigitalxp.customerserviceapi.service.CustomerService;
import pe.com.indigitalxp.customerserviceapi.util.JsonManagerResponse;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/saveCustomer")
    public Map<String, Object> saveCustomer(@Valid @RequestBody CustomerDto customerDto) {

        log.info("Inicio controller saveCustomer()");
        Map<String, Object> response;

        try {
            customerService.saveCustomer(customerDto);
            response = JsonManagerResponse.correctProcess().getResponse();
        }catch (Exception e) {
            response = new JsonManagerResponse(e.getMessage(), Boolean.FALSE).getResponse();
        }
        log.info("Fin controller saveCustomer()");
        return response;
    }



    @GetMapping("/search")
    public Map<String, Object> getCustomers(@RequestParam(required = false) String document,
                                            @RequestParam(required = false) String email) {

        Map<String, Object> response;

        try {
            return new JsonManagerResponse("Correct process.", Boolean.TRUE)
                    .buildResponse("result", customerService.listCustomers(document, email)).getResponse();
        }catch (BussinessExcepcion e) {
            response = new JsonManagerResponse(e.getMessage(), Boolean.FALSE).getResponse();
        }

        return response;
    }

    @GetMapping("/getInfoByMonth")
    public Map<String, Object> getInfoByMonth() {

        Map<String, Object> response;

        try {
            return new JsonManagerResponse("Correct process.", Boolean.TRUE)
                    .buildResponse("result", customerService.getListByMM()).getResponse();
        }catch (BussinessExcepcion e) {
            response = new JsonManagerResponse(e.getMessage(), Boolean.FALSE).getResponse();
        }

        return response;
    }

    @GetMapping("/getInfoByYear")
    public Map<String, Object> getInfoByYear() {

        Map<String, Object> response;

        try {
            return new JsonManagerResponse("Correct process.", Boolean.TRUE)
                    .buildResponse("result", customerService.getListByYYYY()).getResponse();
        }catch (BussinessExcepcion e) {
            response = new JsonManagerResponse(e.getMessage(), Boolean.FALSE).getResponse();
        }

        return response;
    }

}
