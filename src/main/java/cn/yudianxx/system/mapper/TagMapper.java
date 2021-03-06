package cn.yudianxx.system.mapper;

import cn.yudianxx.system.entity.SysTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author huangyongwen
 * @date 2018/10/18
 */
public interface TagMapper extends BaseMapper<SysTag> {

    List<SysTag> findByArticleId(long id);
}
