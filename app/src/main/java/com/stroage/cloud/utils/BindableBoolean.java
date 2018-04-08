package com.stroage.cloud.utils;

import org.parceler.Parcel;

@Parcel
public class BindableBoolean extends BaseObservable {
    boolean mValue;

    public boolean get() {
        return mValue;
    }

    public void set(boolean value) {
        if (mValue != value) {
            this.mValue = value;
            notifyChange();
        }
    }
}