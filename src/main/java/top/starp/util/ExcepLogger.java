package top.starp.util;

import lombok.extern.slf4j.Slf4j;
//import org.apache.log4j.Logger;

//import com.sicnu.yudidi.crawler.CrawlerWithCookie;

@Slf4j
public class ExcepLogger {

//	private final static Logger log = Logger.getLogger(CrawlerWithCookie.class);
	public synchronized static void log(Exception e) {
		log.error("=================一个异常的信息===================");
		log.error("简要异常信息: "+e.getMessage());
		log.error("异常堆栈信息:");
//		for (StackTraceElement elem : e.getStackTrace()) {
//			log.error(elem);
//		}
		e.printStackTrace();
	}
	public synchronized static void log(Exception e,String info) {
		log.error("=================一个异常的信息===================");
		log.error("其他信息: "+info);
		log.error("简要异常信息: "+e.getMessage());
		log.error("异常堆栈信息:");
//		for (StackTraceElement elem : e.getStackTrace()) {
//			log.error(elem);
//		}
		e.printStackTrace();
	}
}
