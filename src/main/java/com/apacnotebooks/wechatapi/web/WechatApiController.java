package com.apacnotebooks.wechatapi.web;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wx")
public class WechatApiController {
	
	protected static Logger logger = LoggerFactory.getLogger(WechatApiController.class);
	
	private final String TOKEN="apac";
	
	@RequestMapping(value = "/apitokentest.do")
	@ResponseBody
	public String apiTokenTest(String signature, String timestamp, String nonce, String echostr) {
		logger.info("this is [apitokentest.do] start ...");
		logger.info("signature:"+signature+"timestamp:"+timestamp+"nonce:"+nonce+"echostr:"+echostr);
		String result=echostr;
		if (!checkToken(signature, timestamp, nonce)){
			result="ERROR";
		}
		
		logger.info("this is [apitokentest.do] end ...");
		return result;
	}
	
	private boolean checkToken(String signature, String timestamp, String nonce){
		String[] arrTmp={TOKEN, timestamp, nonce};
		Arrays.sort(arrTmp);
		
		StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < arrTmp.length; i++) {  
            sb.append(arrTmp[i]);  
        }  
        String pwd = Encrypt(sb.toString());  
           
        logger.info("signature:"+signature+"timestamp:"+timestamp+"nonce:"+nonce+"pwd:"+pwd); 
          
        if(pwd.trim().equals(signature.trim())){  
            return true;  
        }else{  
            return false;  
        }  
	}
	
	private String Encrypt(String strSrc) {  
        MessageDigest md = null;  
        String strDes = null;  
  
        byte[] bt = strSrc.getBytes();  
        try {  
            md = MessageDigest.getInstance("SHA-1");  
            md.update(bt);  
            strDes = bytes2Hex(md.digest()); //to HexString  
        } catch (NoSuchAlgorithmException e) {  
            System.out.println("Invalid algorithm.");  
            return null;  
        }  
        return strDes;  
    }  
  
    public String bytes2Hex(byte[] bts) {  
        String des = "";  
        String tmp = null;  
        for (int i = 0; i < bts.length; i++) {  
            tmp = (Integer.toHexString(bts[i] & 0xFF));  
            if (tmp.length() == 1) {  
                des += "0";  
            }  
            des += tmp;  
        }  
        return des;  
    }  

}
