package com.stroage.cloud.model.preferences;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * @date 创建时间 2018/3/18
 * @author Administrator
 * @Description
 * @version
 */
public class PreferencesHelperImpl<T> implements PreferencesHelper<T> {

    SharedPreferences sharedPreferences;
    Gson gson;

    @Inject
    public PreferencesHelperImpl(SharedPreferences sharedPreferences, Gson gson) {
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
    }

    @Override
    public Observable<T> save(final String key, final T value) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                sharedPreferences.edit().putString(key,gson.toJson(value)).apply();
                subscriber.onNext(value);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<T> get(final String key,final Class<T> generic) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                String json = sharedPreferences.getString(key, "");
                T myClass = gson.fromJson(json, generic);
                subscriber.onNext(myClass);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Boolean> clear() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                sharedPreferences.edit().clear().commit();
                subscriber.onNext(true);
                subscriber.onCompleted();
            }
        });
    }
}