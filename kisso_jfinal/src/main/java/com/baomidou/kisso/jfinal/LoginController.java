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

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.common.IpHelper;
import com.baomidou.kisso.web.waf.request.WafRequestWrapper;
import com.jayqqaa12.jbase.jfinal.ext.ctrl.JsonController;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.LogKit;

/**
 * 登录
 */

@ControllerBind(controllerKey = "/login")
public class LoginController extends JsonController {

    public void index() {
        SSOToken token = SSOHelper.getToken(getRequest());
        if (token != null) {
            redirect("/");
            return;
        }

        setHttpServletRequest(new WafRequestWrapper(getRequest()));
        String username = getPara("username");
        String password = getPara("password");

        //查询数据库 判断是否 正确

        if ("123".equals(username) && "123".equals(password)&&validateCaptcha(getPara("vcode"))) {

            token = new SSOToken();
            token.setId(1000L);
            token.setUid("1000");
            token.setIp(IpHelper.getIpAddr(getRequest()));

            //记住密码，设置 cookie 时长 1 周 = 604800 秒 【动态设置 maxAge 实现记住密码功能】
            //String rememberMe = req.getParameter("rememberMe");
            //if ( "on".equals(rememberMe) ) {
            //	request.setAttribute(SSOConfig.SSO_COOKIE_MAXAGE, 604800);
            //}
            SSOHelper.setSSOCookie(getRequest(), getResponse(), token, true);
            redirect("/");

            return;
        }
        render("/login.html");
    }


    public void verify() {
        renderCaptcha();
    }

    /**
     * <p>
     * 支持APP端登录
     * <br>
     * 调用时需要为请求Header设置PLATFORM=APP
     * `，而直接视为jFinal的controller
     * </p>
     */
    public void auth() {
        SSOToken token = (SSOToken) SSOHelper.getToken(getRequest());
        if (token != null) {
            sendJson();
        } else {
            //查询数据库 判断是否 正确
            setHttpServletRequest(new WafRequestWrapper(getRequest()));
            String username = getPara("username");
            String password =getPara("password");

            if ("123".equals(username) && "123".equals(password)) {
                token = new SSOToken();
                token.setUid("1000");
                token.setIp(IpHelper.getIpAddr(getRequest()));
                SSOHelper.setSSOCookie(getRequest(), getResponse(), token, true);
                sendJson();
            } else
                renderError(401);
        }
    }
}
