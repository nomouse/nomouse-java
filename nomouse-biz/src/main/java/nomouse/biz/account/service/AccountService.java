package nomouse.biz.account.service;

import nomouse.biz.account.entity.User;
import nomouse.biz.account.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private UserDao userDao;

    public User findUserByName(String name) {
        User result = userDao.findByUsername(name);
        return result;
    }


    public void reg() {

    }

}
