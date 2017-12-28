package com.test.dao.master;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TestMapper {

	void saveHtmlInfo(@Param("url") String url,@Param("htmlString") String htmlString,@Param("time") Date time);

	
}
