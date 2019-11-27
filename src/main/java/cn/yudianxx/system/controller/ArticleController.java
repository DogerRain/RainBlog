package cn.yudianxx.system.controller;

import cn.yudianxx.common.annotation.Log;
import cn.yudianxx.common.controller.BaseController;
import cn.yudianxx.common.exception.GlobalException;
import cn.yudianxx.common.utils.QueryPage;
import cn.yudianxx.common.utils.R;
import cn.yudianxx.system.entity.SysArticle;
import cn.yudianxx.system.entity.SysTag;
import cn.yudianxx.system.entity.dto.ArchivesWithArticle;
import cn.yudianxx.system.response.ResponseResult;
import cn.yudianxx.system.service.ArticleService;
import cn.yudianxx.system.service.ArticleTagService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author TyCoding
 * @date 2018/10/16
 */
@RestController
@RequestMapping("/api/article")
@Api(value = "ArticleController", tags = {"文章管理接口"})
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleTagService articleTagService;

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


    @PostMapping(value = "/uploadFile")
    public String uploadFile(@RequestParam(value = "editormd-image-file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult();
        Map<String, Object> map = new HashMap<String, Object>();
        File targetFile = null;
        String url = "";//返回存储路径
        int code = 1;
        System.out.println(file);
        String fileName = file.getOriginalFilename();//获取文件名加后缀
        if (fileName != null && fileName != "") {
            String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/upload/imgs/";//存储路径
            String path = request.getSession().getServletContext().getRealPath("upload/imgs"); //文件存储位置
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
            fileName = new Date().getTime() + "_" + new Random().nextInt(1000) + fileF;//新的文件名

            //先判断文件是否存在
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String fileAdd = sdf.format(new Date());
            //获取文件夹路径
            File file1 = new File(path + "/" + fileAdd);
            //如果文件夹不存在则创建
            if (!file1.exists() && !file1.isDirectory()) {
                file1.mkdir();
            }
            //将图片存入文件夹
            targetFile = new File(file1, fileName);
            try {
                //将上传的文件写到服务器上指定的文件。
                file.transferTo(targetFile);
                url = returnUrl + fileAdd + "/" + fileName;
                code = 0;
                result.setCode(code);
                result.setMessage("图片上传成功");
                map.put("url", url);
                result.setUrl(url);
            } catch (Exception e) {
                e.printStackTrace();
                result.setMessage("系统异常，图片上传失败");
            }
        }
        return JSON.toJSONString(result);
    }
    @RequestMapping("editormdPic")
    @ResponseBody
    public JSONObject editormdPic (@RequestParam(value = "editormd-image-file", required = true) MultipartFile file1, HttpServletRequest request,HttpServletResponse response) throws Exception {
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

    @RequestMapping("editormdPic1")
    @ResponseBody
    public JSONObject editormdPic1 (@RequestParam(value = "editormd-image-file", required = true) MultipartFile multipartFile, HttpServletRequest request,HttpServletResponse response) throws Exception {
        String originalFilename = multipartFile.getOriginalFilename();
        String extensionName = originalFilename.substring(originalFilename.lastIndexOf("."));
        String saveFileName =  Thread.currentThread().getId() + extensionName;
        String a ="G:\\源码\\Vue+Springboot\\src\\main\\resources\\static\\admin\\blog_image\\";
        Path path = Paths.get(a, saveFileName);
        Files.write(path, multipartFile.getBytes());
        multipartFile.transferTo(Paths.get(a, saveFileName).toFile());
        String fileUrl = "http://localhost:8848" + "/admin/blog_image/" + saveFileName;
        JSONObject result = new JSONObject();
        result.put("success", 1);
        result.put("message", "success");
        result.put("url", fileUrl);
        return result;
    }
}
