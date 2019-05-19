package cn.future.core.shiro;

import cn.future.core.shiro.service.UserAuthService;
import cn.future.core.shiro.service.impl.UserAuthServiceServiceImpl;
import cn.future.core.tokenlogin.UserAndToken;
import cn.future.modular.system.model.User;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * 密码
 * @author zp
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {

        if(authcToken instanceof UserAndToken) {
            return true;
        }else{
            UserAuthService shiroFactory = UserAuthServiceServiceImpl.me();
            UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
            System.out.print("username=========" + token.getUsername());
            User user = shiroFactory.user(token.getUsername());
            String oldPass = user.getPassword();
            String newPass = String.valueOf(token.getPassword());
            System.out.print("oldPass=========" + oldPass);
            System.out.print("newPass=========" + newPass);
            if (oldPass.equals(newPass)) {
                return true;
            } else {
                return false;
            }
        }
    }
}

