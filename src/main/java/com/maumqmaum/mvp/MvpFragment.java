package com.maumqmaum.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maumqmaum.mvp.event.FragmentEvent;
import com.maumqmaum.mvp.presenter.MvpPresenter;
import com.maumqmaum.mvp.view.MvpView;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public abstract class MvpFragment<V extends MvpView, P extends MvpPresenter<V>> extends Fragment
        implements MvpView {

    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    protected P presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE);
        presenter = createPresenter();
        presenter.attachView((V) this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
    }

    @Override
    public void onAttach(Context context) {
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        presenter.detachView();
    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @Override
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @NonNull
    public abstract P createPresenter();

    @NonNull
    public P getPresenter() {
        return presenter;
    }

    @NonNull
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }


}
