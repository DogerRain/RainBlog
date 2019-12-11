package cn.yudianxx.admin.dto;

import cn.yudianxx.common.config.QiniuyunConfig;
import cn.yudianxx.common.properties.TumoProperties;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author huangyongwen
 * @date 2019/11/26
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QiniuyunUploadTest {


    @Autowired
    TumoProperties tumoProperties;

    @Autowired
    QiniuyunConfig config;

    private UploadManager uploadManager = new UploadManager(new Configuration());

//    String localFilePath = "F:\\qiniu\\unnamed.jpg";
    String localFilePath = "F:\\image.png";

    @Test
    public void test() {
        upload(localFilePath);
//        download();
//        System.out.println(config.getBucketName());
//        System.out.println(config.getUrl());
    }

    public String upload(String file) {
//        String accessKey = tumoProperties.getQiniu().getAk();
//        String secretKey = tumoProperties.getQiniu().getSk();
//        String bucket = tumoProperties.getQiniu().getBn();
        String accessKey = config.getAccessKey();
        String secretKey = config.getSecretKey();
        String bucket = config.getBucketName();
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png

        String key = "";
        Auth auth = Auth.create(accessKey, secretKey);

        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null;
    }

    void download(){

    }
}
