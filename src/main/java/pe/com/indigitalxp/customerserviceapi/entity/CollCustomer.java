package pe.com.indigitalxp.customerserviceapi.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "coll_customer")
public class CollCustomer {

    @Id
    @Indexed
    private String strId;

    private String strName;

    private String strLastname;

    private String strMail;

    private String strDocument;

    private Date dtCreationDate;

    private Date dtBornDate;

    private String state;

}
