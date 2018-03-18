package com.stroage.cloud.utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mertsimsek on 13/01/17.
 */

public class RxTransformer {

    public static <T> Observable.Transformer<T, T> applyIOSchedulers() {

       return new Observable.Transformer<T, T>() {
           @Override
           public Observable<T> call(Observable<T> tObservable) {
               return tObservable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
           }
       };
    }

    public static <T> Observable.Transformer<T, T> applyComputationSchedulers() {

       return new Observable.Transformer<T, T>() {
           @Override
           public Observable<T> call(Observable<T> tObservable) {
               return tObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
           }
       } ;

    }
}
