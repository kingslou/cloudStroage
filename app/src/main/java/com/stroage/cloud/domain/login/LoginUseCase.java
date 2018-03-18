package com.stroage.cloud.domain.login;

import com.stroage.cloud.model.usefeed.LoginFeed;

import rx.Observable;

/**
 * @author Administrator
 * @date 创建时间 2018/3/18
 * @Description
 */

public interface LoginUseCase {

    Observable<LoginFeed> login(String loginName,String loginPwd);
}
