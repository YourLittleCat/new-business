import com.neuedu.Dao.ICategory;
import com.neuedu.Dao.impl.CategoryDaoImp;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Category {

    ICategory category=new CategoryDaoImp();


    @Test
    public void testFindProductByCategory(){


        System.out.println(category.findProductByCategoryId(4));


    }

     @Test
    public void testFindCategorySubByCategoryId(){
        System.out.println(category.findCategorySubByCategoryId(2));

    }

    @Test
    public void testAddCategory(){
        System.out.println(category.addCategory(1, "百草堂"));


    }

}
