# SimpleMvp
This is simple mvp pattern library for rxlifecycle.  

# Example
### Activity
```
Observable.just("1","2",3).compose(RxLifecycle.bindUntilEvent(lifecycle(), ActivityEvent.DESTROY));
```
### Fragment
```
Observable.just("1","2",3).compose(RxLifecycle.bindUntilEvent(lifecycle(), FragmentEvent.DESTROY));
```
### Presenter
```
Observable.just("1","2",3).compose(RxLifecycle.bindUntilEvent(lifecycle(), PresenterEvent.DETACH_VIEW));
```
