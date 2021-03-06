package cn.yudianxx.system.mapper;

import cn.yudianxx.common.utils.QueryPage;
import cn.yudianxx.system.entity.SysComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huangyongwen
 * @date 2018/10/17
 */
public interface CommentMapper extends BaseMapper<SysComment> {

    List<SysComment> findAll(@Param("state") String state, @Param("queryPage") QueryPage queryPage);
}
