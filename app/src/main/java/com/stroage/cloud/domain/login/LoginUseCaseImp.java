package com.stroage.cloud.domain.login;

import com.stroage.cloud.model.api.ApiSource;
import com.stroage.cloud.model.usefeed.LoginFeed;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        return apiSource.login(loginName,loginPwd).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
