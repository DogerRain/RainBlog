package cn.yudianxx.system.response;

import lombok.Data;

/**
 * @author huangyongwen
 * @date 2019/11/27
 * @Description
 */
@Data
public class ResponseResult {
    int code;
    String message;
    String url;
}
