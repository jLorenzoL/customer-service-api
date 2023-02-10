package pe.com.indigitalxp.customerserviceapi.repository;

import com.google.gson.Gson;
import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pe.com.indigitalxp.customerserviceapi.dto.StatisticDto;
import pe.com.indigitalxp.customerserviceapi.entity.CollCustomer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

@Repository
public class CustomerRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<StatisticDto> getBornCustomByMM(){

        MongoCollection<Document> coll = mongoTemplate.getCollection("coll_customer");

        AggregateIterable<Document> result = coll.aggregate(
                Collections.singletonList(new Document("$group",
                        new Document("_id",new Document("$month", "$dtBornDate"))
                                .append("count", new Document("$sum", 1L)))));

        ArrayList<Document> dataResult = new ArrayList<>(1);

        result.forEach((Consumer<? super Document>) dataResult::add);

        return mapData(dataResult);
    }

    public List<StatisticDto> getBornCustomByYYYY(){

        MongoCollection<Document> coll = mongoTemplate.getCollection("coll_customer");

        AggregateIterable<Document> result = coll.aggregate(
                Collections.singletonList(new Document("$group",
                        new Document("_id",new Document("$year", "$dtBornDate"))
                                .append("count", new Document("$sum", 1L)))));

        ArrayList<Document> dataResult = new ArrayList<>(1);

        result.forEach((Consumer<? super Document>) dataResult::add);

        return mapData(dataResult);
    }

    private List<StatisticDto> mapData(List<Document> dataResult){

        List<StatisticDto> statisticDtoList = new ArrayList<>();

        dataResult.forEach(key -> {
            StatisticDto dto = new StatisticDto();
            dto.set_id(Integer.valueOf(key.get("_id").toString()));
            dto.setCount(Integer.valueOf(key.get("count").toString()));
            statisticDtoList.add(dto);
        });
        statisticDtoList.sort(Comparator.comparing(StatisticDto::get_id));

        return statisticDtoList;
    }

    public void saveProduct(CollCustomer collCustomer){
        mongoTemplate.save(collCustomer);
    }

    public List<CollCustomer> resultConsumers(String numDocument, String email){

        List<Criteria> lstCriteria = getCriteriaSearch(numDocument, email);
        if(!lstCriteria.isEmpty()){
            Criteria criteria = new Criteria().andOperator(lstCriteria.toArray(new Criteria[0]));
            Query query = new Query(criteria);
            return mongoTemplate.find(query, CollCustomer.class);
        }

        return mongoTemplate.findAll(CollCustomer.class);

    }

    public CollCustomer getCustomerByDoc(String document){

        Query query = new Query();
        query.addCriteria(Criteria.where("strDocument").is(document));

        return mongoTemplate.findOne(query, CollCustomer.class);
    }

    private List<Criteria> getCriteriaSearch(String numDocument, String email) {

        List<Criteria> criteriaList = new ArrayList<>();

        if(StringUtils.isNotBlank(numDocument)){
            criteriaList.add(Criteria.where("strDocument").is(numDocument));
        }
        if(StringUtils.isNotBlank(email)){
            criteriaList.add(Criteria.where("strMail").is(email));
        }

        return criteriaList;
    }

    public Page<CollCustomer> getCustomerPageable(Query query, Pageable pageable) {
        List<CollCustomer> list = getCustomer(query);

        if (list.isEmpty()) {
            return null;
        }

        long count = mongoTemplate.count(query, CollCustomer.class);
        Page<CollCustomer> resultPage = new PageImpl<>(list, pageable, count);
        return resultPage;
    }

    public List<CollCustomer> getCustomer(Query query) {
        return mongoTemplate.find(query, CollCustomer.class);
    }

}
