/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
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
package cn.future.modular.system.service.impl;

import cn.future.modular.system.service.IPushService;
import cn.hutool.http.HttpUtil;
import cn.future.modular.system.model.Dept;
import cn.future.modular.system.model.Majorposition;
import cn.future.modular.system.model.Rank;
import cn.future.modular.system.model.User;
import cn.future.modular.unified.dao.UninterfaceLogMapper;
import cn.future.modular.unified.dao.UninterfaceMapper;
import cn.future.modular.unified.model.Uninterface;
import cn.future.modular.unified.model.UninterfaceLog;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.stylefeng.roses.core.util.HttpContext.getIp;

/**
 * <p>
 * 推送 服务类
 * </p>
 *
 * @author zp
 */
@Service
public class PushServiceImpl implements IPushService {

    @Resource
    private UninterfaceMapper uninterfaceMapper;

    @Resource
    private UninterfaceLogMapper uninterfaceLogMapper;


    @Override
    public void pushUser(User user, String operation) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("user",  JSONObject.toJSONString(user));
        map.put("operation", operation);
        //访问接口表，获取到所有的推送接口url地址
        List<Uninterface>  uninterfaceList = uninterfaceMapper.selectByName("推送用户接口");
        for(Uninterface uninterface : uninterfaceList){
            //进行推送user
            try {
                long startTime = fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
                String result = HttpUtil.post(uninterface.getUrl(), map);
                long stopTime = fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
                //成功保存
                this.log(uninterface.getId(),String.valueOf(stopTime - startTime), JSONObject.toJSONString(map)+"/接口返回"+result+"</br>接口名称："+uninterface.getName()+"</br>推送地址："+uninterface.getUrl());
            }catch (Exception e) {
               //失败保存（不保存时间就是失败了）
                this.log(uninterface.getId(),null, "推送用户失败"+JSONObject.toJSONString(map)+"</br>接口名称："+uninterface.getName()+"</br>推送地址："+uninterface.getUrl()+"</br>异常信息："+e.toString());
            }
        }

    }

    @Override
    public void pushDept(Dept dept, String operation) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("dept", JSONObject.toJSONString(dept));
        map.put("operation", operation);
        //访问接口表，获取到所有的推送接口url地址
        List<Uninterface>  uninterfaceList = uninterfaceMapper.selectByName("推送部门接口");
        for(Uninterface uninterface : uninterfaceList){
            //进行推送dept
            try {
                long startTime = fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
                String result = HttpUtil.post(uninterface.getUrl(), map);
                long stopTime = fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
                //成功保存
                this.log(uninterface.getId(),String.valueOf(stopTime - startTime), JSONObject.toJSONString(map)+"/接口返回"+result+"</br>接口名称："+uninterface.getName()+"</br>推送地址："+uninterface.getUrl());
            }catch (Exception e) {
                //失败保存（不保存时间就是失败了）
                this.log(uninterface.getId(),null, "推送部门失败"+JSONObject.toJSONString(map)+"</br>接口名称："+uninterface.getName()+"</br>推送地址："+uninterface.getUrl()+"</br>异常信息："+e.toString());
            }
        }
    }

    @Override
    public void pushMajorposition(Majorposition majorposition, String operation) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("majorposition", JSONObject.toJSONString(majorposition));
        map.put("operation", operation);
        //访问接口表，获取到所有的推送接口url地址
        List<Uninterface>  uninterfaceList = uninterfaceMapper.selectByName("推送岗位接口");
        for(Uninterface uninterface : uninterfaceList){
            //进行推送majorposition
            try {
                long startTime = fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
                String result = HttpUtil.post(uninterface.getUrl(), map);
                long stopTime = fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
                //成功保存
                this.log(uninterface.getId(),String.valueOf(stopTime - startTime), JSONObject.toJSONString(map)+"/接口返回"+result+"</br>接口名称："+uninterface.getName()+"</br>推送地址："+uninterface.getUrl());
            }catch (Exception e) {
                //失败保存（不保存时间就是失败了）
                this.log(uninterface.getId(),null, "推送岗位失败"+JSONObject.toJSONString(map)+"</br>接口名称："+uninterface.getName()+"</br>推送地址："+uninterface.getUrl()+"</br>异常信息："+e.toString());
            }
        }
    }

    @Override
    public void pushRank(Rank rank, String operation) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("rank", JSONObject.toJSONString(rank));
        map.put("operation", operation);
        //访问接口表，获取到所有的推送接口url地址
        List<Uninterface>  uninterfaceList = uninterfaceMapper.selectByName("推送职务接口");
        for(Uninterface uninterface : uninterfaceList){
            //进行推送majorposition
            try {
                long startTime = fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
                String result = HttpUtil.post(uninterface.getUrl(), map);
                long stopTime = fromDateStringToLong(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(new Date()));
                //成功保存
                this.log(uninterface.getId(),String.valueOf(stopTime - startTime), JSONObject.toJSONString(map)+"/接口返回"+result+"</br>接口名称："+uninterface.getName()+"</br>推送地址："+uninterface.getUrl());
            }catch (Exception e) {
                //失败保存（不保存时间就是失败了）
                this.log(uninterface.getId(),null, "推送职务失败"+JSONObject.toJSONString(map)+"</br>接口名称："+uninterface.getName()+"</br>推送地址："+uninterface.getUrl()+"</br>异常信息："+e.toString());
            }
        }
    }

    /**
     * 日志
     */
    public void log(Integer uninterfaceid,String time,String message){
        UninterfaceLog uninterfaceLog = new UninterfaceLog();
        uninterfaceLog.setUninterfaceid(uninterfaceid);
        uninterfaceLog.setTime(time);
        uninterfaceLog.setMessage(message);
        uninterfaceLog.setCreatetime(new Date());
        uninterfaceLog.setIp(getIp());
        uninterfaceLogMapper.insert(uninterfaceLog);
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
