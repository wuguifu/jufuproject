package cn.com.jufu.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
@Service("pageCrawlerUtil")
public class PageCrawlerUtil {
	private static final Logger log = LoggerFactory.getLogger(PageCrawlerUtil.class);
	public static Properties prop=new Properties();
	public static Map<String, String> txtConfigValueMap = new HashMap<String, String>();
	public Map<String,Long>  map1 = new HashMap<String,Long>();
	static{
		try {
			prop.load(PageCrawlerUtil.class.getClassLoader().getResourceAsStream("properties-wz.properties"));
			prop.load(PageCrawlerUtil.class.getClassLoader().getResourceAsStream("properties.properties"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getConfigValue(String key){
		if(prop.getProperty(key)!=null){
			return prop.getProperty(key).trim();
		}else{
			return "";
		}
	}





	public static void waiting(long second) throws Exception{
		try {
			Thread.sleep(second*1000l);
		} catch (Exception e) {
			throw e;
		}
	}

	public static void sleepSecord(long second){
		try {
			Thread.sleep(second*1000);
		} catch (Exception e) {
		}
	}

	public static void sleepMSecord(long second){
		try {
			Thread.sleep(second);
		} catch (Exception e) {
		}
	}



	public static void waitSecord(Thread thread, long second) {
		synchronized (thread) {
			try {
				if (second <= 0) {
					thread.wait();
				} else {
					thread.wait(second * 1000);
				}
			} catch (Exception e) {
			}
		}
	}

	public static String getPageContent(String xPath, HtmlPage htmlPage)
	throws Exception {
		String pageContent = null;
		List inputList = htmlPage.getByXPath(xPath);
		if (inputList != null) {
			Object content = inputList.get(0);
			if (content != null)
				pageContent = content.toString();
		}
		return pageContent;
	}

	public static HtmlPage clickAhchor(String xPath, HtmlPage htmlPage)
	throws Exception {
		HtmlPage page = null;
		List inputList = htmlPage.getByXPath(xPath);
		if (inputList != null && inputList.size() > 0) {
			HtmlAnchor hi = (HtmlAnchor) inputList.get(0);
			log.info(hi.toString());
			page = hi.dblClick();
			waiting(1);
		}
		return page;
	}

	public static byte[] lock = new byte[0];
	public static ConcurrentLinkedQueue<HashMap<String,String>> queue = new ConcurrentLinkedQueue<HashMap<String,String>>();
	public synchronized void saveToFile(Thread thr_,HashMap<String,String> map){
		if(queue.size()>40){
			try {
				thr_.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	class toDoSaveFile extends Thread{
		public void run(){
		}
	}

	/**
	 * 23鐐�50鍒�0鐐圭殑鏃跺�欙紝鍋滄10鍒嗛挓
	 * @return
	 */
	public static boolean isStopCrawler(){
		if(DateUtil.getCurrentHour()==23 && DateUtil.getCurrentMinute()>50){
			return true;
		}
		return false;
	}
	public static ExecutorService pool = Executors.newFixedThreadPool(20);
	public static void colseInputStream(InputStream in)
	{
		if(null == in)
		{
			return;
		}

		try
		{
			in.close();
		}
		catch(Exception e)
		{
			log.error("鍏抽棴InputStream澶辫触",e);
		}
	}

	public static void colseOutputStream(OutputStream out)
	{
		if(null == out)
		{
			return;
		}

		try
		{
			out.close();
		}
		catch(Exception e)
		{
			log.error("鍏抽棴OutputStream澶辫触",e);
		}
	}
	public static void colseBufferedOutputStream(BufferedOutputStream fc)
	{
		if(null == fc)
		{
			return;
		}

		try
		{
			fc.close();
		}
		catch(Exception e)
		{
			log.error("鍏抽棴BufferedOutputStream澶辫触",e);
		}
	}


	public static List<String> readListFromFile(String filePath){
		List<String> returnList = new ArrayList<String>();
		InputStreamReader isr = null;
		BufferedReader reader = null;
		try {
			isr = new InputStreamReader(
					new FileInputStream(filePath), "UTF-8");
			reader = new BufferedReader(isr);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				if(!"".equals(tempString)){
					returnList.add(tempString);
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {}
			}
			if(isr != null){
				try {
					isr.close();
				} catch (Exception e2) {}
			}
		}
		return returnList;
	}

	public static String getMidString(String content, String startString,
			String endString, int startIndex) {
		int start = startIndex;
		int s = content.indexOf(startString, start);
		if (s < 0)
			return "";
		start = s + startString.length();
		String midString = content.substring(start,content.indexOf(endString, start));
		return midString;
	}


	@SuppressWarnings("deprecation")
	public static void cleanWebClient(WebClient webClient)
	{
		if (null != webClient) {
			try {
				log.info("鍏抽棴灏卞凡鎵撳紑绐楀彛:"+webClient.getWebWindows().size());
				webClient.closeAllWindows();
				// 娓呯悊缂撳瓨
				//webClient.getCache().clear();
				//cleanWebClientBySize(webClient);
			} catch (Exception ex) {
				log.info("cleanWebClient娓呯悊缂撳瓨寮傚父!");
			}
		}
	}


	@SuppressWarnings("deprecation")
	public static void cleanWebClientBySize(WebClient webClient)
	{	
		//涓轰簡瑙ｅ喅鍐呭瓨婧㈠嚭闂锛屽綋缂撳瓨>=鐨勬椂鍊欐墠寮�濮嬫竻鐞�
		if(null != webClient)
		{
			log.info("WebClient缂撳瓨涓猴細" + webClient.getCache().getSize() + "---------宸叉竻闄�!!!");
			webClient.getCache().clear();
			webClient.closeAllWindows();
		}
	}


	public static String getDirectoryPath(int type)
	{
		String path = "";
		switch(type)
		{
		case 0:
			path =  PageCrawlerUtil.getConfigValue("crawler.work.file.path");
			break;
		case 1:
			path =  PageCrawlerUtil.getConfigValue("crawler.fail.file.path");
			break; 
		case 2:
			path =  PageCrawlerUtil.getConfigValue("crawler.seed.file.path");
			break; 
		case 3:
			path =  PageCrawlerUtil.getConfigValue("parser.backup.path");
			break; 
		case 4:
			path =  PageCrawlerUtil.getConfigValue("parser.fail.path");
			break;
		case 5:
			path =  PageCrawlerUtil.getConfigValue("parse.file.save.tmp.path");
			break;
		case 6:
			path =  PageCrawlerUtil.getConfigValue("parse.file.save.path");
			break;
		case 7:
			path =  PageCrawlerUtil.getConfigValue("parser.backup.path");
			path = path+"_sp";
			break;
		case 8:
			path =  PageCrawlerUtil.getConfigValue("parser.fail.path");
			path = path+"_sp";
			break;
		case 9:
			path =  PageCrawlerUtil.getConfigValue("parse.file.save.path");
			path = path+"_sp";
			break;
		case 10:
			path =  PageCrawlerUtil.getConfigValue("crawler.work.file.path");
			path = path+"_sp";
			break;
		case 11:
			path =  PageCrawlerUtil.getConfigValue("crawler.zip.download.folder");
			break;
		case 12:
			path =  PageCrawlerUtil.getConfigValue("crawler.zip.backup.folder");
			break;
		case 13:
			path =  PageCrawlerUtil.getConfigValue("crawler.zip.download.folder");
			path = path+"_sp";
			break;
		case 14:	   
			path =  PageCrawlerUtil.getConfigValue("crawler.zip.backup.folder");
			path = path+"_sp";
			break;
		case 15:
			path =  PageCrawlerUtil.getConfigValue("crawler.seed.file.path");
			path = path+"_sp";
			break;
		case 16:
			path =  PageCrawlerUtil.getConfigValue("app.zip.download.folder");
			path = path+"sp";
			break;
		}

		return path;
	}







	public static int getApiTimes(){
		int minute = DateUtil.getCurrentMinute();
		if(minute<=10){
			return 1;
		}else if(minute<=20){
			return 2;
		}else if(minute<=30){
			return 3;
		}else if(minute<=40){
			return 4;
		}else if(minute<=50){
			return 5;
		}else{
			return 6;
		}
	}


	public static String getExpandServiceAppURL() {
		String serviceAppURL = PageCrawlerUtil.getConfigValue("crawler.seeds.insert.interface.url");
		serviceAppURL = serviceAppURL.substring(0,serviceAppURL.indexOf("crawler/") + 7);
		return serviceAppURL;
	}

	public static boolean isNickNameHost(String requestHost,String configHost){
		//娌℃湁閰嶇疆鏃讹紝榛樿涓嶉檺鍒�
		if(configHost == null || "".equals(configHost)) return true;

		boolean result = false;
		String[] configHosts = configHost.split("&");
		for (String host : configHosts) {
			if(requestHost.equals(host)){
				result = true;
				break;
			}
		}
		return result;
	}


	public static String getTxtConfigValue(String key,String filePath){
		String value = "";
		try {
			File file = new File(filePath);
			String filemodifytime = String.valueOf(file.lastModified());

			String filename = filePath.substring(filePath.lastIndexOf(File.separator)+1);
			String lastmodifytime = txtConfigValueMap.get(filename);

			//濡傛灉鏂囦欢鏈変慨鏀癸紝鍒欒鍙栨枃浠�
			if(!filemodifytime.equals(lastmodifytime) || txtConfigValueMap.get(key) == null){
				List<String> configlist = readListFromFile(filePath);
				for (String line : configlist) {
					if(line.startsWith("#")) continue;
					String val = "";
					String[] array = line.split("=");
					if(array.length == 2) val = array[1];

					txtConfigValueMap.put(array[0], val);
				}
				txtConfigValueMap.put(filename, String.valueOf(filemodifytime));
			}
			value = txtConfigValueMap.get(key);
		} catch (Exception e) {
			log.error("璇诲彇澶╀笅閫氫富鏈洪壌鏉冩枃浠跺彂鐢熷紓甯革紒",e);
		}
		return value;
	}

	public static void main(String args[]) {
		String hostfile = PageCrawlerUtil.getDirectoryPath(15) + File.separator + "host.txt";
		System.out.println(PageCrawlerUtil.getTxtConfigValue("crawler.onlysp.convert.nickname.host", hostfile));
	}

	//褰撳墠灏忔椂鐨勮姹傛鏁�
	public static Map<Integer,Integer> hourMap= null;

	public synchronized static Map<Integer,Integer> getHourMap(){
		if (null == hourMap) {
			hourMap = new HashMap<Integer,Integer>();
			hourMap.put(0, 0);
			hourMap.put(1, 0);
			hourMap.put(2, 0);
			hourMap.put(3, 0);
			hourMap.put(4, 0);
			hourMap.put(5, 0);
			hourMap.put(6, 0);
			hourMap.put(7, 0);
			hourMap.put(8, 0);
			hourMap.put(9, 0);
			hourMap.put(10, 0);
			hourMap.put(11, 0);
			hourMap.put(12, 0);
			hourMap.put(13, 0);
			hourMap.put(14, 0);
			hourMap.put(15, 0);
			hourMap.put(16, 0);
			hourMap.put(17, 0);
			hourMap.put(18, 0);
			hourMap.put(19, 0);
			hourMap.put(20, 0);
			hourMap.put(21, 0);
			hourMap.put(22, 0);
			hourMap.put(23, 0);
		}
		return hourMap;
	}
}
