package cn.future.core.util;

import com.alibaba.fastjson.JSONObject;

import java.net.NetworkInterface;
import java.util.*;

public class AppUtil {

    public static Date addDay(int num) {
        Date d = new Date();
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, num);
        d = ca.getTime();
        return d;
    }

    public static void main(String args[]){
        System.out.print(getNewMac());
    }

    /**
     * 通过网络接口取
     * @return
     */
    public static String getNewMac() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return null;
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 公用错误提示
     * @return
     */
    public static Map<String, Object> msgMap(int code, String msg, Map<String,Object> map){
        Map<String,Object> newMmap = new HashMap<String,Object>();
        newMmap.put("code", code);
        newMmap.put("msg", msg);
        newMmap.put("data", map);
        return newMmap;
    }
}
