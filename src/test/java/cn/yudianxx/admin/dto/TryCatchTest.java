package cn.yudianxx.admin.dto;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

/**
 * @author huangyongwen
 * @date 2019/12/2
 * @Description
 */
@Slf4j
public class TryCatchTest {
    @Test
    public void test() throws Exception {
        try {
            int a = test1();
            System.out.println("a的值：" + a);
        } catch (Exception e) {
            System.out.println("catch了test1()方法返回的异常");
            throw e;
        }
    }

    public int test1() throws Exception {
        int count = 0;
        while (true) {
            try {
                System.out.println("返回值：" + count()); //
                return count;
            } catch (IOException e) {
                System.out.println("catch了count()方法返回的异常1"); //捕捉到try的错误，而调用count()方法返回异常也会捕捉，再往上抛给调用test1()方法的父方法
                throw e;
            } catch (Exception e) {
                System.out.println("catch了count()方法返回的异常2");
                throw e;
            } finally {
                count++;
                System.out.println(String.format("第%s次尝试了：", count));
            }
        }

    }

    public int count() throws Exception {
        int a = 0;
        try {
            a = 1 / 0;
        } catch (Exception e) {
            log.error("count()方法的计算异常了：{}", e);
            e.printStackTrace();
            throw new IllegalAccessException("计算异常了"); //Java抛出异常会直接返回，交给上一级方法处理
        } finally {
//            System.out.println("1");
//            return a ; //finall最后会覆盖catch的返回throw，导致上层调用没有捕捉到异常
        }
        return a;
    }
}
