package com.github.liuyueyi.forum.web.global;

import com.github.liueyueyi.forum.api.model.context.ReqInfoContext;
import com.github.liueyueyi.forum.api.model.vo.user.dto.BaseUserInfoDTO;
import com.github.liuyueyi.forum.core.util.NumUtil;
import com.github.liuyueyi.forum.service.user.service.LoginService;
import com.github.liuyueyi.forum.web.config.GlobalViewConfig;
import com.github.liuyueyi.forum.web.global.vo.GlobalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author YiHui
 * @date 2022/9/3
 */
@Service
public class GlobalInitService {
    @Value("${env.name}")
    private String env;
    @Autowired
    private LoginService loginService;

    @Resource
    private GlobalViewConfig globalViewConfig;

    /**
     * 全局属性配置
     */
    public GlobalVo globalAttr() {
        GlobalVo vo = new GlobalVo();
        vo.setEnv(env);
        vo.setSiteInfo(globalViewConfig);
        if (ReqInfoContext.getReqInfo() != null && NumUtil.upZero(ReqInfoContext.getReqInfo().getUserId())) {
            vo.setIsLogin(true);
            vo.setUser(ReqInfoContext.getReqInfo().getUser());
        } else {
            vo.setIsLogin(false);
        }
        vo.setMsgNum(1);
        return vo;
    }

    public void initLoginUser(ReqInfoContext.ReqInfo reqInfo) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        for (Cookie cookie : request.getCookies()) {
            if (LoginService.SESSION_KEY.equalsIgnoreCase(cookie.getName())) {
                String session = cookie.getValue();
                BaseUserInfoDTO user = loginService.getUserBySessionId(session);
                reqInfo.setSession(session);
                if (user != null) {
                    reqInfo.setUserId(user.getUserId());
                    reqInfo.setUser(user);
                }
                return;
            }
        }
    }
}