package cn.yudianxx.admin.dto;

import org.junit.Test;

import java.io.IOException;

/**
 * @author huangyongwen
 * @date 2019/12/2
 * @Description
 */
public class TryCatchTest {
    //完了完了我就说最近的头发怎么掉的有点多~
//    昨晚夜深的时候，楼下断断续续传来砰砰砰的诡异声音。
//      辗转反侧，做了个梦：
//      梦里我和我自己说 写个代码，用While(true)循环对声音做个监听吧，响一次就计数，然后放到Redis里面。
//        早上醒来，Redis里面的值是51。
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
        int count=0;
        while (true) {

            try {
                System.out.println("返回值：" + count()); //
                return 1;//成功返回
            } catch (IOException e){
                System.out.println("catch了count()方法返回的异常1");
                throw e;
            } catch (Exception e) {
                System.out.println("catch了count()方法返回的异常2");
                throw e;
            } finally {
                count++;
                System.out.println(String.format("第%s次尝试了：",count));
            }
        }
    }

    public int count() throws Exception {
        int a = 0;
        try {
            a = 1 / 0;
        } catch (Exception e) {

            throw new IllegalAccessException("计算异常了");
        } finally {
//            System.out.println("1");
//            return a ; //finall最后会覆盖catch的返回throw，导致上层调用没有捕捉到异常
        }
        return a;
    }
}
