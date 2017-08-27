package com.maumqmaum.mvp.presenter;

import android.support.annotation.NonNull;

import com.maumqmaum.mvp.event.PresenterEvent;
import com.maumqmaum.mvp.view.MvpView;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;


public class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private final BehaviorSubject<PresenterEvent> lifecycleSubject = BehaviorSubject.create();

    private WeakReference<V> viewRef;

    @Override
    public void attachView(V view) {
        lifecycleSubject.onNext(PresenterEvent.ATTACH_VIEW);
        viewRef = new WeakReference<V>(view);
    }

    public V getView() {
        return viewRef == null ? null : viewRef.get();
    }

    public boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    public void detachView() {
        lifecycleSubject.onNext(PresenterEvent.DETACH_VIEW);
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    @NonNull
    public final Observable<PresenterEvent> lifecycle() {
        return lifecycleSubject;
    }
}