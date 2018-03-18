package com.stroage.cloud.domain.login;

import com.stroage.cloud.model.api.ApiSource;
import com.stroage.cloud.model.usefeed.LoginFeed;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Administrator
 * @date 创建时间 2018/3/18
 * @Description
 */

public class LoginUseCaseImp implements LoginUseCase {

    ApiSource apiSource;

    @Inject
    public  LoginUseCaseImp(ApiSource apiSource){
        this.apiSource = apiSource;
    }

    @Override
    public Observable<LoginFeed> login(String loginName, String loginPwd) {
        return null;
    }
}
