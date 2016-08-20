package com.maumqmaum.mvp.presenter;

import com.maumqmaum.mvp.view.MvpView;

public interface MvpPresenter<V extends MvpView> {

    void attachView(V view);

    void detachView();

}
