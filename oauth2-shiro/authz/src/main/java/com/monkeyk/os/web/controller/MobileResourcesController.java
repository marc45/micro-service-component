package com.monkeyk.os.web.controller;

import com.monkeyk.os.domain.oauth.OauthRepository;
import com.monkeyk.os.service.RsServer;
import com.monkeyk.os.service.dto.Ret;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Mobile resource API
 * Protect by Oauth
 * <p>
 * <p>
 * resource-id: mobile-resource
 *
 * @author Shengzhao Li
 * @see org.apache.oltu.oauth2.rsfilter.OAuthFilter
 */
@Controller
@RequestMapping("api/")
public class MobileResourcesController {

    @Autowired
    RsServer rsServer;

    @Autowired
    OauthRepository jdbc;



    @RequestMapping("pwd")
    @ResponseBody
    @RequiresPermissions("user:edit")
    public Ret pwd(HttpServletRequest req, HttpServletResponse resp) {

        final String username = req.getUserPrincipal().getName();

        return new Ret(jdbc.pwd( username, req.getParameter("pwd")));

    }



    @RequestMapping("role")
    @ResponseBody
    public Ret role(HttpServletRequest req,HttpServletResponse resp) throws OAuthSystemException {

        return  rsServer.role(req,resp);
    }


    @RequestMapping("permission")
    @ResponseBody
    public Ret permission(HttpServletRequest req,HttpServletResponse resp) throws OAuthSystemException {
        return  rsServer.role(req,resp);
    }





}