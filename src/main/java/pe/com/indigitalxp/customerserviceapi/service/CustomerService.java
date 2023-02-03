package pe.com.indigitalxp.customerserviceapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.indigitalxp.customerserviceapi.dto.CustomerDto;
import pe.com.indigitalxp.customerserviceapi.dto.StatisticDto;
import pe.com.indigitalxp.customerserviceapi.excepcion.BussinessExcepcion;
import pe.com.indigitalxp.customerserviceapi.mapper.CustomerMapper;
import pe.com.indigitalxp.customerserviceapi.repository.CustomerRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class CustomerService {

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    CustomerRepository customerRepository;

    public List<StatisticDto> getListByMM(){
        return customerRepository.getBornCustomByMM();
    }

    public List<StatisticDto> getListByYYYY(){
        return customerRepository.getBornCustomByYYYY();
    }

    @Transactional
    public void saveCustomer(CustomerDto customerDto){

        /* Verify if customer exists */
        CustomerDto customDb = customerMapper.customDtoToEntity(customerRepository.getCustomerByDoc(customerDto.getDocumentCustomer()));
        if(!Objects.isNull(customDb)){
            throw new BussinessExcepcion("The document is already registered, try with another document");
        }
        customerDto.setCreationCustomer(new Date());
        customerRepository.saveProduct(customerMapper.customDtoToEntity(customerDto));

    }

    public List<CustomerDto> listCustomers(String document, String mail){
        return customerMapper.map(customerRepository.resultConsumers(document, mail));
    }
}
