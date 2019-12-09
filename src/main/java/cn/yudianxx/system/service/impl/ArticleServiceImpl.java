package cn.yudianxx.system.service.impl;

import cn.yudianxx.common.constants.CommonConstant;
import cn.yudianxx.common.exception.GlobalException;
import cn.yudianxx.common.utils.QueryPage;
import cn.yudianxx.system.entity.*;
import cn.yudianxx.system.entity.dto.ArchivesWithArticle;
import cn.yudianxx.system.mapper.ArticleMapper;
import cn.yudianxx.system.mapper.CategoryMapper;
import cn.yudianxx.system.mapper.TagMapper;
import cn.yudianxx.system.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @auther TyCoding
 * @date 2018/10/16
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, SysArticle> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public List<SysArticle> findAll() {
        LambdaQueryWrapper<SysArticle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(SysArticle::getId);
        queryWrapper.eq(SysArticle::getState, CommonConstant.DEFAULT_RELEASE_STATUS);
        IPage<SysArticle> page = new Page<>(0, 8);
        return articleMapper.selectPage(page, queryWrapper).getRecords();
    }

    @Override
    public IPage<SysArticle> list(SysArticle sysArticle, QueryPage queryPage) {
        IPage<SysArticle> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<SysArticle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(SysArticle::getId);
        queryWrapper.like(StringUtils.isNotBlank(sysArticle.getTitle()), SysArticle::getTitle, sysArticle.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(sysArticle.getAuthor()), SysArticle::getAuthor, sysArticle.getAuthor());
        IPage<SysArticle> selectPage = articleMapper.selectPage(page, queryWrapper);
        findInit(selectPage.getRecords());
        return selectPage;
    }

    /**
     * 封装文章分类、标签数据
     *
     * @param list
     */
    private void findInit(List<SysArticle> list) {
        if (!list.isEmpty()) {
            list.forEach(article -> {
                List<SysCategory> sysCategoryList = categoryService.findByArticleId(article.getId());
                if (sysCategoryList.size() > 0) {
                    article.setCategory(sysCategoryList.get(0).getName());
                }
                List<SysTag> tagList = tagService.findByArticleId(article.getId());
                List<String> stringList = new ArrayList<>();
                tagList.forEach(tags -> {
                    stringList.add(tags.getName());
                });
                article.setTags(tagList);
            });
        }
    }

    @Override
    public IPage<SysArticle> findByPageForSite(QueryPage queryPage) {
        IPage<SysArticle> page = new Page<>(queryPage.getPage(), queryPage.getLimit());
        LambdaQueryWrapper<SysArticle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(SysArticle::getId);
        queryWrapper.eq(SysArticle::getState, CommonConstant.DEFAULT_RELEASE_STATUS);
        return articleMapper.selectPage(page, queryWrapper);
    }

    @Override
    public SysArticle findById(Long id) {
        SysArticle sysArticle = articleMapper.selectById(id);
        List<SysArticle> sysArticleList = new ArrayList<>();
        sysArticleList.add(sysArticle);
        findInit(sysArticleList);
        return sysArticle;
    }

    @Override
    @Transactional
    public void add(SysArticle sysArticle) {
        if (sysArticle.getState() == null) {
            sysArticle.setState(CommonConstant.DEFAULT_DRAFT_STATUS);
        }
        if (sysArticle.getPublishTime() == null && sysArticle.getState() == "1") {
            sysArticle.setPublishTime(new Date());
        }
        sysArticle.setAuthor(((SysUser) SecurityUtils.getSubject().getPrincipal()).getUsername());
        sysArticle.setEditTime(new Date());
        sysArticle.setCreateTime(new Date());
        articleMapper.insert(sysArticle);
        sysArticle.setId(sysArticle.getId());
        this.updateArticleCategoryTags(sysArticle);
    }

    /**
     * 更新文章-分类-标签，三表间的关联
     *
     * @param sysArticle
     */
    private void updateArticleCategoryTags(SysArticle sysArticle) {
        if (sysArticle.getId() != 0) {
            if (sysArticle.getCategory() != null) {
                articleCategoryService.deleteByArticleId(sysArticle.getId());
                SysCategory sysCategory = categoryService.getById(sysArticle.getCategory());
                if (sysCategory != null) {
                    articleCategoryService.add(new ArticleCategory(sysArticle.getId(), sysCategory.getId()));
                }
            }
            if (sysArticle.getTags() != null && sysArticle.getTags().size() > 0) {
                articleTagService.deleteByArticleId(sysArticle.getId());
                sysArticle.getTags().forEach(tag -> {
                    articleTagService.add(new ArticleTag(sysArticle.getId(), tag.getId()));
                });
            }
        }
    }

    @Override
    @Transactional
    public void update(SysArticle sysArticle) {
        if (sysArticle.getPublishTime() == null && sysArticle.getState().equals("1")) {
            sysArticle.setPublishTime(new Date());
        }
        articleMapper.updateById(sysArticle);
        updateArticleCategoryTags(sysArticle);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (id != null && id != 0) {
            articleMapper.deleteById(id);
            //删除文章-分类表的关联
            articleCategoryService.deleteByArticleId(id);
            //删除文章-标签表的关联
            articleTagService.deleteByArticleId(id);
        }
    }

    @Override
    public List<SysArticle> findByCategory(String category) {
        LambdaQueryWrapper<SysArticle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(SysArticle::getCategory, category);
        return articleMapper.selectList(queryWrapper);
    }

    @Override
    public List<ArchivesWithArticle> findArchives() {
        List<ArchivesWithArticle> archivesWithArticleList = new ArrayList<ArchivesWithArticle>();
        try {
            List<String> dates = articleMapper.findArchivesDates();
            dates.forEach(date -> {
                List<SysArticle> sysArticleList = articleMapper.findArchivesByDate(date);
                ArchivesWithArticle archivesWithArticle = new ArchivesWithArticle(date, sysArticleList);
                archivesWithArticleList.add(archivesWithArticle);
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
        return archivesWithArticleList;
    }

    @Override
    public List<ArchivesWithArticle> findArchivesByTags(Long tagId) {
        List<ArchivesWithArticle> archivesWithArticleList = new ArrayList<ArchivesWithArticle>();
        try {
            //tags的名称
            List<SysTag> sysTag = tagMapper.selectList(new LambdaQueryWrapper<SysTag>().eq(SysTag::getId, tagId));
            sysTag.forEach(date -> {
                //把文章查出来，放到list里面
                List<SysArticle> sysArticleList = articleMapper.findArchivesByTags(date.getId());
                ArchivesWithArticle archivesWithArticle = new ArchivesWithArticle(date.getName(), sysArticleList);
                archivesWithArticleList.add(archivesWithArticle);
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
        return archivesWithArticleList;
    }

    @Override
    public List<ArchivesWithArticle> findArchivesByCategorys(Long categoryId) {
        List<ArchivesWithArticle> archivesWithArticleList = new ArrayList<ArchivesWithArticle>();
        try {
            //category的名称
            List<SysCategory> sysCategoryList = categoryMapper.selectList(new LambdaQueryWrapper<SysCategory>().eq(SysCategory::getId, categoryId));
            sysCategoryList.forEach(date -> {
                //把文章查出来，放到list里面
                List<SysArticle> sysArticleList = articleMapper.findArchivesByCategorys(date.getId());
                ArchivesWithArticle archivesWithArticle = new ArchivesWithArticle(date.getName(), sysArticleList);
                archivesWithArticleList.add(archivesWithArticle);
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
        return archivesWithArticleList;
    }

}
