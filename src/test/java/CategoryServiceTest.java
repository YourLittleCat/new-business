import com.neuedu.Dao.ICategory;
import com.neuedu.Dao.impl.CategoryDaoImp;
import com.neuedu.service.ICategoryService;
import com.neuedu.service.impl.CategoryServiceImp;
import org.junit.jupiter.api.Test;

public class CategoryServiceTest {

    @Test
    public void testForSpring(){
        ICategoryService iCategoryService=new CategoryServiceImp();
        System.out.println(  iCategoryService.findCategorySubByCategoryId(1));

    }

    @Test
    public void testAddCategory(){
        ICategoryService iCategoryService=new CategoryServiceImp();
        System.out.println(   iCategoryService.addCategory(2, "花花草草"));


    }

@Test
    public void testFindCategoryByName(){

     ICategoryService categoryService=new CategoryServiceImp();
    System.out.println(categoryService.findCategoryByName("化妆品"));

    }


}
