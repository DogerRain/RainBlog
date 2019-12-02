package cn.yudianxx.admin.dto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author huangyongwen
 * @date 2019/11/28
 * @Description
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ThreadUploadTest {
    @Test
    public void Test() throws InterruptedException {
        long strat = System.currentTimeMillis();
        System.out.println(111);
        Thread.sleep(5*1001);
        System.out.println(555);
        long end = System.currentTimeMillis()-strat;
        System.out.println(String.format("用时：%s",end));
    }


    @Test
    public void test1()  {

        System.out.println(test2());
    }


    public  int test2() {
        int b = 20;

        try {
            System.out.println("try block");
//            b =b /0 ;
            return b;
//            throw new Exception("11");
//            return b;  //这里的return不能和抛出异常一起用，因为不会执行，编译报错
        } catch (Exception e) {
            b= 50;
            System.out.println("catch block");
            return b;
        } finally {
            b=100;
            System.out.println("finally block");
//            return b;
        }
//        return b;
    }
}
