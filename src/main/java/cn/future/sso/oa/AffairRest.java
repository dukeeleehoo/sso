package cn.future.sso.oa;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
public class AffairRest {


    @Autowired
    AuthorityService authorityService;

    /**
     * 获取公文待办事项
     * @param userCode 人员code
     */
    public      List<OADaiBan> exportEdocPendingList(String userCode) {
        String  json = authorityService.getClient().get("affairs/pending/code/" + userCode+"?firstNum=1&pageSize=6", String.class);
        JSONObject jsonObject = JSONObject.parseObject(json);
        List<OADaiBan> list = JSONObject.parseArray(jsonObject.get("data").toString(), OADaiBan.class);
        return list;
    }

    /**
     * 获取人员code
     * @param ticketId
     * @return
     */
    public   String getUserCode(String ticketId) {
        String json = authorityService.getClient().get("orgMember?loginName=" + ticketId, String.class);
        JSONObject jsonObject = JSONObject.parseObject(json);
        return jsonObject.getString("code");
    }

    /**
     * 获取单位新闻板块为21 得单位新闻板块内容
     * @param userCOde
     * @return
     */

    public List<OANews> getNews(String userCOde){
        String   json = authorityService.getClient().get("news/newsType/21?ticket=" + userCOde+"&firstNum=1&pageSize=6", String.class);
        List<OANews> list = JSONObject.parseArray(json, OANews.class);
        return list;
    }

}
