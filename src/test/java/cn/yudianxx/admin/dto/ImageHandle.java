package cn.yudianxx.admin.dto;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.tencentcloudapi.common.JsonResponseModel;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ticm.v20181127.models.ImageModerationRequest;
import com.tencentcloudapi.ticm.v20181127.models.ImageModerationResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * @author huangyongwen
 * @date 2019/12/19
 * @Description
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ImageHandle {
    @Test
    public void handle() {
//        go("E:\\projet\\springBootLogback");
        uploadFileToCloud("E:\\projet\\springBootLogback\\logo.png");
    }

    public void go(String fileSrc) {
        File f1 = new File(fileSrc);
        File[] fs1 = f1.listFiles();//列出所有文件和子目录
        for (File file : fs1) {
            if (file.isDirectory()) {
                go(file + "");
            }
            if (file.isFile()) {
//                文件名称
                String fileName = file.getName();
                //                集合
                String colletion = file.getParent().substring((file.getParent().lastIndexOf("\\") + 1));
//                名字
                String modelName = file.getParentFile().getParent().substring((file.getParentFile().getParent().lastIndexOf("\\") + 1));

                //腾讯云鉴别
                ImageModerationResponse imageModerationResponse = new ImageModerationResponse();
                ImageModerationRequest imageModerationRequest = new ImageModerationRequest();


                //上传到腾讯云
                String url = uploadFileToCloud (fileSrc);
                if (StringUtils.isNotBlank(url)){
//                    写库

                }

            }
        }
    }

    public String uploadFileToCloud(String file) {
        String secretId = "AKIDfFfPtqiBUaTBoA5Cva2av97pIacUl0qS";
        String secretKey = "b2Lgz9LAwdClEj3CF7tTo8E5FRSHrWx8";
        try {
            COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
            Region region = new Region("ap-guangzhou");
            ClientConfig clientConfig = new ClientConfig(region);
            COSClient cosClient = new COSClient(cred, clientConfig);
            File uploadFile = new File(file);
            String bucketName = "images-1253198264";
            String key = new Date().getTime() + "";
            key += file.substring(file.lastIndexOf("."));
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, uploadFile);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            System.out.println("putObjectResult+");
//            腾讯云外链
            String url= "https://images-1253198264.cos.ap-guangzhou.myqcloud.com/";
            url = url   + key;
            return url;
        } catch (CosServiceException serverException) {
            serverException.printStackTrace();
        } catch (CosClientException clientException) {
            clientException.printStackTrace();
        }
        return null;
    }



}

//    CREATE TABLE `t_images` (
//        `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
//        `title` varchar(400) DEFAULT NULL COMMENT '标题',
//        `image_link` varchar(400) DEFAULT NULL COMMENT '图片链接',
//        `content` mediumtext COMMENT '内容',
//        `name` varchar(100) DEFAULT NULL COMMENT '名字',
//        `collection` varchar(100) DEFAULT NULL COMMENT '集合',
//        `category` varchar(120) DEFAULT NULL COMMENT '分类',
//        `state` varchar(100) NOT NULL COMMENT '状态',
//        `edit_time` datetime NOT NULL COMMENT '上次修改时间',
//        `create_time` datetime NOT NULL COMMENT '创建时间',
//        PRIMARY KEY (`id`) USING BTREE
//        ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='图片';
//
//
//
//        CREATE TABLE `t_category` (
//        `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
//        `name` varchar(400) DEFAULT NULL COMMENT '分类名称',
//        PRIMARY KEY (`id`) USING BTREE
//        ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='文章表';