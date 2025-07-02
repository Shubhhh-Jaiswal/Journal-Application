package net.engineeringdigest.journalApp.Repository;

import net.engineeringdigest.journalApp.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserWithEmailAndSA(){
        Query query= new Query();
        Criteria criteria= new Criteria();

//        query.addCriteria(Criteria.where("email").exists(true));
//        query.addCriteria(Criteria.where("email").ne(""));
//        query.addCriteria(Criteria.where("sentiment").is(true));
        query.addCriteria(criteria.andOperator(Criteria.where("email").ne(""), Criteria.where("sentiment").is(true)));


       List<User> all = mongoTemplate.find(query, User.class);
       return all;
    }


}
