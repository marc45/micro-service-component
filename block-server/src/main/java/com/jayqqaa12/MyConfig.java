package com.jayqqaa12;

import com.jayqqaa12.jbase.jfinal.ext.JbaseConfig;
import com.jayqqaa12.jbase.jfinal.ext.exception.JsonExceptionInterceptor;
import com.jfinal.config.Interceptors;
import com.jfinal.config.Plugins;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;


public class MyConfig extends JbaseConfig {
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {

//		ActiveRecordPlugin atbp = addActiveRecordPlugin(me, addDruidPlugin(me));
//		_MappingKit.mapping(atbp);

//		addQuartzPlugin(me);
		addEhCachePlugin(me);


	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		super.configInterceptor(me);

//		me.add(new JsonExceptionInterceptor());
	}


	/**
	 * 使用Jrebel 关闭jetty扫描 设置扫描时间为0 但是jrebel 不用运行Myconfig 里面的方法 如果新增contrl model
	 * 之类的要重启一下 Jrebel 要使用 DEBUG 模式运行（Eclipse）
	 */
	public static void main(String[] args) {

		JFinal.start("src/main/webapp", 8080, "/", 0);
	}

}
