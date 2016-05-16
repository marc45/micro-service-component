/**
 * Copyright (c) 2011-2014, hubin (243194995@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.kisso.jfinal;

import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.plugin.SSOJfinalInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;

@Before(SSOJfinalInterceptor.class)
@ControllerBind(controllerKey = "/")
public class IndexController extends Controller {

    /**
     * <p>
     * SSOHelper.getToken(request)
     * 从 Cookie 解密 token 使用场景，拦截器
     * SSOHelper.attrToken(request)
     * 非拦截器使用减少二次解密
     * </p>
     */
    public void index() {

		/*
		 * <p>
		 * SSOHelper.getToken(request)
		 * 从 Cookie 解密 token 使用场景，拦截器
		 * SSOHelper.attrToken(request)
		 * 非拦截器使用减少二次解密
		 * </p>
		 */
        SSOToken token = SSOHelper.attrToken(getRequest());
        if (token != null) {
            System.err.println(" Long 用户ID :" + token.getId());
            System.err.println(" 登录用户ID : " + token.getUid());
        }

        System.err.println(" 当前注入运行模式是：" + SSOConfig.getInstance().getRunMode());
        render("index.html");
    }



    /**
     * <p>
     * SSO 退出，清空退出状态即可
     * 子系统退出 SSOHelper.logout(request, response); 注意 sso.properties 包含 退出到
     * SSO 的地址 ， 属性 sso.logout.url 的配置
     * </p>
     */
    public void logout() {

        SSOHelper.clearLogin(getRequest(), getResponse());
        redirect("login");
    }


}
