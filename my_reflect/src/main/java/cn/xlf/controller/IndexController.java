package cn.xlf.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 类中域:徐林飞
 * @date 2019/12/26 16:24
 */
@RestController
public class IndexController {

    @RequestMapping(value = {"/",""})
    public String test(){
        return "1111";
    }

}
