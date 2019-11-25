package cn.yudianxx.system.controller;

import cn.yudianxx.common.annotation.Log;
import cn.yudianxx.common.controller.BaseController;
import cn.yudianxx.common.exception.GlobalException;
import cn.yudianxx.common.utils.QueryPage;
import cn.yudianxx.common.utils.R;
import cn.yudianxx.system.entity.SysLink;
import cn.yudianxx.system.service.LinkService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author TyCoding
 * @date 2018/10/18
 */
@RestController
@RequestMapping("/api/link")
@Api(value = "LinkController", tags = {"友链管理接口"})
public class LinkController extends BaseController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public R list(SysLink link, QueryPage queryPage) {
        return new R<>(super.getData(linkService.list(link, queryPage)));
    }

    @GetMapping("/{id}")
    public R findById(@PathVariable Long id) {
        return new R<>(linkService.getById(id));
    }

    @PostMapping
    @Log("新增友链")
    public R save(@RequestBody SysLink link) {
        try {
            linkService.add(link);
            return new R();
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @PutMapping
    @Log("更新友链")
    public R update(@RequestBody SysLink link) {
        try {
            linkService.update(link);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Log("删除友链")
    public R delete(@PathVariable Long id) {
        try {
            linkService.delete(id);
            return new R();
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
    }
}
