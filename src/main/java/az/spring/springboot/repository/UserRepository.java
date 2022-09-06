package az.spring.springboot.repository;

import az.spring.springboot.dto.response.UserAddressResponse;
import az.spring.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u.firstName from User u")
    List<String> findUserNames();

    @Query(value = "select u.last_name from users u", nativeQuery = true)
    List<String> findSurnames();

    @Query("select u.firstName from User u where u.firstName=:name")
    List<String> findUserNames(@Param("name") String name1);

    @Query(value = "select u.last_name from users u where u.last_name=?1",nativeQuery = true)
    List<String> findSurnames(String surname);
    List<User> findByAgeGreaterThan(Integer num);

    @Query("select new az.spring.springboot.dto.response.UserAddressResponse(u.firstName, u.lastName, a.address)" +
            " from User u inner join Address a on u.address.id=a.id")
    List<UserAddressResponse> getUserAddressResponse();
}
