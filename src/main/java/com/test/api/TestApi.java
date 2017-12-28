package com.test.api;

import org.apache.tomcat.util.http.fileupload.UploadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.test.service.TestService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/test")
public class TestApi {
	private Logger logger = LoggerFactory.getLogger(TestApi.class);

    @Autowired
	private TestService TestService;
	
    @ApiOperation("test")
    @GetMapping("/")
    public String getTest(){
        return "success";
    }
    @ApiOperation("getHtmlInfo")
    @GetMapping("/HtmlInfo")
    public String  getHtmlInfo(@RequestParam("url") String url){
    	
    	return TestService.getHtmlInfo(url);
    	
    }
    
    @ApiOperation("saveHtmlInfo")
    @PostMapping("/HtmlInfo")
    public String  saveHtmlInfo(@RequestParam("url") String url){
    	
    	return TestService.saveHtmlInfo(url);
    	
    }
}
