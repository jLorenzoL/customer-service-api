package pe.com.indigitalxp.customerserviceapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.com.indigitalxp.customerserviceapi.dto.CustomerDto;
import pe.com.indigitalxp.customerserviceapi.excepcion.BussinessExcepcion;
import pe.com.indigitalxp.customerserviceapi.service.CustomerService;
import pe.com.indigitalxp.customerserviceapi.util.JsonManagerResponse;

import java.util.Map;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/saveProduct")
    public Map<String, Object> saveProduct(@RequestBody CustomerDto customerDto){

        Map<String, Object> response = null;
        try {
            customerService.saveCustomer(customerDto);
            response = JsonManagerResponse.correctProcess().getResponse();
        }catch (Exception e) {
            response = new JsonManagerResponse(e.getMessage(), Boolean.FALSE).getResponse();
        }
        return response;
    }



    @GetMapping("/search")
    public Map<String, Object> getCustomers(@RequestParam(required = false) String document,
                                            @RequestParam(required = false) String email){

        Map<String, Object> response = null;
        try {
            return new JsonManagerResponse("Correct process.", Boolean.TRUE)
                    .buildResponse("result", customerService.listCustomers(document, email)).getResponse();
        }catch (BussinessExcepcion e) {
            response = new JsonManagerResponse(e.getMessage(), Boolean.FALSE).getResponse();
        }
        return response;
    }

    @GetMapping("/getInfoByMonth")
    public Map<String, Object> getInfoByMonth(){

        Map<String, Object> response = null;
        try {
            return new JsonManagerResponse("Correct process.", Boolean.TRUE)
                    .buildResponse("result", customerService.getListByMM()).getResponse();
        }catch (BussinessExcepcion e) {
            response = new JsonManagerResponse(e.getMessage(), Boolean.FALSE).getResponse();
        }
        return response;
    }

    @GetMapping("/getInfoByYear")
    public Map<String, Object> getInfoByYear(){

        Map<String, Object> response = null;
        try {
            return new JsonManagerResponse("Correct process.", Boolean.TRUE)
                    .buildResponse("result", customerService.getListByYYYY()).getResponse();
        }catch (BussinessExcepcion e) {
            response = new JsonManagerResponse(e.getMessage(), Boolean.FALSE).getResponse();
        }
        return response;
    }

}
