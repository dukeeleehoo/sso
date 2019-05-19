package cn.future.modular.system.controller;

import cn.future.core.common.exception.BizExceptionEnum;
import cn.future.core.log.LogObjectHolder;
import cn.future.modular.system.service.IPushService;
import cn.future.modular.system.warpper.RankWarpper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import cn.future.modular.system.model.Rank;
import cn.future.modular.system.service.IRankService;

import java.util.List;
import java.util.Map;

/**
 * 职务控制器
 *
 * @author fengshuonan
 * @Date 2019-04-21 18:55:38
 */
@Controller
@RequestMapping("/rank")
public class RankController extends BaseController {

    private String PREFIX = "/system/rank/";

    @Autowired
    private IRankService rankService;

    @Autowired
    private IPushService pushService;

    /**
     * 跳转到职务首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "rank.html";
    }

    /**
     * 跳转到添加职务
     */
    @RequestMapping("/rank_add")
    public String rankAdd() {
        return PREFIX + "rank_add.html";
    }

    /**
     * 跳转到修改职务
     */
    @RequestMapping("/rank_update/{rankId}")
    public String rankUpdate(@PathVariable Integer rankId, Model model) {
        Rank rank = rankService.selectById(rankId);
        model.addAttribute("item",rank);
        LogObjectHolder.me().set(rank);
        return PREFIX + "rank_edit.html";
    }

    /**
     * 获取职务列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.rankService.list(condition);
        return super.warpObject(new RankWarpper(list));
    }

    /**
     * 新增职务
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Rank rank) {
        rank.setStatus(1);
        // 判断code是否重复
        Rank m = rankService.getByCode(rank.getCode());
        if(m != null){
            throw new ServiceException(BizExceptionEnum.CODE_ERROR);
        }
        rankService.insert(rank);
        pushService.pushRank(rank,"add");
        return SUCCESS_TIP;
    }

    /**
     * 删除职务
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer rankId) {
        rankService.deleteById(rankId);
        pushService.pushRank(rankService.selectById(rankId),"delete");
        return SUCCESS_TIP;
    }

    /**
     * 修改职务
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Rank rank) {
        // 判断code是否重复
        Rank oldRank = rankService.selectById(rank.getId());
        if (ToolUtil.isNotEmpty(rank.getCode())) {
            Rank m = rankService.getByCode(rank.getCode());
            if (m != null) {
                if(!oldRank.getCode().equals(rank.getCode())){
                    throw new ServiceException(BizExceptionEnum.CODE_ERROR);
                }
            }
        }
        rankService.updateById(rank);
        pushService.pushRank(rank,"update");
        return SUCCESS_TIP;
    }

    /**
     * 职务详情
     */
    @RequestMapping(value = "/detail/{rankId}")
    @ResponseBody
    public Object detail(@PathVariable("rankId") Integer rankId) {
        return rankService.selectById(rankId);
    }
}
