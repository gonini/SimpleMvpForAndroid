package com.maumqmaum.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.maumqmaum.mvp.event.ActivityEvent;
import com.maumqmaum.mvp.presenter.MvpPresenter;
import com.maumqmaum.mvp.view.MvpView;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public abstract class MvpActivity<V extends MvpView, P extends MvpPresenter<V>> extends AppCompatActivity
        implements MvpView {

    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(ActivityEvent.CREATE);
        presenter = createPresenter();
        presenter.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        presenter.detachView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    protected void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    protected void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    @NonNull
    public abstract P createPresenter();

    @NonNull
    public P getPresenter() {
        return presenter;
    }

    @NonNull
    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }
}
