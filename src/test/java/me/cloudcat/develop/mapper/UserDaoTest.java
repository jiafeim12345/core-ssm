package me.cloudcat.develop.mapper;

import me.cloudcat.develop.dao.RoleDao;
import me.cloudcat.develop.dao.UserDao;
import me.cloudcat.develop.entity.Role;
import me.cloudcat.develop.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Field;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml",
        "classpath:spring-redis.xml", "classpath:spring-rabbitmq.xml", "classpath:spring-security.xml"})
public class UserDaoTest {

	@Autowired
	UserDao userDao;

    @Autowired
    RoleDao roleDao;

	@Test
	public void testGet() {
        List<User> all = userDao.findAll();
        all.forEach(e -> {
            Field[] fields = e.getClass().getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    System.out.println(field.getName() + ":  " + field.get(e));
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    @Test
    public void testOne() {
        List<Role> all = roleDao.findAll();
        System.out.println(all.size());
    }
}
