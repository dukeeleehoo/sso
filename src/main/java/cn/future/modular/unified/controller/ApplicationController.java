package cn.future.modular.unified.controller;


import cn.future.modular.unified.warpper.ApplicationWarpper;
import cn.future.config.properties.GunsProperties;
import cn.future.core.common.annotion.BussinessLog;

import cn.future.core.common.constant.dictmap.UserDict;

import cn.future.core.common.exception.BizExceptionEnum;
import cn.future.core.common.node.ZTreeNode;
import cn.future.modular.system.model.User;
import cn.future.modular.system.service.IUserService;

import cn.future.sso.Base64Utils;
import cn.future.sso.RSAUtils;

import cn.stylefeng.roses.core.base.controller.BaseController;


import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.future.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.future.modular.unified.model.Application;
import cn.future.modular.unified.service.IApplicationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * 接入应用控制器
 *
 * @author fengshuonan
 * @Date 2018-12-06 13:48:59
 */
@Controller
@RequestMapping("/application")
public class ApplicationController extends BaseController {

    private String PREFIX = "/unified/application/";

    @Autowired
    private IApplicationService applicationService;


    @Autowired
    private IUserService userService;

    @Autowired
    private GunsProperties gunsProperties;
    /**
     * 跳转到接入应用首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "application.html";
    }

    /**
     * 跳转到添加接入应用
     */
    @RequestMapping("/application_add")
    public String applicationAdd() {
        return PREFIX + "application_add.html";
    }

    /**
     * 跳转到修改接入应用
     */
    @RequestMapping("/application_update/{applicationId}")
    public String applicationUpdate(@PathVariable Integer applicationId, Model model) {
        Application application = applicationService.selectById(applicationId);
        model.addAttribute("item",application);
        LogObjectHolder.me().set(application);
        return PREFIX + "application_edit.html";
    }

    /**
     * 获取接入应用列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list() {
        List<Map<String, Object>> applications= applicationService.selectApplications();
        return new ApplicationWarpper(applications).wrap();
    }

    /**
     * 新增接入应用
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Application application, HttpServletRequest request)throws Exception {

        application.setCreatetime(new Date());
        application.setDelflag(0);
        application.setToken(UUID.randomUUID().toString());
        application.setAppid(UUID.randomUUID().toString());
        String basePath =  gunsProperties.getKeyPath();

        Map<String, Object>  map  =  RSAUtils.genKeyPair();
       Base64Utils.decodeToFile(basePath+application.getAppid()+"_publicKey.key", RSAUtils.getPublicKey(map));
        Base64Utils.decodeToFile(basePath + application.getAppid() + "_privateKey.key", RSAUtils.getPrivateKey(map));
       application.setPrivatekey(basePath+application.getAppid()+"_privateKey.key");
       application.setPublickey(basePath+application.getAppid()+"_publicKey.key");
       applicationService.insert(application);
        return SUCCESS_TIP;
    }

    /**
     * 删除接入应用
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(value = "删除应用", key = "applicationId", dict = UserDict.class)
    @ResponseBody
    public Object delete(@RequestParam Integer applicationId) {
        Application application = applicationService.selectById(applicationId);
        //修改删除标记
        application.setDelflag(1);
        applicationService.updateById(application);
        return SUCCESS_TIP;
    }

    /**
     * 修改接入应用
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Application application) {
        applicationService.updateById(application);
        return SUCCESS_TIP;
    }

    /**
     * 接入应用详情
     */
    @RequestMapping(value = "/detail/{applicationId}")
    @ResponseBody
    public Object detail(@PathVariable("applicationId") Integer applicationId) {
        return applicationService.selectById(applicationId);
    }


    /**
     * 下载公钥
     */
    @RequestMapping(value = "/uploadkey")
    public void uploadkey(Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Application application = applicationService.selectById(id);
        String keypath = application.getPublickey();

        request.setCharacterEncoding("UTF-8");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;


        //获取下载文件露肩
        String downLoadPath = keypath;
        //获取文件的长度
        long fileLength = new File(downLoadPath).length();

        //设置文件输出类型
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment; filename="
                + new String("publicKey.key".getBytes("utf-8"), "ISO8859-1"));
        //设置输出长度
        response.setHeader("Content-Length", String.valueOf(fileLength));
        //获取输入流
        bis = new BufferedInputStream(new FileInputStream(downLoadPath));
        //输出流
        bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        //关闭流
        bis.close();
        bos.close();
    }

    /**
     * 启用应用
     */
    @RequestMapping("/freeze")
    @BussinessLog(value = "启用应用", key = "applicationId", dict = UserDict.class)
    @ResponseBody
    public ResponseData freeze(@RequestParam Integer applicationId) {
        if (ToolUtil.isEmpty(applicationId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        Application application = applicationService.selectById(applicationId);
        application.setFlag(1);
        applicationService.updateById(application);
        return SUCCESS_TIP;
    }

    /**
     * 停用应用
     */
    @RequestMapping("/unfreeze")
    @BussinessLog(value = "停用应用", key = "applicationId", dict = UserDict.class)
    @ResponseBody
    public ResponseData unfreeze(@RequestParam Integer applicationId) {
        if (ToolUtil.isEmpty(applicationId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        Application application = applicationService.selectById(applicationId);
        application.setFlag(2);
        applicationService.updateById(application);
        return SUCCESS_TIP;
    }

    /**
     * 应用树型接口
     * @param userId
     * @return
     */
    @RequestMapping("/userappTreeList/{userId}")
    @ResponseBody
    public List<ZTreeNode> userAppTree(@PathVariable("userId")String userId){
        List<Application> applications = applicationService.selectList(null);
        User user = userService.selectById(userId);
        List<ZTreeNode> zTreeNodes = new ArrayList<>();
        for(Application application:applications){
            ZTreeNode zTreeNode = new ZTreeNode();
            zTreeNode.setId(Long.valueOf(application.getId()));
            zTreeNode.setName(application.getName());
            zTreeNode.setIsOpen(true);
            zTreeNodes.add(zTreeNode);
            if(null!=user.getSubsystemadmin()){
                if(application.getAppid().equals(user.getSubsystemadmin())){
                    zTreeNode.setChecked(true);
                }
            }else{
                zTreeNode.setChecked(false);
            }
        }
        return zTreeNodes;
    }

}
