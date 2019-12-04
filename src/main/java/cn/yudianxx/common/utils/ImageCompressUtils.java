package cn.yudianxx.common.utils;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

public class ImageCompressUtils {
    
    /**
     * @Author: Administrator
     * @Date: 2019/12/5  0:02 
     * @Description: 
     * @Param: 文件名，文件路径
     * @return: 
     */
    public static void googleImageCompress(File localFile, String FilePath) throws IOException {
        Long  size = localFile.length() / 1024 / 1024;
        //文件大于5M默认给压缩到原来的1.5倍
        if (size>= 5) {
            Thumbnails.of(FilePath)
                    .scale(ConstantValueUtils.SCALE)
                    .outputQuality(ConstantValueUtils.OUTPUTQUALITY)
                    .toFile(FilePath);
        }else if (size>2){
            Thumbnails.of(FilePath)
                    .scale(ConstantValueUtils.POINT_FIVEE_SCALE)
                    .outputQuality(ConstantValueUtils.OUTPUTQUALITY)
                    .toFile(FilePath);
        }
    }

}
