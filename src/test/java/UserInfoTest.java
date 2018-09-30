import com.neuedu.Dao.IUserDao;
import com.neuedu.service.IUserService;
import org.junit.Before;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserInfoTest {
IUserDao userDao ;
IUserService userService;

     @Before
     public void before(){
         ApplicationContext context=new ClassPathXmlApplicationContext("spring.xml");
    //     System.out.println("context="+context);
         userDao= (IUserDao) context.getBean("userDaoImp");

         userService= (IUserService) context.getBean("userServiceImp");

     }


    @Test
    public void testGetUser(){


        System.out.println(userDao.getUser("huahua", "12313"));


    }


    @Test
    public  void testServiceLogin(){
        System.out.println(userService.login("guagua", "123"));


    }

}
