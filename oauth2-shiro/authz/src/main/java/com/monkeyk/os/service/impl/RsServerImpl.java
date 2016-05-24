package com.monkeyk.os.service.impl;

import com.monkeyk.os.domain.rs.ShiroExt;
import com.monkeyk.os.service.RsServer;
import com.monkeyk.os.service.dto.Ret;
import com.monkeyk.os.web.WebUtils;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 12 on 2016/5/23.
 */
@Service
public class RsServerImpl implements RsServer {


    public Ret role(HttpServletRequest req,HttpServletResponse resp) throws OAuthSystemException {

        boolean has = ShiroExt.hasRole(req.getParameter("role"));

        if (has)
            return new Ret();

        else {
            OAuthResponse oAuthResponse =   OAuthRSResponse.errorResponse(401)
                    .setError("unauthorized")
                    .buildJSONMessage();
            WebUtils.writeOAuthJsonResponse(resp, oAuthResponse);
        }
        return null;
    }


    public Ret permission(HttpServletRequest req,HttpServletResponse resp) throws OAuthSystemException {

        boolean has = ShiroExt.hasPermission(req.getParameter("permission"));

        if (has)
            return new Ret();
        else{

            OAuthResponse oAuthResponse =   OAuthRSResponse.errorResponse(401)
                    .setError("unauthorized")
                    .buildJSONMessage();
            WebUtils.writeOAuthJsonResponse(  resp, oAuthResponse);
        }
        return null;
    }
}
