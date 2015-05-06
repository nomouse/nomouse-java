package nomouse.demo.repository;

import nomouse.demo.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {

    public User findByUsername(String name);
}
