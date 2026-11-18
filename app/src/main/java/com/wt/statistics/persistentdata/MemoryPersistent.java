package com.wt.statistics.persistentdata;

/* loaded from: classes.dex */
public class MemoryPersistent implements com.wt.statistics.persistentdata.IPersistent {
    public MemoryPersistent(android.content.Context cxt) {
    }

    @Override // com.wt.statistics.persistentdata.IPersistent
    public void removeData() {
    }

    @Override // com.wt.statistics.persistentdata.IPersistent
    public void saveNormalEvent(java.util.ArrayList<com.wt.statistics.persistentdata.EventBeans.Events> events) {
    }

    @Override // com.wt.statistics.persistentdata.IPersistent
    public void saveActivityEvent(java.util.ArrayList<com.wt.statistics.persistentdata.EventBeans.ActivityEvents> events) {
    }

    @Override // com.wt.statistics.persistentdata.IPersistent
    public void saveFragmentEvent(java.util.ArrayList<com.wt.statistics.persistentdata.EventBeans.FragmentEvents> events) {
    }

    @Override // com.wt.statistics.persistentdata.IPersistent
    public void saveExceptionEvent(com.wt.statistics.persistentdata.ExceptionBean.ExceptionInfo bean) {
    }

    @Override // com.wt.statistics.persistentdata.IPersistent
    public com.wt.statistics.persistentdata.ExceptionBean readExceptionEvent(long timestamp) {
        return null;
    }

    @Override // com.wt.statistics.persistentdata.IPersistent
    public com.wt.statistics.persistentdata.EventBeans readEvent(long timestamp) {
        return null;
    }

    @Override // com.wt.statistics.persistentdata.IPersistent
    public void deleteExceptionData(long timestamp) {
    }

    @Override // com.wt.statistics.persistentdata.IPersistent
    public void deleteEventData(long timestamp) {
    }

    @Override // com.wt.statistics.persistentdata.IPersistent
    public boolean needUploadEvent() {
        return false;
    }
}
