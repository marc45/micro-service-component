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
package wang.leq.kissojfinal;

import wang.leq.sso.LoginHelper;
import wang.leq.sso.SSOToken;

import com.jfinal.core.Controller;

/**
 * 登录
 */
public class LoginController extends Controller {

	public void index() {
		render("login.html");
	}


	public void post() {
		String username = this.getPara("username");
		String password = this.getPara("password");
		if ( "kisso".equals(username) && "123".equals(password) ) {
			SSOToken st = new SSOToken();
			st.setUserId("12306");
			LoginHelper.authSSOCookie(getRequest(), getResponse(), st);
			redirect("/");
			return;
		}
		render("login.html");
	}
}
