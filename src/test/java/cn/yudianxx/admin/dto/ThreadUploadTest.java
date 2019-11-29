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
@RunWith(SpringRunner.class)
@SpringBootTest
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
}
