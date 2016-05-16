/**
 * Copyright (c) 2011-2014, hubin (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.kisso.plugin;

import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.Token;
import com.baomidou.kisso.web.interceptor.KissoAbstractInterceptor;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * 登录权限验证
 * <p>
 * kisso jfinal 拦截器，Controller 方法调用前处理。
 * </p>
 */
public class SSOJfinalInterceptor extends KissoAbstractInterceptor implements Interceptor {

	private static final Logger logger = Logger.getLogger("SSOJfinalInterceptor");


	public void intercept( Invocation inv ) {
		/**
		 * 正常执行
		 *
		 * 一般app 还是使用 oauth2 认证
		 *
		 */
		Token token = SSOHelper.getToken(inv.getController().getRequest());
		if ( token == null ) {
			if ( "XMLHttpRequest".equals(inv.getController().getRequest().getHeader("X-Requested-With")) ) {
				 //ajax
				getHandlerInterceptor().preTokenIsNullAjax(inv.getController().getRequest(),  inv.getController().getResponse());
			} else if ( "APP".equals(inv.getController().getRequest().getHeader("PLATFORM")) ) {
				/*
				 * Handler 处理 APP接口调用 请求
				 * 没有修改kisso核心代码，直接使用Ajax的认证判断方式，如果未认证，返回401状态码
				 */
				getHandlerInterceptor().preTokenIsNullAjax(inv.getController().getRequest(),  inv.getController().getResponse());
				logger.info("request from APP invoke");
			} else {
				try {
					logger.fine("logout. request url:" + inv.getController().getRequest().getRequestURL());
					SSOHelper.clearRedirectLogin(inv.getController().getRequest(),  inv.getController().getResponse());
				} catch ( IOException e ) {
					e.printStackTrace();
				}
			}
		} else {

			inv.getController().setAttr(SSOConfig.SSO_TOKEN_ATTR, token);
			inv.invoke();
		}
	}

}
