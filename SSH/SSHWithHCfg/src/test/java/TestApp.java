import com.ayy.bean.User;
import com.ayy.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 22/02/2021
 * @ Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestApp {
    @Autowired
    private UserService userService;

    @Test
    public void demo01(){
        User user = new User();
        user.setUname("USR1");
        user.setPwd("123456");
        user.setAge(18);

        userService.register(user);
    }
}
