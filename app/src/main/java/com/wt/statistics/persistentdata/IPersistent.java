package com.wt.statistics.persistentdata;

/* loaded from: classes.dex */
public interface IPersistent {
    public static final com.wt.statistics.persistentdata.EventBeans mBean = new com.wt.statistics.persistentdata.EventBeans();

    void deleteEventData(long j);

    void deleteExceptionData(long j);

    boolean needUploadEvent();

    com.wt.statistics.persistentdata.EventBeans readEvent(long j);

    com.wt.statistics.persistentdata.ExceptionBean readExceptionEvent(long j);

    void removeData();

    void saveActivityEvent(java.util.ArrayList<com.wt.statistics.persistentdata.EventBeans.ActivityEvents> arrayList);

    void saveExceptionEvent(com.wt.statistics.persistentdata.ExceptionBean.ExceptionInfo exceptionInfo);

    void saveFragmentEvent(java.util.ArrayList<com.wt.statistics.persistentdata.EventBeans.FragmentEvents> arrayList);

    void saveNormalEvent(java.util.ArrayList<com.wt.statistics.persistentdata.EventBeans.Events> arrayList);
}
