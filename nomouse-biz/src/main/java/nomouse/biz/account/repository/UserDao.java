package nomouse.biz.account.repository;

import nomouse.biz.account.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {

    public User findByUsername(String name);
}
