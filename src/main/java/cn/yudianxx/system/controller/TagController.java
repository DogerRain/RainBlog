package cn.yudianxx.system.controller;

import cn.yudianxx.common.annotation.Log;
import cn.yudianxx.common.controller.BaseController;
import cn.yudianxx.common.exception.GlobalException;
import cn.yudianxx.common.utils.QueryPage;
import cn.yudianxx.common.utils.R;
import cn.yudianxx.system.entity.SysTag;
import cn.yudianxx.system.service.TagService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author TyCoding
 * @date 2018/10/18
 */
@RestController
@RequestMapping("/api/tag")
@Api(value = "TagController", tags = {"标签管理接口"})
public class TagController extends BaseController {

    @Autowired
    private TagService tagService;

    @GetMapping("/findAll")
    public R newList() {
        return new R<>(tagService.findAll());
    }

    @GetMapping("/list")
    public R findByPage(SysTag tag, QueryPage queryPage) {
        return new R<>(super.getData(tagService.list(tag, queryPage)));
    }

    @GetMapping("/{id}")
    public R findById(@PathVariable Long id) {
        return new R<>(tagService.getById(id));
    }

    @PostMapping
    @Log("新增标签")
    public R save(@RequestBody SysTag tag) {
        try {
            tagService.add(tag);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @PutMapping
    @Log("更新标签")
    public R update(@RequestBody SysTag tag) {
        try {
            tagService.update(tag);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Log("删除标签")
    public R delete(@PathVariable Long id) {
        try {
            tagService.delete(id);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }
}
