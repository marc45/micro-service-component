
package com.baomidou.kisso;

import com.baomidou.kisso.plugin.KissoJfinalPlugin;
import com.jayqqaa12.jbase.jfinal.ext.JbaseConfig;
import com.jfinal.config.Plugins;
import com.jfinal.core.JFinal;

/**
 * 配置
 */
public class DemoConfig extends JbaseConfig {

	/**

	/**
	 * 配置插件
	 */
	@Override
	public void configPlugin(Plugins plugins) {
		//kisso 初始化
		plugins.add(new KissoJfinalPlugin());
	}



	public static void main(String[] args) {

		JFinal.start("/src/main/webapp",2222,"/",0);
	}

}
