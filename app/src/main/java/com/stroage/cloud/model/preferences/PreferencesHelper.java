package com.stroage.cloud.model.preferences;

import rx.Observable;

/**
 * @date 创建时间 2018/3/18
 * @author Administrator
 * @Description
 * @version
 */

public interface PreferencesHelper<T> {

    Observable<T> save(String key, T value);

    Observable<T> get(String key, Class<T> generic);

    Observable<Boolean> clear();
}
