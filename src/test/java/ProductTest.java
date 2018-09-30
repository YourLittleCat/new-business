import com.neuedu.Dao.IProduct;
import com.neuedu.Dao.impl.ProductDaoImp;
import com.neuedu.pojo.Product;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import javax.swing.*;
import java.math.BigDecimal;

public class ProductTest {
    IProduct product;


    @Before
    public void befor(){
        ApplicationContext context=new ClassPathXmlApplicationContext("spring.xml");
     product= (IProduct) context.getBean("productDaoImp");


    }


    @Test
    public void testFindUserById(){
      product=new ProductDaoImp();

        System.out.println( product.findProductById(101));

    }


    @Test
    public  void  testAddProduct(){
         product=new ProductDaoImp();
        Product product1=new Product();
        product1.setCategory_id(2);
        product1.setName("xcx");
        product1.setSubtitle("zxz");
        product1.setStock(212);
        product1.setPrice(BigDecimal.valueOf(212));

        System.out.println( product.addProduct(product1));



    }

}
