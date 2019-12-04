package cn.yudianxx.system.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface CommonService {


    public String uploadFileToQiNiu(MultipartFile multipartFile, String key);

    public void returnDealFile(File localFile, String FilePath) throws IOException;


}
