package uestc.lj.eduService.controller;

import org.springframework.web.bind.annotation.*;
import uestc.lj.commonutils.R;

@RestController
@RequestMapping("/eduService/user")

public class EduLoginController {

    @PostMapping("login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    @GetMapping("info")
    public R info() {
        return R.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://i.loli.net/2021/05/12/jRNQayuW5cKXwsd.png");
    }
}
