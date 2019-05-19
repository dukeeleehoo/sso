package cn.future.modular.system.controller;

import cn.future.config.properties.GunsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

@Controller
@RequestMapping(value="/resource/upload")
public class UploadImageController {

	@Autowired
	private GunsProperties gunsProperties;
	/**
	 * 上传图片
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/images")
	public Map<String, Object> images(MultipartFile upfile, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			// 文件保存目录路径
			String ext = getExt(upfile.getOriginalFilename());
			String fileName = String.valueOf(System.currentTimeMillis()).concat("_").concat(getRandom(6)).concat(".").concat(ext);
			String fileSavePath = gunsProperties.getFileUploadPath();
			upfile.transferTo(new File(fileSavePath + fileName));
			params.put("state", "SUCCESS");
			params.put("url", "/kaptcha/"+fileName);
			params.put("size", upfile.getSize());
			params.put("original", fileName);
			params.put("type", upfile.getContentType());
		} catch (Exception e) {
			params.put("state", "ERROR");
		}
		return params;
	}

	/**
	 * 上传文档
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/file")
	public Map<String, Object> file(MultipartFile upfile, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			// 文件保存目录路径
			String ext = getExt(upfile.getOriginalFilename());
			String fileName = String.valueOf(System.currentTimeMillis()).concat("_").concat(getRandom(6)).concat(".").concat(ext);
			String fileSavePath = gunsProperties.getFileUploadPath();
			upfile.transferTo(new File(fileSavePath + fileName));
			params.put("state", "SUCCESS");
			params.put("url", "/kaptcha/file/"+fileName);
			params.put("size", upfile.getSize());
			params.put("original", fileName);
			params.put("type", upfile.getContentType());
		} catch (Exception e) {
			params.put("state", "ERROR");
		}
		return params;
	}
	
	/** 
     * 获取指定位数的随机数 
     * @param num 
     * @return 
     */  
    public static String getRandom(int num){  
        Random random = new Random();  
        StringBuilder sb = new StringBuilder();  
        for(int i = 0;i < num; i++){  
            sb.append(String.valueOf(random.nextInt(10)));  
        }  
        return sb.toString();  
    }

	/**
	 * 获取名称后缀
	 * @param name
	 * @return
	 */
	public static String getExt(String name){
		if(name == null || "".equals(name) || !name.contains("."))
			return "";
		return name.substring(name.lastIndexOf(".")+1);
	}
}