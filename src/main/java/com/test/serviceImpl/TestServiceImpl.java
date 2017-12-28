package com.test.serviceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.test.dao.master.TestMapper;
import com.test.service.TestService;

import lombok.Data;

@Service
public class TestServiceImpl implements TestService {
	private TestMapper testMapper;

	public static String getPageContent(String strUrl, String strPostRequest,int maxLength) {			  
		  StringBuffer buffer = new StringBuffer();
		  System.setProperty("sun.net.client.defaultConnectTimeout", "5000");
		  System.setProperty("sun.net.client.defaultReadTimeout", "5000");
		  try {
		   URL newUrl = new URL(strUrl);
		   HttpURLConnection hConnect = (HttpURLConnection) newUrl
		     .openConnection();
		  
		   if (strPostRequest.length() > 0) {
		    hConnect.setDoOutput(true);
		    OutputStreamWriter out = new OutputStreamWriter(hConnect
		      .getOutputStream());
		    out.write(strPostRequest);
		    out.flush();
		    out.close();
		   }			   
		   BufferedReader rd = new BufferedReader(new InputStreamReader(
		     hConnect.getInputStream()));
		   int ch;
		   for (int length = 0; (ch = rd.read()) > -1
		     && (maxLength <= 0 || length < maxLength); length++)
		    buffer.append((char) ch);
		   String s = buffer.toString();
		   s.replaceAll("//&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
		   s = delHTMLTag(s);
		   System.out.println(s);
		   rd.close();
		   hConnect.disconnect();
		   return s.toString().trim();
		  } catch (Exception e) {			  
		   return null;
		  }
	}

	public static String delHTMLTag(String htmlStr){ 
	
		  String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; 
		  String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; 
		  String regEx_html="<[^>]+>"; 
		   
		  Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
		  Matcher m_script=p_script.matcher(htmlStr); 
		  htmlStr=m_script.replaceAll(""); 
		   
		  Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
		  Matcher m_style=p_style.matcher(htmlStr); 
		  htmlStr=m_style.replaceAll(""); 
		   
		  Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
		  Matcher m_html=p_html.matcher(htmlStr); 
		  htmlStr=m_html.replaceAll(""); 
		
		//  htmlStr = htmlStr.replaceAll("\n", "");
		//  htmlStr = htmlStr.replaceAll("\t", "");
		  htmlStr = htmlStr.replaceAll("\t|\\r|\n|&nbsp", "");
		  htmlStr = htmlStr.replaceAll("\\s+", " ");
		  return htmlStr.trim(); 
	} 	

	@Override
	public String getHtmlInfo(String url) {
		// TODO Auto-generated method stub
		String htmlString  = getPageContent(url, "post", 100500);
		
		return htmlString;
	}
	
	@Override
	public String saveHtmlInfo(String url) {
		// TODO Auto-generated method stub
		String htmlString  = getPageContent(url, "post", 100500);
		Date now = new Date();
		testMapper.saveHtmlInfo(url,htmlString,now);
		return htmlString;
	}
	
	
}
