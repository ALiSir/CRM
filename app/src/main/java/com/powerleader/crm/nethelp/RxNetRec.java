package com.powerleader.crm.nethelp;

import rx.Observable;
import rx.Subscriber;
import rx.observers.Observers;
import rx.observers.Subscribers;

/**
 * Created by ALiSir on 2016/8/2.
 */
public class RxNetRec {
    private static RxNetRec rnrInit = null;
    private Observable<String> netRecObs;
    private Subscriber<String> netRecSub;

    public static  RxNetRec getRnrInit() {
        if(rnrInit == null){
            rnrInit = new RxNetRec();
        }
        return rnrInit;
    }

    public Observable<String> getNetRecObs() {
        return netRecObs;
    }

    public void setNetRecObs(Observable<String> netRecObs) {
        this.netRecObs = netRecObs;
    }

    public Subscriber<String> getNetRecSub() {
        return netRecSub;
    }

    public void setNetRecSub(Subscriber<String> netRecSub) {
        this.netRecSub = netRecSub;
    }
}
