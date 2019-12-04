package cn.yudianxx.admin.dto;

import net.coobird.thumbnailator.Thumbnails;
import org.junit.Test;

import java.io.IOException;

/**
 * @author huangyongwen
 * @date 2019/12/3
 * @Description
 */

public class FileDirtetoryTest {
    @Test
    public void test() throws IOException {
        System.out.println();
        Thumbnails.of("E:\\projet\\RainBlog\\target\\classes\\upload\\1575372585193.jpg")
                .scale(0.15f)
                .outputQuality(1f)
                .toFile("E:\\projet\\RainBlog\\target\\classes\\upload\\1575372585193_google.jpg");
    }
}
