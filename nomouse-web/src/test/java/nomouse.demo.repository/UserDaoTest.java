package nomouse.java.biz.account.repository;

import nomouse.biz.account.entity.User;
import nomouse.biz.account.repository.UserDao;
import nomouse.util.time.TimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/applicationContext.xml",
        "classpath:META-INF/applicationContext-ext.xml"})
@ActiveProfiles("dev")
public class UserDaoTest {

    @Autowired
    UserDao userDao;

    @Test
    public void save() {
        User user = new User();
        user.setUsername("wuch");
        user.setNickname("武昌");
        user.setAvatar("http://my.jpg");
        user.setCreated(TimeUtils.getTimestamp());
        user.setUpdated(TimeUtils.getTimestamp());
        userDao.save(user);
    }

    @Test
    public void find() {
    }

    @Test
    public void update() {

    }
}
