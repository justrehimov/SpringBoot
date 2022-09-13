package az.spring.springboot.dao;

import az.spring.springboot.entity.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserDao {

    private final EntityManager entityManager;


    public List<User> list(String filter, Integer age, String address, Integer page, Integer size) {
        Session session = entityManager.unwrap(Session.class);
        String sql = "select u from User u";
        String where = "";
        Map<String, Object> parameters = new HashMap<>();

        if(!ObjectUtils.isEmpty(filter)){
            where += "and lower(u.firstName) like lower(:filter) or lower(u.lastName) like lower(:filter) ";
            parameters.put("filter", filter + "%");
        }if(!ObjectUtils.isEmpty(address)){
            where += "and lower(u.address.address)=lower(:address) ";
            parameters.put("address", address);
        }if(!ObjectUtils.isEmpty(age)){
            where += "and u.age=:age ";
            parameters.put("age", age);
        }

        if(StringUtils.hasText(where)){
            sql+= " where " + where.substring(3);
        }

        Query query = session.createQuery(sql);
         if(!parameters.isEmpty()){
             parameters.entrySet().forEach(entry->{
                 query.setParameter(entry.getKey(), entry.getValue());
             });
         }

         int firstResult = page * size;
         query.setFirstResult(firstResult);
         query.setMaxResults(size);
         List<User> userList = query.getResultList();

         return userList;
    }
}
