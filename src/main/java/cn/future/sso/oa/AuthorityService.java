package cn.future.sso.oa;

import cn.future.config.properties.GunsProperties;
import com.seeyon.client.CTPRestClient;
import com.seeyon.client.CTPServiceClientManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 验证服务
 */

@Component
@Scope("singleton")
public class AuthorityService {


    private static GunsProperties gunsProperties;


    // 定义REST动态客户机

    private static CTPRestClient client = null;

    public static Boolean auFlag = false;


   private void init() {
       CTPServiceClientManager clientManager = CTPServiceClientManager.getInstance(gunsProperties.getOaUrl());
       client = clientManager.getRestClient();
       auFlag = client.authenticate("test", "123456");
   }


   @Autowired
    public void setGunsProperties(GunsProperties gunsProperties) {
        AuthorityService.gunsProperties = gunsProperties;
        init();
    }

    /**
     * 验证权限
     * @return 验证结果
     */
    public boolean authenticate() {

        CTPServiceClientManager clientManager = CTPServiceClientManager.getInstance(gunsProperties.getOaUrl());
        client = clientManager.getRestClient();
        return client.authenticate("test", "123456");
    }

    /**
     * 获取client
     * @return client
     */
    public CTPRestClient getClient() {
        return client;
    }

    /**
     * 设置client
     * @param client client
     */
    public void setClient(CTPRestClient client) {
        this.client = client;
    }
    

}
