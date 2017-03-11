package cn.com.jufu.utils;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * webclient宸ュ叿绫�
 * @author EX-YANGKEKE001
 */
public class WebClientUtil {

	private static final Logger log = LoggerFactory.getLogger(WebClientUtil.class);
	private static final String useProxy = "1";
	
	//鑾峰彇缃戦〉澶辫触娆℃暟
	public static int ERRORNUM = 5;

	public static void main(String[] args){
		WebClient wc = getWebClient();
		try {
			HtmlPage page = wc.getPage("http://bj.esf.sina.com.cn/community/a1-b13/");
			DomNode node = (DomNode) page.getByXPath("//div[@id='searchmain_c_1']/div[@class='search_item_list_area']/div[@class='lists']/ul/li[10]/div[@class='topline']/div[@class='details']/div[@class='title']/a").get(0);
			System.out.println(node.asXml());
		}catch (Exception e) {
			e.printStackTrace();
		}
		/*List<String> urls = new ArrayList<String>();
		String js_1 = "http://t.uctrac.com/js/tc.js";
		String js_2 = "http://hm.baidu.com/h.js?c5899c8768ebee272710c9c5f365a6d8";
		urls.add(js_1);
		urls.add(js_2);
		if(urls.contains(".js")){
			System.out.println(":缁撴潫瀛樺湪.js");
		}
		if(urls.contains("?c5899c8768ebee272710c9c5f365a6d8")){
			System.out.println(js_2+":缁撴潫瀛樺湪.js");
		}*/
		
	}
	
	
	/**
	 * 鍒涘缓webclient瀵硅薄:鏈姞杞絁S锛岄渶瑕佸姞杞絁S鍙互寰楀埌webclient瀵硅薄鍚庨噸鏂拌缃紝閫氳繃閰嶇疆鏂囦欢鍒ゆ柇鏄惁闇�瑕佷娇鐢ㄤ唬鐞�
	 * @return
	 */
	public static WebClient getWebClient()
	{
		WebClient webClient = new WebClient();
		WebClientOptions option = webClient.getOptions();
		option.setCssEnabled(false);
		option.setThrowExceptionOnScriptError(false);
		option.setRedirectEnabled(true);
		option.setActiveXNative(false);
		option.setTimeout(3000000);
		option.setThrowExceptionOnFailingStatusCode(false);
		//濡傛灉璁剧疆JavaScriptEnabled涓篢RUE鏃堕渶瑕佽缃秴鏃舵椂闂达紝鍚﹀垯浼氬嚭鐜板唴瀛樻孩鍑虹殑鎯呭喌锛岄粯璁avaScriptEnabled涓篢RUE
		option.setJavaScriptEnabled(false);
//		webClient.setJavaScriptTimeout(15000); 
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setUseInsecureSSL(true);
		//璁剧疆鏈�澶х紦瀛樻暟涓�0 榛樿涓�25--鎺у埗CPU浣跨敤鐜�
		webClient.getCache().setMaxSize(0);
		return webClient;
	}
	
	
	/**
	 * 鍒涘缓webclient瀵硅薄:浣跨敤浠ｇ悊鏂瑰紡,鍦ㄤ娇鐢ㄦ椂闇�瑕佹敞鎰忕鍙ｅ彿淇濇寔涓�鑷达紱鏈姞杞絁S
	 * @return
	 */
	public static WebClient getProxyWebClient(){
		WebClient webClient = new WebClient();
		WebClientOptions option = webClient.getOptions();
		option.setCssEnabled(false);
		option.setThrowExceptionOnScriptError(false);
		option.setRedirectEnabled(true);
		option.setActiveXNative(false);
		option.setTimeout(30000);
		option.setThrowExceptionOnScriptError(false);
		//濡傛灉璁剧疆JavaScriptEnabled涓篢RUE鏃堕渶瑕佽缃秴鏃舵椂闂达紝鍚﹀垯浼氬嚭鐜板唴瀛樻孩鍑虹殑鎯呭喌锛岄粯璁avaScriptEnabled涓篢RUE
		//濡傛灉涓嶅姞杞絁S涓嶅奖鍝嶉〉闈㈢殑
		option.setJavaScriptEnabled(false);
		//webClient.setJavaScriptTimeout(15000); 
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		//璁剧疆鏈�澶х紦瀛樻暟涓�0 榛樿涓�25--鎺у埗CPU浣跨敤鐜�
		webClient.getCache().setMaxSize(0);
		//浣跨敤浠ｇ悊 鍦ㄦ湰鍦版祴璇曟椂闇�瑕佹敞鎰忎慨鏀瑰皢璁剧疆鐨勪唬鐞嗙殑绔彛鍙峰拰webclient涓殑浠ｇ悊绔彛鍙蜂繚鎸佷竴鐩�
		ProxyConfig proxyConfig = new ProxyConfig("127.0.0.1", 8087);
		option.setProxyConfig(proxyConfig);
		return webClient;
	}

	/**
	 * 浣跨敤webclient鏂瑰紡鑾峰彇椤甸潰瀵硅薄锛氳繑鍥炵粨鏋滃彲鑳戒负null
	 * @param url
	 * @param wc
	 * @return
	 */
	public static Document getDocumentByWebClient(String url, WebClient wc)
	{
		int errorRun = 0;
		Document doc = null;
		try {
			//闅忔満浼戠湢2~7绉�
			while(true)
			{
				try {
					HtmlPage htmlPage = wc.getPage(url);
					wc.waitForBackgroundJavaScript(5000);
					doc = Jsoup.parse(htmlPage.asXml());
					break;
				} catch (Exception e) {
					errorRun++;
					e.printStackTrace();
					log.info("====================: 绗� " + errorRun + " 娆★紝缃戠粶杩炴帴寮傚父,璁块棶URL:" + url);
					if(errorRun == ERRORNUM){
						log.error("============: 缃戠粶杩炴帴寮傚父,璁块棶URL:" + url + "锛屽凡缁堟璁块棶锛�", e);
						break;
					}
					Thread.sleep((long)(Math.random()*5000) + 2000);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	/**
	 * 浣跨敤webclient鏂瑰紡鑾峰彇椤甸潰瀵硅薄锛氳繑鍥炵粨鏋滃彲鑳戒负null
	 * @param url
	 * @param wc
	 * @return
	 */
	public static HtmlPage getHtmlPage(String url, WebClient wc)
	{
		int errorRun = 0;
		HtmlPage htmlPage = null;
		try {
			while(true)
			{
				try {
					htmlPage = wc.getPage(url);
					break;
				} catch (Exception e) {
					errorRun++;
					log.info("====================: 绗� " + errorRun + " 娆★紝缃戠粶杩炴帴寮傚父,璁块棶URL:" + url);
					if(errorRun == ERRORNUM){
						log.error("============: 缃戠粶杩炴帴寮傚父,璁块棶URL:" + url + "锛屽凡缁堟璁块棶锛�", e);
						break;
					}
					//闅忔満浼戠湢2~7绉�
					Thread.sleep(Math.round(Math.random()*14+1)*1000);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return htmlPage;
	}
	
	/**
	 * 浣跨敤webclient鏂瑰紡鑾峰彇椤甸潰瀵硅薄锛氳繑鍥炵粨鏋滃彲鑳戒负null
	 * @param url
	 * @param wc
	 * @param jsFlag 鏄惁鍔犺浇js
	 * @return
	 */
	public static HtmlPage getHtmlPage(String url, WebClient client, boolean jsFlag)
	{
		int errorRun = 0;
		HtmlPage htmlPage = null;
		//鍒ゆ柇鏄惁闇�瑕佸姞杞絡s
		boolean javaScriptEnabled = client.getOptions().isJavaScriptEnabled();
		if(jsFlag){
			//鍒ゆ柇鏄惁闇�瑕佸姞杞絡s
			if(!javaScriptEnabled){
				client.getOptions().setJavaScriptEnabled(true);
				client.setJavaScriptTimeout(5000);
			}
		}
		try {
			while(true)
			{
				try {
					htmlPage = client.getPage(url);
					break;
				} catch (Exception e) {
					errorRun++;
					log.info("====================: 绗� " + errorRun + " 娆★紝缃戠粶杩炴帴寮傚父,璁块棶URL:" + url);
					if(errorRun == ERRORNUM){
						log.error("============: 缃戠粶杩炴帴寮傚父,璁块棶URL:" + url + "锛屽凡缁堟璁块棶锛�", e);
						break;
					}
					//闅忔満浼戠湢2~7绉�
					Thread.sleep(Math.round(Math.random()*14+1)*1000);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//灏嗚缃姞杞絡s灞炴�у�艰繕鍘�
			if(jsFlag){
				boolean curJSEnabled = client.getOptions().isJavaScriptEnabled();
				if(!(curJSEnabled&&javaScriptEnabled)){
					client.getOptions().setJavaScriptEnabled(javaScriptEnabled);
				}
			}
		}
		return htmlPage;
	}
	
	/**
	 * 閫氳繃HtmlPage鑾峰彇Json瀛楃涓�
	 *
	 * ex-limin002 2014-2-27 
	 * 
	 * @param url
	 * @param wc
	 * @return
	 * @return String
	 * @throws
	 */
	public static String getHtmlPageForJson(String url, WebClient wc)
	{
		int errorRun = 0;
		String jsonStr = "";
		try {
			while(true)
			{
				try {
					jsonStr = wc.getPage(url).getWebResponse().getContentAsString();;
					break;
				} catch (Exception e) {
					errorRun++;
					log.info("====================: 绗� " + errorRun + " 娆★紝缃戠粶杩炴帴寮傚父,璁块棶URL:" + url);
					if(errorRun == ERRORNUM){
						log.error("============: 缃戠粶杩炴帴寮傚父,璁块棶URL:" + url + "锛屽凡缁堟璁块棶锛�", e);
						break;
					}
					//闅忔満浼戠湢2~7绉�
					Thread.sleep(Math.round(Math.random()*14+1)*1000);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 浣跨敤Jsoup鏃讹紝鍙互浣跨敤璇ユ柟娉曚娇鐢ㄤ唬鐞嗚幏鍙栭〉闈俊鎭�
	 * @param proxyIP
	 * @param proxyPort
	 */
	public static void jsoupProxy(String proxyIP, String proxyPort){  

		System.getProperties().setProperty("proxySet", "true");    
		//鐢ㄧ殑浠ｇ悊鏈嶅姟鍣�    
		System.getProperties().setProperty("http.proxyHost", proxyIP);    
		//浠ｇ悊绔彛    
		System.getProperties().setProperty("http.proxyPort", proxyPort);  
	} 
}
