package oldtest;


import com.neuedu.Dao.IUserDao;
import com.neuedu.Dao.MybatisImpl.UserDaoImp;
import com.neuedu.pojo.UserInfo;
import org.junit.jupiter.api.Test;

public class test {

    @Test
    public void testMybatisLogin(){
        IUserDao userDao=new UserDaoImp();
        System.out.println(userDao.getUser("wwwu1105", "qwe123"));

    }

   @Test
    public void testMybatisCheckName(){

          IUserDao userDao =new UserDaoImp();
        System.out.println(userDao.checkUsername("wwwu1102"));

    }

    @Test
    public void testMyBatisCheckEmail(){

        IUserDao userDao =new UserDaoImp();
        System.out.println( userDao.checkEmail("www7"));
    }

@Test
    public void testMyBatisRegister(){
        UserInfo userInfo=new UserInfo();
             userInfo.setUsername("wewe");
             userInfo.setPhone("12312341234");
             userInfo.setPassword("123123");
             userInfo.setQuestion("花花不是花花");
             userInfo.setRole(1);
             userInfo.setAnswer("狗不狗");
             userInfo.setEmail("89232@qq.com");

        IUserDao userDao =new UserDaoImp();
        System.out.println( userDao.register(userInfo));


    }


@Test
          public void testMyBatisFindAll() {
    IUserDao userDao = new UserDaoImp();

    System.out.println(userDao.findAll());


}

@Test

        public void testFindAllByUsername(){
        IUserDao userDao =new UserDaoImp();
    System.out.println(userDao.findAllByUsername(null));


        }



        @Test
          public void testFindAllByOption(){

        IUserDao userDao=new UserDaoImp();
        UserInfo userInfo =new UserInfo();
        userInfo.setId(12349);

            System.out.println(userDao.findAllByOption(userInfo));



          }


@Test
          public void testUpdateByOption(){

        IUserDao userDao =new UserDaoImp();
        UserInfo userInfo=new UserInfo();
        userInfo.setUsername("zouyizou");
        userInfo.setId(12312);
    System.out.println(userDao.updateByOption(userInfo));
          }







}
