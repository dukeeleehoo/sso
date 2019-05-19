package cn.future.core.common.controller;


import cn.future.core.common.exception.BizExceptionEnum;
import cn.future.core.log.LogManager;
import cn.future.core.log.factory.LogTaskFactory;
import cn.future.core.shiro.ShiroKit;
import cn.future.core.shiro.ShiroUser;
import cn.future.core.tokenlogin.UserAndToken;
import cn.future.core.util.AppUtil;
import cn.future.modular.system.model.User;
import cn.future.modular.unified.model.Application;
import cn.future.modular.unified.service.IApplicationService;
import cn.future.modular.unified.service.IUninterfaceLogService;
import cn.future.modular.unified.service.IUninterfaceService;
import cn.future.core.util.JiguangPush;
import cn.future.modular.system.service.IUserService;
import cn.future.sso.RSAUtils;
import cn.hutool.http.HttpUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Pattern;

import static cn.stylefeng.roses.core.util.HttpContext.getIp;

/**
 * 推送消息Controller
 */
@Controller
@RequestMapping("/apppush")
@Slf4j
public class JiGuangPushController extends BaseController {

    @Autowired
    private IApplicationService applicationService;

    @Autowired
    private IUserService userService;

    @RequestMapping("/push")
    @ResponseBody
    public Map<String, Object> push(HttpServletRequest request, HttpServletResponse response,String appid,String code) throws Exception {

        Application application = applicationService.getByAppid(appid);

        if(null==application){
            return AppUtil.msgMap(BizExceptionEnum.APP_NOT_EXIT.getCode(), BizExceptionEnum.APP_NOT_EXIT.getMessage(),null);
        }

        String result = RSAUtils.decryptByPrivateKey(code,application);
        JSONObject json = JSON.parseObject(result);

        //推送的用户
        String account = json.getString("account");


        //内容标题
        String title = json.getString("title");

        //内容摘要
        String msg_content = json.getString("content");

        //内容链接
        String redirctUrl = json.getString("redirctUrl");


        //参数检查
        if(ToolUtil.isEmpty(account) || ToolUtil.isEmpty(title) || ToolUtil.isEmpty(appid) || ToolUtil.isEmpty(msg_content)||ToolUtil.isEmpty(redirctUrl)){
            return AppUtil.msgMap(BizExceptionEnum.CANSHU_ERR.getCode(), BizExceptionEnum.CANSHU_ERR.getMessage(),null);
        }
        User user = userService.getByAccount(account);
        if(null==user){
            return AppUtil.msgMap(BizExceptionEnum.SSO_NO_THIS_USER.getCode(), BizExceptionEnum.SSO_NO_THIS_USER.getMessage(),null);
        }
        if(!(redirctUrl.startsWith("http")||redirctUrl.startsWith("https"))){
            return AppUtil.msgMap(BizExceptionEnum.ERROR_URL.getCode(), BizExceptionEnum.ERROR_URL.getMessage(),null);
        }
        Map map = new HashMap();
        map.put("account", account);
        map.put("token", application.getToken());
        map.put("code", BizExceptionEnum.LINK_SUCCESS.getCode());
        map.put("msg", BizExceptionEnum.LINK_SUCCESS.getMessage());
        String str = JSON.toJSON(map).toString();
        map.clear();
        map.put("authurl", application.getAuthurl());
        map.put("redirctUrl", redirctUrl);
        map.put("appid", appid);
        map.put("code", RSAUtils.encryptByPrivateKey(str, application));
        String data = JSON.toJSONString(map);
        System.out.println(data);
        map.clear();

        List<String> alis = new ArrayList<>();
        alis.add(user.getToken());
        int res=0;
        //向某个用户所有平台(安卓,ios,平板)推送

        String errorMsg = "";
        try {
            res = JiguangPush.sendToAllPlatformForOneUser(
                    alis,
                    msg_content,
                    "key",
                    data,
                    title);
        }catch (Exception e){
            System.out.println(e.getMessage());
            errorMsg = e.getMessage();
        }
        log.debug("推送结果：" + res);
        map.clear();
        if(res==0){
            map.put("code", BizExceptionEnum.ERROR_PUSH.getCode());
            map.put("msg", errorMsg);
            return map;
        }
        map.put("code", BizExceptionEnum.LINK_SUCCESS.getCode());
        map.put("msg", BizExceptionEnum.LINK_SUCCESS.getMessage());
        return map;

    }




    @RequestMapping("/urlRedirect")
    public void appUrlGo(HttpServletResponse response,HttpServletRequest request,String authurl,String redirctUrl,String code,String appid) throws IOException {

//
//        if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("code", code);
            map.put("redirctUrl", redirctUrl);
            sendByPost(authurl,request,response,map);
//        }else{
//            response.sendRedirect("/login?code="+code+"&redirctUrl="+redirctUrl+"&authurl="+authurl+"&appid="+appid);
//            return;
//        }

    }

    /**
     * post重定向
     * @param url
     * @param request
     * @param response
     * @throws IOException
     */

    public void sendByPost(String url,HttpServletRequest request,HttpServletResponse response,Map<String,Object> map) throws IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");

        out.println("<HTML>");

        out.println(" <HEAD><TITLE>sender</TITLE></HEAD>");

        out.println(" <BODY>");

        out.println("<form name=\"submitForm\" action=\"" + url + "\" method=\"post\">");

        for (Map.Entry<String, Object> entry : map.entrySet()) {

            String key = entry.getKey();

            out.println("<input type=\"hidden\" name=\"" + key + "\" value=\"" + entry.getValue() + "\"/>");

        }

        out.println("</from>");

        out.println("<script>window.document.submitForm.submit();</script> ");

        out.println(" </BODY>");

        out.println("</HTML>");

        out.flush();

        out.close();

    }

}
