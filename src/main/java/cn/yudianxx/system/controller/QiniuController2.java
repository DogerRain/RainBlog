package cn.yudianxx.system.controller;

import cn.yudianxx.common.constants.enums.CommonEnum;
import cn.yudianxx.common.exception.GlobalException;
import cn.yudianxx.common.properties.QiniuProperties;
import cn.yudianxx.common.properties.TumoProperties;
import cn.yudianxx.common.utils.R;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huangyongwen
 * @date 2019/12/3
 * @Description
 */
@RestController
@RequestMapping("/api/storage/qiniu")
public class QiniuController2 {

    @Autowired
    private TumoProperties tumoProperties;
    private String FilePath;


    @RequestMapping("/uploadTest")
    public void test(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String accessKey = tumoProperties.getQiniu().getAk();
        String secretKey = tumoProperties.getQiniu().getSk();
        String bucket = tumoProperties.getQiniu().getBn();
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "F:\\qiniu\\1575031365174.jpg";
        Auth auth = Auth.create(accessKey, secretKey);

        String upToken = auth.uploadToken(bucket);

        String key = new Date().getTime() + "";
        //写入文件
        try {
            File path = new File(URLDecoder.decode(ResourceUtils.getURL("classpath:").getPath(), "UTF-8"));
            File filePath = new File(path.getAbsolutePath(), "upload/");
            if (!filePath.exists() && !filePath.isDirectory()) {
                filePath.mkdir();
            }
            String filename = file.getOriginalFilename(); //获取原始文件名称
            key += filename.substring(filename.lastIndexOf(".")); //获取文件类型
            File localFile = new File(filePath, key);
            file.transferTo(localFile); //写入磁盘
            FilePath = filePath + "/" + key;
            Thread.sleep(200);
            UploadManager uploadManager = new UploadManager(new Configuration());
            Response response = uploadManager.put(FilePath, key, upToken);
//            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            System.out.println(response);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            if (localFile.exists()) {
                localFile.delete(); //删除本地缓存的文件
            }
        } catch (QiniuException e) {
            System.out.println("IO异常？");
        } catch (Exception e) {

        } finally{


    }
}
}
