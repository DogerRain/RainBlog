package cn.yudianxx.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class QiniuyunConfig {
    @Value("${image.file.directory}")
    String uploadFileUrl;
    @Value("${qiniuyun.SecretKey}")
    String secretKey;
    @Value("${qiniuyun.AccessKey}")
    String accessKey;
    @Value("${qiniuyun.BucketName}")
    String bucketName;
    @Value("${qiniuyun.url}")
    String url;

}
