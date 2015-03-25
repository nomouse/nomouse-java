package nomouse.core.repository;

import nomouse.core.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {
}
