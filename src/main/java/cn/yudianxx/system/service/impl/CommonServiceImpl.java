package cn.yudianxx.system.service.impl;

import cn.yudianxx.common.properties.QiniuProperties;
import cn.yudianxx.common.properties.TumoProperties;
import cn.yudianxx.system.service.CommonService;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.util.Map;

@Service
@Slf4j
public class CommonServiceImpl implements CommonService {


    @Autowired
    TumoProperties tumoProperties;

    @Override
    //异步上传
//    @Async
    public String uploadFileToQiNiu(MultipartFile multipartFile, String key) {
        if (multipartFile.isEmpty()){
            return "error";
        }
        String URL ="";
        QiniuProperties qiniu = tumoProperties.getQiniu();
        String FilePath = "";
        try{
            File path = new File(URLDecoder.decode(ResourceUtils.getURL("classpath:").getPath(), "UTF-8"));
            File filePath = new File(path.getAbsolutePath(), "upload/");
            if (!filePath.exists() && !filePath.isDirectory()) {
                log.info("目录不存在，创建目录===========>" + filePath);
                filePath.mkdir();
            }
            File localFile = new File(filePath, key);
            multipartFile.transferTo(localFile); //写入磁盘
            //压缩文件,覆盖源文件
            FilePath = filePath + "/" +"deal_"+ key;
            returnDealFile(localFile,FilePath);
            log.info("文件原始路径=========>" + filePath);
            log.info("新文件名称===========>" + key);


            //密钥配置
            Auth auth = Auth.create(qiniu.getAk(), qiniu.getSk());
            String upToken = auth.uploadToken(qiniu.getBn());
            UploadManager uploadManager = new UploadManager(new Configuration());
            Response res = uploadManager.put(FilePath, key, upToken);;
            URL = qiniu.getUrl() + key;
        }catch (Exception e){
            System.out.println("报错了");
        }
        return URL;
    }
    /**
     * 获取图片宽度和高度
     *
     * @param file 图片路径
     * @return 返回图片的宽度
     */
    public static int[] getImgWidthHeight(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int result[] = { 0, 0 };
        try {
            // 获得文件输入流
            is = new FileInputStream(file);
            // 从流里将图片写入缓冲图片区
            src = ImageIO.read(is);
            // 得到源图片宽
            result[0] =src.getWidth(null);
            // 得到源图片高
            result[1] =src.getHeight(null);
            is.close();  //关闭输入流
        } catch (Exception ef) {
            ef.printStackTrace();
        }
        return result;
    }

    public void returnDealFile(File localFile,String sourcePath) throws IOException {
        int[] results = getImgWidthHeight(localFile);
        int widthdist = results[0];
        int heightdist = results[1];
        Image src = ImageIO.read(localFile);
        // 构造一个类型为预定义图像类型之一的 BufferedImage
        BufferedImage tag = new BufferedImage( widthdist,  heightdist, BufferedImage.TYPE_INT_RGB);

        // 这边是压缩的模式设置
        tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist, Image.SCALE_SMOOTH), 0, 0, null);

        //创建文件输出流
        FileOutputStream out = new FileOutputStream(sourcePath);
        //将图片按JPEG压缩，保存到out中
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        encoder.encode(tag);
        //关闭文件输出流
        out.close();
    }

    public static void main(String[] args) throws IOException {
        CommonServiceImpl c = new CommonServiceImpl();
        c.returnDealFile(new File("E:\\projet\\RainBlog\\target\\classes\\upload\\1575367246378.jpg"),"E:\\projet\\RainBlog\\target\\classes\\upload\\1575367246378.jpg");
    }

}
