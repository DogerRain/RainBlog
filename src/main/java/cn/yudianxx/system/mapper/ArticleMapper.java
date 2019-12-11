package cn.yudianxx.system.mapper;

import cn.yudianxx.system.entity.SysArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author huangyongwen
 * @date 2018/10/16
 */
public interface ArticleMapper extends BaseMapper<SysArticle> {

    List<String> findArchivesDates();

    List<SysArticle> findArchivesByDate(String date);

    List<SysArticle> findArchivesByTags(Long tagId);

    List<SysArticle> findArchivesByCategorys(Long categoryId);


}
