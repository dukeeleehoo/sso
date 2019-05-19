package cn.future.modular.app;

import cn.future.core.common.exception.BizExceptionEnum;
import cn.future.core.util.AppUtil;
import cn.future.modular.system.model.User;
import cn.future.modular.system.service.IUserService;
import cn.stylefeng.roses.core.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * app接口
 * @author zp
 */
@RestController
@RequestMapping("/app")
public class AppInterface {

    @Autowired
    private IUserService userService;

    /**
     * app修改用户密码
     */
    @RequestMapping(path="/updatePassword")
    public Map<String, Object> updatePassword(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter("account");
        String newPassword = request.getParameter("newPassword");
        String oldPassword = request.getParameter("oldPassword");
        String token = request.getParameter("token");

        //参数检查
        if(ToolUtil.isEmpty(token) || ToolUtil.isEmpty(account) || ToolUtil.isEmpty(newPassword) || ToolUtil.isEmpty(oldPassword)){
            return AppUtil.msgMap(BizExceptionEnum.CANSHU_ERR.getCode(), BizExceptionEnum.CANSHU_ERR.getMessage(),null);
        }

        User user = userService.getByAccount(account);
        //根据用户名找到了用户
        if(user != null) {
            //检查token
            if (token.equals(user.getToken())) {
                if (user.getPassword().equals(oldPassword)) {
                    user.setPassword(newPassword);
                    user.updateById();
                    return AppUtil.msgMap(BizExceptionEnum.SSO_YES_PWD.getCode(), BizExceptionEnum.SSO_YES_PWD.getMessage(), null);
                } else {
                    return AppUtil.msgMap(BizExceptionEnum.SSO_OLD_PWD_NOT_RIGHT.getCode(), BizExceptionEnum.SSO_OLD_PWD_NOT_RIGHT.getMessage(), null);
                }
            }else{
                return AppUtil.msgMap(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage(), null);
            }
        }else{
            return AppUtil.msgMap(BizExceptionEnum.SSO_NO_THIS_USER.getCode(), BizExceptionEnum.SSO_NO_THIS_USER.getMessage(),null);
        }
    }

    /**
     * 根据String型时间，获取long型时间，单位毫秒
     * @param inVal 时间字符串
     * @return long型时间
     */
    public static long fromDateStringToLong(String inVal) {
        Date date = null;
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
        try {
            date = inputFormat.parse(inVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

}
