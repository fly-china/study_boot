package com.lpf.spring.webSocket;

import com.lpf.spring.common.ResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author lipengfei
 * @create 2019-04-17 11:29
 **/
@Controller
@RequestMapping("/chatCenter")
public class WebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    // 页面请求
    @GetMapping("/goChat/{uid}")
    public ModelAndView socket(@PathVariable String uid) {
        ModelAndView mav = new ModelAndView("websocket/websocket");
        mav.addObject("uid", uid);
        return mav;
    }

    //推送数据接口
    @ResponseBody
    @RequestMapping("/sysPushMsg/{uid}")
    public ResultModel pushToWeb(@PathVariable String uid, String message) {
        try {
            WebSocketServer.sendInfo(Long.valueOf(uid), message);
        } catch (Exception e) {
            logger.error("推送数据异常，原因：", e);
            return ResultModel.failure("推送数据异常，原因：" + e.getMessage());
        }
        logger.info("系统给uid={}的用户推送数据成功。");
        return ResultModel.success("给uid=" + uid + "的用户推送数据成功。");
    }
}
