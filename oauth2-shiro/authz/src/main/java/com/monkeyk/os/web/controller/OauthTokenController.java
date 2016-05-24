package com.monkeyk.os.web.controller;

import com.monkeyk.os.domain.oauth.OauthRepository;
import com.monkeyk.os.infrastructure.jdbc.OauthJdbcRepository;
import com.monkeyk.os.service.dto.Ret;
import com.monkeyk.os.web.WebUtils;
import com.monkeyk.os.oauth.OAuthTokenxRequest;
import com.monkeyk.os.oauth.token.OAuthTokenHandleDispatcher;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

/**
 * 2015/7/3
 * <p>
 * URL: oauth/token
 *
 * @author Shengzhao Li
 */
@Controller
@RequestMapping("oauth/")
public class OauthTokenController {


    @Autowired
    OauthRepository jdbc;


    /**
     * Handle grant_types as follows:
     * <p>
     * grant_type=authorization_code
     * grant_type=password
     * grant_type=refresh_token
     * grant_type=client_credentials
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws OAuthSystemException
     */
    @RequestMapping("token")
    public void authorize(HttpServletRequest request, HttpServletResponse response) throws OAuthSystemException {
        try {
            OAuthTokenxRequest tokenRequest = new OAuthTokenxRequest(request);

            OAuthTokenHandleDispatcher tokenHandleDispatcher = new OAuthTokenHandleDispatcher(tokenRequest, response);
            tokenHandleDispatcher.dispatch();

        } catch (OAuthProblemException e) {
            //exception
            OAuthResponse oAuthResponse = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_FOUND)
                    .location(e.getRedirectUri())
                    .error(e)
                    .buildJSONMessage();
            WebUtils.writeOAuthJsonResponse(response, oAuthResponse);
        }
    }

    @RequestMapping("createUser")
    @ResponseBody
    public Ret createUser(HttpServletRequest req, HttpServletResponse response)  {
        return new Ret(jdbc.createUser(req.getParameter("username"), req.getParameter("pwd")));
    }





}
