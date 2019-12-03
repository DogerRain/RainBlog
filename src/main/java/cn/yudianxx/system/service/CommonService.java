package cn.yudianxx.system.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface CommonService {


    public String uploadFileToQiNiu(MultipartFile multipartFile, String key);


}
