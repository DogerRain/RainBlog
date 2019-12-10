package cn.yudianxx.system.controller;

import cn.yudianxx.common.annotation.Log;
import cn.yudianxx.common.controller.BaseController;
import cn.yudianxx.common.exception.GlobalException;
import cn.yudianxx.common.utils.R;
import cn.yudianxx.system.entity.SysLoginLog;
import cn.yudianxx.system.entity.SysUser;
import cn.yudianxx.system.service.ArticleService;
import cn.yudianxx.system.service.CommentService;
import cn.yudianxx.system.service.LoginLogService;
import cn.yudianxx.system.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huangyongwen
 * @date 2018/10/18
 */
@RestController
@RequestMapping("/api/user")
@Api(value = "UserController", tags = {"用户管理接口"})
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LoginLogService loginLogService;

    @GetMapping("/info")
    public R getInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("articleCount", articleService.count());
        map.put("commentCount", commentService.count());
        map.put("todayIp", 12);
        List<SysLoginLog> log = loginLogService.list();
        if (log == null || log.size() == 0) {
            map.put("lastLoginTime", null);
        } else {
            map.put("lastLoginTime", log.get(log.size() - 1).getCreateTime());
        }
        map.put("token", this.getSession().getId());
        map.put("user", this.getCurrentUser());
        return new R<>(map);
    }

    @GetMapping("/{id}")
    public R findById(@PathVariable Long id) {
        return new R<>(userService.getById(id));
    }

    @PostMapping
    @Log("新增用户")
    public R save(@RequestBody SysUser sysUser) {
        try {
            userService.add(sysUser);
            return new R();
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @PutMapping
    @Log("更新用户")
    public R update(@RequestBody SysUser sysUser) {
        try {
            sysUser.setId(this.getCurrentUser().getId());
            userService.update(sysUser);
            return new R();
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Log("删除用户")
    public R delete(@PathVariable Long id) {
        try {
            userService.delete(id);
            return new R();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }
}
