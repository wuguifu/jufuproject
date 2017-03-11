package cn.com.jufu.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HttpClientUtils {

	private static final Logger log = LoggerFactory.getLogger(HttpClientUtils.class);
	
	public static final String REFERRER = "http://www.10jqka.com.cn/" ; //引用
	
	public static boolean JAVASCRPIT_ENABLED = true; //是否加载js
	public static boolean CSS_ENABLED = true; //是否加载css
	public static boolean THROWEXCEPTION_ONFAILING_STATUSCODE = true; //是否抛出状态码异常
	public static boolean THROWEXCEPTI_ONONSCRIPTERROR = false; //是否抛出脚本加载异常
	public static boolean REDIRECT_ENABLED = true; //是否重定向
	public static boolean ACTIVEXNATIVE = true; //是否加载activeX
	public static boolean POPUPBLOCKER_ENABLED = true ;
	public static int TIMEOUT = 1000 * 90 * 2; //连接超时
	public static int JAVASCRPIT_TIMEOUT = 1000 * 60 * 2; //加载js超时
	public static int RUNNING_COUNT = 6 ; //请求次数
	public static boolean useProxy = false ; //是否使用代理 默认不使用代理
    //代理
	public static String PROXYHOST = ""; //代理ip
	public static int PROXYPOST = 0; //代理端口
	
	public static WebClient webClient = null ;
	
	
	public static WebClient getWebClient() {
		return webClient;
	}


	public static void setWebClient(WebClient webClient) {
		HttpClientUtils.webClient = webClient;
	}


	static {
		webClient = creatWebClient() ; //初始化webClient
		//ssl配置
//		if(false){
//			try {
//				webClient.setUseInsecureSSL(true) ; //判断是否访问ssl
//			} catch (GeneralSecurityException e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	/**
	 * 功能说明
	 *
	 * guixin.chen 2014-7-24 
	 * 
	 * @param args
	 * @return void
	 * @throws
	 */
	public static void main(String[] args) {
	}
	
	
	/**
	 * 
	 * 通过Jsoup来抓取网页
	 *
	 * guixin.chen 2014-7-24 
	 * 
	 * @param url
	 * @return
	 * @return Document
	 * @throws
	 */
	public static Document getJsoupPage(String url){
		int count = 0;
		Document doc = null;
		while (count < RUNNING_COUNT - 1) {
			count++;
			try {
				String referrer = REFERRER;
				doc = Jsoup
						.connect(url).ignoreContentType(true)
						.userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)")
						.referrer(referrer).timeout(1000 * 10 * 5).get();
				PageCrawlerUtil.sleepSecord((long) (Math.random() * 5) + 5);
				return doc;
			} catch (Exception e) {
				String message = e.getMessage().trim();
				log.error("第 " + count
						+ " 次，webClient访问网络连接异常,访问URL:" + url + "，原因："
						+ message);

				if (message.startsWith("403")) {// IP被禁止访问
					log.info("本机IP被禁止访问，休眠30分钟...");
					PageCrawlerUtil.sleepSecord(60 * 30);
				}
				PageCrawlerUtil.sleepSecord(20);
			}
		}
		return Jsoup.parse("<div></div>");
	
	} 
	
	
	/**
	 * 
	 * 功能说明 WebClient抓取网页
	 *
	 * EX-WANGWENTAO001 2014-7-24 
	 * 
	 * @param url
	 * @return
	 * @return Document
	 * @throws
	 */
	public static Document getWebClientPage(String url) {
		int count = 0;
		while(count < RUNNING_COUNT - 1){
			count++;
			try {
				HtmlPage page = webClient.getPage(url);
				int statuscode = page.getWebResponse().getStatusCode();
				PageCrawlerUtil.sleepMSecord((long)(Math.random()*100) + 5000);
				if(statuscode == 200){
					return Jsoup.parse(page.asXml()) ;
				}else{
					log.info("第 " + count + " 次，webClient访问网络连接异常,访问URL:" + url+"，statuscode：" + statuscode);
				}
			} catch (Exception e) {
				String message = e.getMessage().trim();
				log.error("第 " + count + " 次，webClient访问网络连接异常,访问URL:" + url+"，原因：" + message);
				
				if(message.startsWith("403")){//IP被禁止访问
					log.info("本机IP被禁止访问，休眠30分钟...");
					PageCrawlerUtil.sleepSecord(60*30);
				}
				PageCrawlerUtil.sleepSecord(20);
			}
		}
		return Jsoup.parse("<div></div>");
	}
	/**
	 * 
	 * 功能说明
	 *
	 * EX-WANGWENTAO001 2014-7-24 
	 * 
	 * @return
	 * @return WebClient
	 * @throws
	 */
	public static WebClient creatWebClient() {
		WebClient webClient = new WebClient();
		WebClientOptions option = webClient.getOptions();
		option.setCssEnabled(CSS_ENABLED);
		option.setThrowExceptionOnScriptError(THROWEXCEPTI_ONONSCRIPTERROR);
		option.setThrowExceptionOnFailingStatusCode(false) ;
		option.setRedirectEnabled(REDIRECT_ENABLED);
		option.setPopupBlockerEnabled(POPUPBLOCKER_ENABLED);
		option.setActiveXNative(ACTIVEXNATIVE);
		option.setJavaScriptEnabled(JAVASCRPIT_ENABLED);
		option.setRedirectEnabled(REDIRECT_ENABLED);
		option.setTimeout(TIMEOUT);
		webClient.waitForBackgroundJavaScriptStartingBefore(20000);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.setJavaScriptTimeout(JAVASCRPIT_TIMEOUT);
		// 设置最大缓存数为0 默认为25--控制CPU使用率

//		 ProxyConfig proxyConfig = new ProxyConfig("103.10.231.99", 80);
//		 webClient.setProxyConfig(proxyConfig);
		webClient.waitForBackgroundJavaScript(2000);
		
		webClient.getCache().setMaxSize(0);
		
		return webClient;
	}

}
