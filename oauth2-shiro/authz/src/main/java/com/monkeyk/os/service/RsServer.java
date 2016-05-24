package com.monkeyk.os.service;

import com.monkeyk.os.service.dto.Ret;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 12 on 2016/5/23.
 */
public interface RsServer {

    public Ret role(HttpServletRequest req, HttpServletResponse resp) throws OAuthSystemException;

    public Ret permission(HttpServletRequest req, HttpServletResponse resp) throws OAuthSystemException;

}




