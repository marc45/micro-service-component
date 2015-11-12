/**
 * Copyright (c) 2011-2014, hubin (243194995@qq.com).
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
package com.baomidou.kisso.jfinal;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.common.IpHelper;
import com.baomidou.kisso.common.util.HttpUtil;
import com.baomidou.kisso.web.waf.request.WafRequestWrapper;
import com.jfinal.core.Controller;

/**
 * 登录
 */
public class LoginController extends Controller {

	public void index() {
		JToken st = (JToken) SSOHelper.getToken(getRequest());
		if (st != null) {
			redirect("/");
			return;
		}

		/**
		 * 登录 生产环境需要过滤sql注入
		 */
		if (HttpUtil.isPost(getRequest())) {
			WafRequestWrapper req = new WafRequestWrapper(getRequest());
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			if ("kisso".equals(username) && "123".equals(password)) {
				/**
				 * 系统定义 SSOToken st = new SSOToken();
				 * <p>
				 * 自定义 JToken
				 * </p>
				 */
				st = new JToken();
				st.setUserId(10000L);
				st.setIp(IpHelper.getIpAddr(getRequest()));
				SSOHelper.authSSOCookie(getRequest(), getResponse(), st);
				redirect("/");
				return;
			}
		}
		render("login.html");
	}
}
