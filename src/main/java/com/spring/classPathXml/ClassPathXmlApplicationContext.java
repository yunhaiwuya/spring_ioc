package com.spring.classPathXml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import com.spring.context.ApplicationContext;

/**
 * 基于类路径加载配置文件
 * @author cjm
 *
 */
public class ClassPathXmlApplicationContext implements ApplicationContext{

	//要解析的配置文件
	private File file;
	//存放bean对象的实例
	private Map<String,Object> map = new HashMap<>();
	//解析配置文件，实例化容器，将对象存放入容器中
	public ClassPathXmlApplicationContext(String configfile) throws Exception {
		//获取类实例对象、获取类加载器、获取文件资源
		URL url = this.getClass().getClassLoader().getResource(configfile);
		//创建一个文件：URI用来表示抽象路径名
		file = new File(url.toURI());
		xmlParse(file);
	}
	
	private void xmlParse(File file) throws JDOMException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//创建JDOM解析器 对象
		SAXBuilder builder = new SAXBuilder();
		//获取Document
		Document doc = builder.build(file);
		// 创建XPath对象,反射获取XPath对象
		XPathFactory factory = XPathFactory.instance();
		// 获取所有的Bean节点,XPath解析写的写法可以了解一下
		XPathExpression<Object> expression=factory.compile("//bean");
		List<Object> beans = expression.evaluate(doc);
		Iterator<Object> it = beans.iterator();
		while(it.hasNext()){
			Element e = (Element) it.next();
			//获取配置文件的id、class属性值
			String id = e.getAttributeValue("id");
			String cls = e.getAttributeValue("class");
			//反射拿到类的相应信息，先拿到类的实例对象
			@SuppressWarnings("rawtypes")
			Class cla = Class.forName(cls);
			Object object = cla.newInstance();
			//获取类的所有方法。然后通过set方法给这个对象设置属性值
			Method[] methods = cla.getDeclaredMethods();
			List<Element> list = e.getChildren("property");
			for(Element el:list){
				for(int i=0;i<methods.length;i++){
					String methodName = methods[i].getName();
					//属性名
					String temp = "";
					if(methodName.startsWith("set")){
						// 这里就只截取set方法的方法名并且转换为小写的名字
						temp = methodName.substring(3).toLowerCase();
						// 属性为普通对象的属性
						if(el.getAttribute("name")!=null){
							if(temp.equals(el.getAttributeValue("name"))){
								// 反射给对象设置值
								methods[i].invoke(object, el.getAttributeValue("value"));
							}
						}else{
							// 属性为引用对象的属性
							methods[i].invoke(object, map.get(el.getAttributeValue("ref")));
						}
					}
				}
			}
			//将对象添加到容器
			map.put(id, object);
		}
	}
	
	//获取bean对象
	@Override
	public Object getBean(String name) {
		return map.get(name);
	}
}
