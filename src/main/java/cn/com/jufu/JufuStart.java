package cn.com.jufu;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.jufu.utils.HttpClientUtils;
import cn.com.jufu.utils.PageCrawlerUtil;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.UnexpectedPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class JufuStart {
	private final static Logger log = LoggerFactory.getLogger(JufuStart.class);
	private static String username = "158745";
	private static String password = "wgf158745";
	public static void main(String[] args) {
		
	}
	public void run(){
		WebClient webClient=creatWebClient();
		WebRequest webrqRequest = null;
		
			try{
				WebClient client = HttpClientUtils.getWebClient();
				CookieManager cookieManager = new CookieManager();
				Cookie cookie = new Cookie("www.vim6.com",".ASPXAUTH", "80C61DBD8FDD4B58335FF4EFDF0652C0EE2624091F43ECEE6FB5860A7E2ED582744A3D2671426ADC8ED38F355F3FFFF6483DD8C2837A1D811A9D47D1173228716FC38B21A3C54217B252AF887B053465B0B48C5EFB53FA3E235FE84337B45DF1");
				cookieManager.addCookie(cookie);
				cookie = new Cookie("www.vim6.com", "ASP.NET_SessionId","jr4hw2wpnbpandsh10eoijin");
				cookieManager.addCookie(cookie);
				log.info("开始尝试Cookie登陆");

				// 登陆
				webClient.setCookieManager(cookieManager);
				HtmlPage page1 = webClient.getPage("http://www.vim6.com/UserManage/GetCode.html");
				if(!page1.asText().contains("释放所有号码")){
					log.info("login fail");
					return;
				}
				log.info("login success");
					String mobile = "";
					boolean flag=true;
					while(true){
					if(flag){
						HtmlAnchor anchor = (HtmlAnchor) page1.getElementById("btnGetPhone");
						anchor.click();
						
						HtmlTextInput text = (HtmlTextInput) page1.getElementById("txtPhone");
						if(text.asText().isEmpty()){
							anchor.click();
							text = (HtmlTextInput) page1.getElementById("txtPhone");
						}
						webClient.getCookieManager().setCookiesEnabled(true);
						webClient.addRequestHeader("Host", "www.vim6.com");
						webClient.addRequestHeader("Origin", "http://www.vim6.com");
						webClient.addRequestHeader("Accept-Encoding","gzip, deflate");
						webClient.addRequestHeader("Accept", "*/*");
						webClient.addRequestHeader("Accept-Language","zh-CN,zh;q=0.8");
						webClient.addRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
						webClient.addRequestHeader("Referer","http://www.vim6.com/UserManage/GetCode.html");
						webClient.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36");
						webrqRequest = new WebRequest(new URL("http://www.vim6.com/UserManage/GetPhone"), HttpMethod.POST);
						List<NameValuePair> nl = new ArrayList<NameValuePair>();
						NameValuePair nvp3 = new NameValuePair("assignPhone", "");
						nl.add(nvp3);
						NameValuePair nvp4 = new NameValuePair("categoryId", "38");
						nl.add(nvp4);
						NameValuePair nvp2 = new NameValuePair("mobileType", "");
						nl.add(nvp2);
						NameValuePair nvp5 = new NameValuePair("province", "");
						nl.add(nvp5);
						NameValuePair nvp6 = new NameValuePair("city", "");
						nl.add(nvp6);
						webrqRequest.setRequestParameters(nl);
						UnexpectedPage lmp = webClient.getPage(webrqRequest);
						mobile = lmp.getWebResponse().getContentAsString();
//						HtmlPage paged = client.getPage("http://www.maz8.com/UserManage/GetCode.html");
						log.info("moblie:"+mobile);
					}
					if(flag){
						webClient.getCookieManager().setCookiesEnabled(true);
						webClient.addRequestHeader("Host", "www.6yzm.com");
						webClient.addRequestHeader("Origin", "http://www.6yzm.com");
						webClient.addRequestHeader("Accept-Encoding","gzip, deflate");
						webClient.addRequestHeader("Accept", "*/*");
						webClient.addRequestHeader("Accept-Language","zh-CN,zh;q=0.8");
						webClient.addRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
						webClient.addRequestHeader("Referer","http://www.6yzm.com/UserManage/GetCode.html");
						webClient.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36");
						webrqRequest = new WebRequest(new URL("http://www.vim6.com/UserManage/AddBlack"), HttpMethod.POST);
						List<NameValuePair> nl = new ArrayList<NameValuePair>();
						NameValuePair nvp3 = new NameValuePair("phone", mobile);
						nl.add(nvp3);
						NameValuePair nvp4 = new NameValuePair("categoryId", "38");
						nl.add(nvp4);
						webrqRequest.setRequestParameters(nl);
						UnexpectedPage lmp = webClient.getPage(webrqRequest);
						String result = lmp.getWebResponse().getContentAsString();
//						HtmlPage paged = client.getPage("http://www.maz8.com/UserManage/GetCode.html");
						if(result.equals("true")){
							log.info("add black success");
						}else{
							log.info("add black fail");
						}
						
						
					}
					if(mobile.endsWith("No_Lock")){
						webClient.getCookieManager().setCookiesEnabled(true);
						webClient.addRequestHeader("Host", "www.6yzm.com");
						webClient.addRequestHeader("Origin", "http://www.6yzm.com");
						webClient.addRequestHeader("Accept-Encoding","gzip, deflate");
						webClient.addRequestHeader("Accept", "*/*");
						webClient.addRequestHeader("Accept-Language","zh-CN,zh;q=0.8");
						webClient.addRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
						webClient.addRequestHeader("Referer","http://www.6yzm.com/UserManage/GetCode.html");
						webClient.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36");
						webrqRequest = new WebRequest(new URL("http://www.vim6.com/UserManage/ReleasePhone"), HttpMethod.POST);
						UnexpectedPage lmp = webClient.getPage(webrqRequest);
						String result = lmp.getWebResponse().getContentAsString();
						if(result.equals("true")){
							log.info("release phone success");
						}else {
							log.info("release phone fail");
						}
						
					}
					PageCrawlerUtil.sleepMSecord(6000);
					}
			}catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	private static WebClient creatWebClient(){
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		WebClientOptions option = webClient.getOptions();
		option.setCssEnabled(false);
		option.setThrowExceptionOnScriptError(false);
		option.setJavaScriptEnabled(false);
		option.setRedirectEnabled(true);
		option.setActiveXNative(false);
		option.setTimeout(30000);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
//		try {
//			webClient.setUseInsecureSSL(true);
//		} catch (GeneralSecurityException e) {
//			e.printStackTrace();
//		}
		return webClient;
	}
}
