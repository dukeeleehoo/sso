package cn.future.core.tokenlogin;

import org.apache.shiro.authc.UsernamePasswordToken;

import java.io.Serializable;

/**
 * 用户token登录
 */
public class UserAndToken extends UsernamePasswordToken implements Serializable {


    public UserAndToken( ) {

    }

    private String username;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserAndToken(String token) {
        this.token = token;
    }
}
