package cn.future.core.util;

import java.io.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.sf.json.JSONObject;

public class RestUtils {

    public static String  getToken() throws Exception{
        String url = "http://oa.getda.gov.cn:9006/seeyon/rest/token";
        URL restServiceURL = new URL(url);
        HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
        httpConnection.setRequestMethod("POST");
        httpConnection.setRequestProperty("Accept", "application/json");
        httpConnection.setRequestProperty("Content-Type", "application/json");
        httpConnection.setDoOutput(true);
        httpConnection.setDoInput(true);

        OutputStream outputStream = httpConnection.getOutputStream();
        String input = "{\"userName\":\"hr\",\"password\":\"Aa123456\"}";
        outputStream.write(input.getBytes());
        outputStream.flush();


        BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));
        String end ="";
        String output;
        System.out.println("Output from Server:  \n");
        while ((output = responseBuffer.readLine()) != null) {

            end+=output;

        }
        System.out.println("end=="+end);
        // 将json字符串转换成jsonObject
        JSONObject jsonObject=JSONObject.fromObject(end);
        System.out.println(jsonObject.getString("id"));
        Iterator ite = jsonObject.keys();
        // 遍历jsonObject数据,添加到Map对象
        String value ="";
        while (ite.hasNext()) {
            String key = ite.next().toString();
            value= jsonObject.get(key).toString();

        }
        httpConnection.disconnect();
        return value;
    }
    public static void createPost(){
        try{
            String token = getToken();

            HashMap params = new HashMap();
            //表单流程的模板编号
            params.put("orgAccountId", "-1127199977829347812");
            //附件id数组
            params.put("code", "111111");
            //发起人登录名
            params.put("name", "测试");
            //协同标题
            params.put("description","1111");
            //xml的数据
            params.put("typeId", 1);

            System.out.println(params.toString());
            String url = "http://oa.getda.gov.cn:9006/seeyon/rest/orgPost";
            URL restServiceURL = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.setRequestProperty("token", token);
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);

            OutputStream outputStream = httpConnection.getOutputStream();

            ObjectMapper objectMapper = new ObjectMapper();
            String input = objectMapper.writeValueAsString(params);
            System.out.println(input);
            outputStream.write(input.getBytes());
            outputStream.flush();

            System.out.println(httpConnection.getResponseCode());

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));
            String output;
            System.out.println("Output from Server:  \n");
            while ((output = responseBuffer.readLine()) != null) {
                System.out.println(output);
            }
            httpConnection.disconnect();
        }catch(Exception e ){
            e.printStackTrace();
            System.out.println(e);
        }
    }
    public static void updatePost(){
        try{
            String token = getToken();
            HashMap params = new HashMap();
            Map<String,String> fileMap = new HashMap<String,String>();
            fileMap.put("field", "code");
            params.put("id", fileMap);
            //附件id数组
            params.put("code", "111111");
            //发起人登录名
            params.put("name", "测试2222");
            //协同标题
            params.put("description","12111");
            //xml的数据
            params.put("typeId", 2);

            System.out.println(params.toString());
            String url = "http://oa.getda.gov.cn:9006/seeyon/rest/orgPost";
            URL restServiceURL = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod("PUT");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.setRequestProperty("token", token);
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);

            OutputStream outputStream = httpConnection.getOutputStream();

            ObjectMapper objectMapper = new ObjectMapper();
            String input = objectMapper.writeValueAsString(params);
            System.out.println(input);
            outputStream.write(input.getBytes());
            outputStream.flush();

            System.out.println(httpConnection.getResponseCode());

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));
            String output;
            System.out.println("Output from Server:  \n");
            while ((output = responseBuffer.readLine()) != null) {
                System.out.println(output);
            }
            httpConnection.disconnect();
        }catch(Exception e ){
            e.printStackTrace();
            System.out.println(e);
        }
    }
    public static void delPost(){
        try{
            String token = getToken();

            HashMap params = new HashMap();
            System.out.println(params.toString());
            String url = "http://oa.getda.gov.cn:9006/seeyon/rest/orgPost/code/111111";
            URL restServiceURL = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod("DELETE");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.setRequestProperty("token", token);
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);


            System.out.println(httpConnection.getResponseCode());

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));
            String output;
            System.out.println("Output from Server:  \n");
            while ((output = responseBuffer.readLine()) != null) {
                System.out.println(output);
            }
            httpConnection.disconnect();
        }catch(Exception e ){
            e.printStackTrace();
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
//	   RestUtils.updatePost();
        RestUtils.delPost();
    }

}