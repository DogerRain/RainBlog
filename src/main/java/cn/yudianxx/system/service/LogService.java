package cn.yudianxx.system.service;

import cn.yudianxx.common.utils.QueryPage;
import cn.yudianxx.system.entity.SysLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

import java.io.UnsupportedEncodingException;

/**
 * @author tycoding
 * @date 2019-03-13
 */
public interface LogService extends IService<SysLog> {

    IPage<SysLog> list(SysLog log, QueryPage queryPage);

    void delete(Long id);

    @Async
    void saveLog(ProceedingJoinPoint proceedingJoinPoint, SysLog log) throws JsonProcessingException, UnsupportedEncodingException;
}
