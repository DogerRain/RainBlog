package cn.yudianxx.system.controller;

import cn.yudianxx.common.annotation.Log;
import cn.yudianxx.common.controller.BaseController;
import cn.yudianxx.common.exception.GlobalException;
import cn.yudianxx.common.properties.TumoProperties;
import cn.yudianxx.common.utils.ConstantValueUtils;
import cn.yudianxx.common.utils.QueryPage;
import cn.yudianxx.common.utils.R;
import cn.yudianxx.system.entity.SysArticle;
import cn.yudianxx.system.entity.SysTag;
import cn.yudianxx.system.entity.dto.ArchivesWithArticle;
import cn.yudianxx.system.service.ArticleService;
import cn.yudianxx.system.service.ArticleTagService;
import cn.yudianxx.system.service.CommonService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author huangyongwen
 * @date 2018/10/16
 */
@RestController
@RequestMapping("/api/article")
@Api(value = "ArticleController", tags = {"文章管理接口"})
@Slf4j
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    QiNiuController qiNiuController;

//    @Autowired
//    CommonService commonService;
    @Autowired
    TumoProperties tumoProperties;

    /**
     * 查询文章总数量
     *
     * @return
     */
    @GetMapping("/count")
    public R<Integer> findAllCount() {
        return new R<>(articleService.count(new QueryWrapper<SysArticle>()));
    }

    /**
     * 分页查询
     *
     * @param queryPage
     * @param sysArticle
     * @return
     */
    @GetMapping("/list")
    public R<Map<String, Object>> findByPage(SysArticle sysArticle, QueryPage queryPage) {
        return new R<>(super.getData(articleService.list(sysArticle, queryPage)));
    }

    @GetMapping("{id}")
    public R<SysArticle> findById(@PathVariable Long id) {
        return new R<>(articleService.findById(id));
    }

    /**
     * 查询指定ArticleId的Tags数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}/tags")
    public R<List<String>> findTags(@PathVariable Long id) {
        List<String> list = new ArrayList<String>();
        List<SysTag> tagList = articleTagService.findByArticleId(id);
        for (SysTag t : tagList) {
            list.add(t.getName());
        }
        return new R<>(list);
    }

    /**
     * 查询所有的Archives
     *
     * @return
     */
    @GetMapping(value = "/archives")
    public R<List<ArchivesWithArticle>> findArchives() {
        return new R<>(articleService.findArchives());
    }

    @PostMapping
    @Log("新增文章")
    public R save(@RequestBody SysArticle sysArticle) {
        try {
            articleService.add(sysArticle);
            return new R();
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @PutMapping
    @Log("更新文章")
    public R update(@RequestBody SysArticle sysArticle) {
        try {
            articleService.update(sysArticle);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Log("删除文章")
    public R delete(@PathVariable Long id) {
        try {
            articleService.delete(id);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }


    @RequestMapping(value = "/uploadFile")
    public JSONObject uploadFile(@RequestParam(value = "editormd-image-file", required = false) MultipartFile file, HttpServletRequest request) {
        JSONObject result = new JSONObject();
        try {
            //丢到七牛云
            R<Map> r = qiNiuController.upload(file, request);
            result.put("success", 1);
            result.put("message", "success");
            result.put("url", ConstantValueUtils.HTTP + r.getData().get("url"));
            return result;
        } catch (Exception e) {
            result.put("success", 1);
            result.put("message", "fail");
            log.error("图片上传出错", e);
        }
        return result;
    }

    @RequestMapping("editormdPic")
    @ResponseBody
    public JSONObject editormdPic(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file1, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String url = "G:\\源码\\Vue+Springboot\\src\\main\\resources\\static\\admin\\blog_image\\admin\\blog_image";
        System.out.println(file1.getOriginalFilename());
        File file = new File(url, file1.getOriginalFilename());
        if (!file.exists() && file.mkdirs()) {
        }
        JSONObject resultJs = new JSONObject();

        /*resultJs.put("success", "1");此处不要写 字符串的"1"，只是写为数字不要带引号*/
        resultJs.put("success", 1);
        resultJs.put("message", "上传成功");
        resultJs.put("url", "http://localhost:8848/admin/lib/editormd/images/loading.gif");
        return resultJs;
    }

//    @RequestMapping("editormdPic1")
//    @ResponseBody
//    public JSONObject editormdPic1(@RequestParam(value = "file", required = true) MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String originalFilename = multipartFile.getOriginalFilename();
//        String extensionName = originalFilename.substring(originalFilename.lastIndexOf("."));
//        String saveFileName = Thread.currentThread().getId() + extensionName;
////        String a = (URLDecoder.decode(ResourceUtils.getURL("classpath:").getPath(), "UTF-8"));
//        String a = "E:\\projet\\RainBlog\\src\\main\\resources\\static\\admin\\blog_image\\";
//        String path1 = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "static";
//        Path path = Paths.get(a, saveFileName);
//        Files.write(path, multipartFile.getBytes());
//        multipartFile.transferTo(Paths.get(a, saveFileName).toFile());
//        String fileUrl = "http://localhost:8848" + "/admin/blog_image/" + saveFileName;
//        File file = ResourceUtils.getFile(ResourceUtils.FILE_URL_PREFIX);
//        String path2 = ClassUtils.getDefaultClassLoader().getResource("").getPath();
//        JSONObject result = new JSONObject();
//        result.put("success", 1);
//        result.put("message", "success");
//        result.put("url", fileUrl);
//        return result;
//    }
}
