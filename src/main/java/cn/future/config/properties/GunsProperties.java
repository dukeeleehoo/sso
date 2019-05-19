/**
 * Copyright 2018-2020 stylefeng & fengshuonan (sn93@qq.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.future.config.properties;

import cn.stylefeng.roses.core.util.ToolUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

import static cn.stylefeng.roses.core.util.ToolUtil.getTempPath;

/**
 * guns项目配置
 *
 * @author stylefeng
 * @Date 2017/5/23 22:31
 */
@Component
@ConfigurationProperties(prefix = GunsProperties.PREFIX)
public class GunsProperties {

    public static final String PREFIX = "guns";

    private Boolean kaptchaOpen = false;

    private Boolean swaggerOpen = false;

    private String fileUploadPath;

    private String keyPath;
    private String appKey;

    private String masterSecret;

    private Boolean haveCreatePath = false;

    private Boolean springSessionOpen = false;

    /**
     * session 失效时间（默认为30分钟 单位：秒）
     */
    private Integer sessionInvalidateTime = 30 * 60;

    /**
     * session 验证失效时间（默认为15分钟 单位：秒）
     */
    private Integer sessionValidationInterval = 15 * 60;


    private String jixiaoDaibanUrl;

    private String gisDaiBanUrl;

    private String gisOneUrl;
    private String oaOneUrl;

    private String gisMoreUrl;

    private String oaUrl;

    private String oaNewsOneUrl;

    private String oaNewsMoreUrl;

    private String oaMobileOneUrl;

    public String getOaMobileOneUrl() {
        return oaMobileOneUrl;
    }

    public void setOaMobileOneUrl(String oaMobileOneUrl) {
        this.oaMobileOneUrl = oaMobileOneUrl;
    }

    public String getOaNewsOneUrl() {
        return oaNewsOneUrl;
    }

    public void setOaNewsOneUrl(String oaNewsOneUrl) {
        this.oaNewsOneUrl = oaNewsOneUrl;
    }

    public String getOaNewsMoreUrl() {
        return oaNewsMoreUrl;
    }

    public void setOaNewsMoreUrl(String oaNewsMoreUrl) {
        this.oaNewsMoreUrl = oaNewsMoreUrl;
    }

    public String getOaUrl() {
        return oaUrl;
    }

    public void setOaUrl(String oaUrl) {
        this.oaUrl = oaUrl;
    }

    public String getOaOneUrl() {
        return oaOneUrl;
    }

    public void setOaOneUrl(String oaOneUrl) {
        this.oaOneUrl = oaOneUrl;
    }

    public String getGisMoreUrl() {
        return gisMoreUrl;
    }

    public void setGisMoreUrl(String gisMoreUrl) {
        this.gisMoreUrl = gisMoreUrl;
    }

    public String getGisOneUrl() {
        return gisOneUrl;
    }

    public void setGisOneUrl(String gisOneUrl) {
        this.gisOneUrl = gisOneUrl;
    }

    public String getGisDaiBanUrl() {
        return gisDaiBanUrl;
    }



    public void setGisDaiBanUrl(String gisDaiBanUrl) {
        this.gisDaiBanUrl = gisDaiBanUrl;
    }

    public String getJixiaoDaibanUrl() {
        return jixiaoDaibanUrl;
    }

    public void setJixiaoDaibanUrl(String jixiaoDaibanUrl) {
        this.jixiaoDaibanUrl = jixiaoDaibanUrl;
    }

    private Integer delTokenDate;

    public String getFileUploadPath() {
        //如果没有写文件上传路径,保存到临时目录
        if (ToolUtil.isEmpty(fileUploadPath)) {
            return getTempPath();
        } else {
            //判断有没有结尾符,没有得加上
            if (!fileUploadPath.endsWith(File.separator)) {
                fileUploadPath = fileUploadPath + File.separator;
            }
            //判断目录存不存在,不存在得加上
            if (!haveCreatePath) {
                File file = new File(fileUploadPath);
                file.mkdirs();
                haveCreatePath = true;
            }
            return fileUploadPath;
        }
    }

    public String getKeyPath() {
        //如果没有写文件上传路径,保存到临时目录
        if (ToolUtil.isEmpty(keyPath)) {
            return getTempPath();
        } else {
            //判断有没有结尾符,没有得加上
            if (!keyPath.endsWith(File.separator)) {
                keyPath = keyPath + File.separator;
            }
            //判断目录存不存在,不存在得加上
            if (!haveCreatePath) {
                File file = new File(keyPath);
                file.mkdirs();
                haveCreatePath = true;
            }
            return keyPath;
        }
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public void setFileUploadPath(String fileUploadPath) {
        this.fileUploadPath = fileUploadPath;
    }

    public Boolean getKaptchaOpen() {
        return kaptchaOpen;
    }

    public void setKaptchaOpen(Boolean kaptchaOpen) {
        this.kaptchaOpen = kaptchaOpen;
    }

    public Boolean getSwaggerOpen() {
        return swaggerOpen;
    }

    public void setSwaggerOpen(Boolean swaggerOpen) {
        this.swaggerOpen = swaggerOpen;
    }

    public Boolean getSpringSessionOpen() {
        return springSessionOpen;
    }

    public void setSpringSessionOpen(Boolean springSessionOpen) {
        this.springSessionOpen = springSessionOpen;
    }

    public Integer getSessionInvalidateTime() {
        return sessionInvalidateTime;
    }

    public void setSessionInvalidateTime(Integer sessionInvalidateTime) {
        this.sessionInvalidateTime = sessionInvalidateTime;
    }

    public Integer getSessionValidationInterval() {
        return sessionValidationInterval;
    }

    public void setSessionValidationInterval(Integer sessionValidationInterval) {
        this.sessionValidationInterval = sessionValidationInterval;
    }

    public Integer getDelTokenDate() {
        return delTokenDate;
    }

    public GunsProperties setDelTokenDate(Integer delTokenDate) {
        this.delTokenDate = delTokenDate;
        return this;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getMasterSecret() {
        return masterSecret;
    }

    public void setMasterSecret(String masterSecret) {
        this.masterSecret = masterSecret;
    }
}
