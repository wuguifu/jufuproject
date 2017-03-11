package cn.com.jufu.utils;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JsoupUtil {
	/**
	 * 根据具体的属性值获取元素信息(解决jsoup对属性值有左右空格时取值不到的情况使用)
	 * @param d				jsoup Document对象
	 * @param elementName	元素名称
	 * @param attrName		元素的属性名称
	 * @param attrVal		属性值
	 * @return
	 */
	public static Elements select(Document d,String elementName,String attrName,String attrVal){
		return d.select(elementName+"["+attrName+"~=^"+attrVal+"$]");
	}
}
