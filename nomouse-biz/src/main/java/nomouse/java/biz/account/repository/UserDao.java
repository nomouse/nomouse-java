package nomouse.java.biz.account.repository;

import nomouse.java.biz.account.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {

    public User findByUsername(String name);
}
