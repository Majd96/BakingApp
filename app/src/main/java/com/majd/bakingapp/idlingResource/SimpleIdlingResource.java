package com.majd.bakingapp.idlingResource;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by majd on 9/25/17.
 */

public class SimpleIdlingResource implements IdlingResource {


    @Nullable
    private volatile ResourceCallback mCallback;
    private AtomicBoolean mIsIdleNow=new AtomicBoolean(true);
    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIsIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallback=callback;

    }
    public void setIdleState(Boolean isIdleNow){
        mIsIdleNow.set(isIdleNow);
        if(isIdleNow && mCallback!=null){
            mCallback.onTransitionToIdle();
        }

    }
}
