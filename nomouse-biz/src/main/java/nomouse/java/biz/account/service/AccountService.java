package nomouse.java.biz.account.service;

import nomouse.java.biz.account.entity.User;
import nomouse.java.biz.account.repository.UserDao;
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
