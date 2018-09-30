import com.neuedu.Dao.IUserDao;
import com.neuedu.Dao.MybatisImpl.UserDaoImp;
import org.junit.jupiter.api.Test;

public class MyBatisTest {


    @Test
    public void findByUsername(){
        IUserDao userDao=new UserDaoImp();

        System.out.println(  userDao.findUserByUsername("huahua"));


    }

}
