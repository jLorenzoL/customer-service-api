package pe.com.indigitalxp.customerserviceapi.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.indigitalxp.customerserviceapi.dto.CustomerDto;
import pe.com.indigitalxp.customerserviceapi.dto.CustomerRequest;
import pe.com.indigitalxp.customerserviceapi.dto.CustomerRequestFilter;
import pe.com.indigitalxp.customerserviceapi.dto.StatisticDto;
import pe.com.indigitalxp.customerserviceapi.entity.CollCustomer;
import pe.com.indigitalxp.customerserviceapi.excepcion.BussinessExcepcion;
import pe.com.indigitalxp.customerserviceapi.mapper.CustomerMapper;
import pe.com.indigitalxp.customerserviceapi.repository.CustomerRepository;

import java.util.*;

@Service
@Slf4j
public class CustomerService {

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    CustomerRepository customerRepository;

    public List<StatisticDto> getListByMM() {
        return customerRepository.getBornCustomByMM();
    }

    public List<StatisticDto> getListByYYYY() {
        return customerRepository.getBornCustomByYYYY();
    }

    @Transactional
    public void saveCustomer(CustomerDto customerDto) {

        log.info("Inicio metodo saveCustomer()");
        CustomerDto customDb = customerMapper.customDtoToEntity(customerRepository.getCustomerByDoc(customerDto.getDocumentCustomer()));
        if(!Objects.isNull(customDb)){
            log.info("Cliente registrado en bd");
            throw new BussinessExcepcion("The document is already registered, try with another document");
        }

        customerDto.setCreationCustomer(new Date());
        customerDto.setState("1");
        customerRepository.saveProduct(customerMapper.customDtoToEntity(customerDto));
        log.info("Fin metodo saveCustomer()");
    }

    public List<CustomerDto> listCustomers(String document, String mail){
        return customerMapper.map(customerRepository.resultConsumers(document, mail));
    }

    public Map<String, Object> listCustomersPage(CustomerRequest request){

        Map<String, Object> map = new HashMap<>();

        try {
            List<Criteria> lstCriteria = getCriteriaConsultaCpe(request.getCustomerFilter());

            Criteria criteria = new Criteria().andOperator(lstCriteria.toArray(new Criteria[lstCriteria.size()]));
            Sort sort = new Sort(Sort.Direction.DESC, Collections.singletonList("dtCreationDate"));

            Pageable pageable = PageRequest.of(request.getPage(), request.getResults(), sort);
            Query query = new Query(criteria).with(pageable);

            List<CustomerDto> customerList = new ArrayList<>();

            if (request.isPageable()) {
                Page<CollCustomer> customerPage = customerRepository.getCustomerPageable(query, pageable);

                Long elementos = 0L;
                if (customerPage != null) {
                    customerList = customerMapper.map(customerPage.getContent());
                    elementos = customerPage.getTotalElements();
                    map.put("totalPages", customerPage.getTotalPages());
                }
                map.put("totalElements", elementos);
            } else {
                customerList = customerMapper.map(customerRepository.getCustomer(query));
            }
            map.put("customers", customerList);
        } catch (Exception e){
            throw new BussinessExcepcion(e.getMessage());
        }

        return map;

    }

    private List<Criteria> getCriteriaConsultaCpe(CustomerRequestFilter filtro) {

        List<Criteria> criteriaList = new ArrayList<>();

        if(StringUtils.isNotBlank(filtro.getDocumentNumber())){
            criteriaList.add(Criteria.where("strDocument").is(filtro.getDocumentNumber()));
        }
        if(StringUtils.isNotBlank(filtro.getEmail())){
            criteriaList.add(Criteria.where("strMail").is(filtro.getEmail()));
        }
        criteriaList.add(Criteria.where("state").is("1"));
        return criteriaList;

    }

}
