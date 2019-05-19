package cn.future.core.util;

import cn.future.modular.unified.service.IApplicationService;
import cn.future.modular.unified.service.IUninterfaceService;
import cn.future.core.common.exception.BizExceptionEnum;
import cn.future.modular.unified.model.Application;
import cn.future.modular.unified.model.Uninterface;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.alibaba.fastjson.JSON;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口过滤器
 */
public class InterfaceFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        IApplicationService applicationService = SpringContextHolder.getBean(IApplicationService.class);
        IUninterfaceService uninterfaceService = SpringContextHolder.getBean(IUninterfaceService.class);
        Map<String, Object> map = new HashMap<String, Object>();
        String appid = httpRequest.getParameter("appid");
        String code = httpRequest.getParameter("code");
        if(ToolUtil.isEmpty(appid) || ToolUtil.isEmpty(code)){
            map.put("code", BizExceptionEnum.APP_NOT_EXIT.getCode());
            map.put("msg", BizExceptionEnum.APP_NOT_EXIT.getMessage());
            jsonR(httpRequest,httpResponse,map);
            return;
        }
        //校验appid获取应用
        Application application = applicationService.getByAppid(appid);
        //根据访问url模糊查询此接口状态是否启用
        Uninterface uninterface = uninterfaceService.selectByUrl(httpRequest.getServletPath());
        //接口已停用
        if (null != uninterface) {
            if (1 != uninterface.getStatus()) {
                map.put("code", BizExceptionEnum.INTERFACE_TING.getCode());
                map.put("msg", BizExceptionEnum.INTERFACE_TING.getMessage());
                jsonR(httpRequest,httpResponse,map);
                return;
            }
        } else {
            map.put("code", BizExceptionEnum.INTERFACE_NOFIND.getCode());
            map.put("msg", BizExceptionEnum.INTERFACE_NOFIND.getMessage());
            jsonR(httpRequest,httpResponse,map);
            return;
        }
        if (null != application) {
            if (1 != application.getFlag()) {
                map.put("code", BizExceptionEnum.APP_NOT_TING.getCode());
                map.put("msg", BizExceptionEnum.APP_NOT_TING.getMessage());
                jsonR(httpRequest,httpResponse,map);
                return;
            }else{
                filterChain.doFilter(httpRequest, httpResponse);
                return;
            }
        } else {
            map.put("code", BizExceptionEnum.APP_NOT_EXIT.getCode());
            map.put("msg", BizExceptionEnum.APP_NOT_EXIT.getMessage());
            jsonR(httpRequest,httpResponse,map);
            return;
        }
    }

    public void jsonR(HttpServletRequest request,HttpServletResponse response,Map<String, Object> map)throws IOException{
        map.put("data","");
        String json = JSON.toJSONString(map);
        request.setCharacterEncoding("utf-8");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.print(json);
    }

    @Override
    public void destroy() {

    }
}
