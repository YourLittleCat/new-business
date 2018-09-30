package oldtest;


import com.neuedu.Dao.IUserDao;
import com.neuedu.Dao.MybatisImpl.UserDaoImp;
import com.neuedu.cache.TokenCache;
import org.junit.jupiter.api.Test;


public class DaoTest {



  IUserDao userDao=new UserDaoImp();



@Test
  public void testLogin(){

    userDao.getUser("wwwu1102", "12345");

  System.out.println(userDao);
  }


@Test
  public  void updateTokenByIdTest(){


   userDao.updateTokenById(12312, "123qwe");


}


@Test
  public void findUserByTokenTest(){
  userDao.findByToken("123qwe");
  System.out.println( userDao.findByToken("123qwe"));
}



@Test

  public void checkUsername(){
  System.out.println( userDao.checkUsername("wwwu1102"));


}

@Test

  public void checkEmail(){

  System.out.println(userDao.checkEmail("www7"));


}


@Test
  public  void findQuestionByUsername(){
  System.out.println(  userDao.findQuestionByUsername("00000"));


}

@Test
public void checkAnswer(){
  System.out.println( userDao.checkAnswer("howoldareyou", "qwe", "wwwu1102"));



}

@Test
public void cacheTest(){
  TokenCache.setCacheloader("hh","试试~" );
  System.out.println(TokenCache.getCacheloader("hh1"));

}


@Test
public void updatePasswordTest(){
  System.out.println( userDao.updatePassword("wwwu1105", "qwe123"));


}

}
