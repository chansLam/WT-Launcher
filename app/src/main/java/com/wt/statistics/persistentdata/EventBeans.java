package com.wt.statistics.persistentdata;

/* loaded from: classes.dex */
public class EventBeans implements com.wt.statistics.persistentdata.IJsonObject {
    private static final java.lang.String LOGTAG = "EventBeans";
    public com.wt.statistics.persistentdata.BaseRequestBean baseBean = new com.wt.statistics.persistentdata.BaseRequestBean();
    public java.util.ArrayList<com.wt.statistics.persistentdata.EventBeans.Events> normal_events = new java.util.ArrayList<>();
    public java.util.ArrayList<com.wt.statistics.persistentdata.EventBeans.ActivityEvents> activity_events = new java.util.ArrayList<>();
    public java.util.ArrayList<com.wt.statistics.persistentdata.EventBeans.FragmentEvents> fragment_events = new java.util.ArrayList<>();

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public com.wt.statistics.persistentdata.EventBeans m6clone() {
        com.wt.statistics.persistentdata.EventBeans bean = new com.wt.statistics.persistentdata.EventBeans();
        bean.baseBean.channel_id = this.baseBean.channel_id;
        bean.baseBean.app_id = this.baseBean.app_id;
        bean.baseBean.vin = this.baseBean.vin;
        bean.baseBean.user_id = this.baseBean.user_id;
        bean.baseBean.carSeries = this.baseBean.carSeries;
        for (int i = 0; i < this.normal_events.size(); i++) {
            bean.normal_events.add(i, this.normal_events.get(i).m8clone());
        }
        for (int i2 = 0; i2 < this.activity_events.size(); i2++) {
            bean.activity_events.add(i2, this.activity_events.get(i2).m7clone());
        }
        for (int i3 = 0; i3 < this.fragment_events.size(); i3++) {
            bean.fragment_events.add(i3, this.fragment_events.get(i3).m9clone());
        }
        return bean;
    }

    @Override // com.wt.statistics.persistentdata.IJsonObject
    public boolean toObject(java.lang.String jsonString) {
        try {
            org.json.JSONObject jsonObj = new org.json.JSONObject(jsonString);
            try {
                this.baseBean.channel_id = jsonObj.getString("channel_id");
                this.baseBean.app_id = jsonObj.getString("app_id");
                this.baseBean.vin = jsonObj.getString("vin");
                org.json.JSONArray eventsArray = jsonObj.getJSONArray("events");
                for (int i = 0; i < eventsArray.length(); i++) {
                    com.wt.statistics.persistentdata.EventBeans.Events normalEvents = new com.wt.statistics.persistentdata.EventBeans.Events();
                    org.json.JSONObject obj = eventsArray.getJSONObject(i);
                    normalEvents.eventTime = obj.getString(com.wt.statistics.persistentdata.EventBeans.Events.EVENTTIME);
                    normalEvents.eventId = obj.getString(com.wt.statistics.persistentdata.EventBeans.Events.EVENTID);
                    normalEvents.eventLabel = obj.getString(com.wt.statistics.persistentdata.EventBeans.Events.EVENTLABEL);
                    org.json.JSONObject mapObj = obj.getJSONObject("properties");
                    normalEvents.properties = mapObj.toString();
                    this.normal_events.add(normalEvents);
                }
                org.json.JSONArray activityArray = jsonObj.getJSONArray(com.wt.statistics.persistentdata.EventConstants.EVENTS_ACTIVITY_TYPE);
                for (int i2 = 0; i2 < activityArray.length(); i2++) {
                    org.json.JSONObject obj2 = activityArray.getJSONObject(i2);
                    com.wt.statistics.persistentdata.EventBeans.ActivityEvents aEvents = new com.wt.statistics.persistentdata.EventBeans.ActivityEvents();
                    aEvents.page_name = obj2.getString(com.wt.statistics.persistentdata.EventBeans.ActivityEvents.PAGE_NAME);
                    aEvents.duration_time = obj2.getLong(com.wt.statistics.persistentdata.EventBeans.ActivityEvents.DURATION_TIME);
                    aEvents.start_time = obj2.getLong(com.wt.statistics.persistentdata.EventBeans.ActivityEvents.START_TIME);
                    aEvents.end_time = obj2.getLong(com.wt.statistics.persistentdata.EventBeans.ActivityEvents.END_TIME);
                    aEvents.page_from = obj2.getString(com.wt.statistics.persistentdata.EventBeans.ActivityEvents.PAGE_FROM);
                    this.activity_events.add(aEvents);
                }
                org.json.JSONArray fragmentArray = jsonObj.getJSONArray(com.wt.statistics.persistentdata.EventConstants.EVENTS_FRAGMENT_TYPE);
                for (int i3 = 0; i3 < fragmentArray.length(); i3++) {
                    org.json.JSONObject obj3 = fragmentArray.getJSONObject(i3);
                    com.wt.statistics.persistentdata.EventBeans.FragmentEvents fEvents = new com.wt.statistics.persistentdata.EventBeans.FragmentEvents();
                    fEvents.fragment_page_name = obj3.getString(com.wt.statistics.persistentdata.EventBeans.FragmentEvents.FRAGMENT_PAGE_NAME);
                    fEvents.fragment_duration_time = obj3.getString(com.wt.statistics.persistentdata.EventBeans.FragmentEvents.FRAGMENT_DURATION_TIME);
                    this.fragment_events.add(fEvents);
                }
                return true;
            } catch (org.json.JSONException e) {
                e.printStackTrace();
                return false;
            }
        } catch (org.json.JSONException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    @Override // com.wt.statistics.persistentdata.IJsonObject
    public org.json.JSONObject toJsonObject() {
        org.json.JSONObject obj = new org.json.JSONObject();
        try {
            obj.put("channel_id", this.baseBean.channel_id);
            obj.put("app_id", this.baseBean.app_id);
            obj.put("vin", this.baseBean.vin);
            obj.put(com.wt.statistics.persistentdata.BaseRequestBean.CARSERIES, this.baseBean.carSeries);
            obj.put(com.wt.statistics.persistentdata.BaseRequestBean.USER_ID, this.baseBean.user_id);
            org.json.JSONArray eventsArray = new org.json.JSONArray();
            for (int i = 0; i < this.normal_events.size(); i++) {
                com.wt.statistics.persistentdata.EventBeans.Events events = this.normal_events.get(i);
                eventsArray.put(i, events.toJsonObject());
            }
            obj.put("events", eventsArray);
            org.json.JSONArray activityArray = new org.json.JSONArray();
            for (int i2 = 0; i2 < this.activity_events.size(); i2++) {
                com.wt.statistics.persistentdata.EventBeans.ActivityEvents events2 = this.activity_events.get(i2);
                activityArray.put(i2, events2.toJsonObject());
            }
            obj.put(com.wt.statistics.persistentdata.EventConstants.EVENTS_ACTIVITY_TYPE, activityArray);
            org.json.JSONArray fragmentArray = new org.json.JSONArray();
            for (int i3 = 0; i3 < this.fragment_events.size(); i3++) {
                com.wt.statistics.persistentdata.EventBeans.FragmentEvents events3 = this.fragment_events.get(i3);
                fragmentArray.put(i3, events3.toJsonObject());
            }
            obj.put(com.wt.statistics.persistentdata.EventConstants.EVENTS_FRAGMENT_TYPE, fragmentArray);
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public void cleanData() {
        this.normal_events.clear();
        this.activity_events.clear();
        this.fragment_events.clear();
    }

    public boolean isEmptyData() {
        return this.normal_events.size() <= 0 && this.activity_events.size() <= 0 && this.fragment_events.size() <= 0;
    }

    public int getEventSize() {
        return this.normal_events.size() + this.activity_events.size() + this.fragment_events.size();
    }

    public static class Events {
        public static final java.lang.String EVENTID = "eventId";
        public static final java.lang.String EVENTLABEL = "eventLabel";
        public static final java.lang.String EVENTTIME = "eventTime";
        public static final java.lang.String PROPERTIES = "properties";
        public java.lang.String eventId;
        public java.lang.String eventLabel;
        public java.lang.String eventTime;
        public java.lang.String properties;

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public com.wt.statistics.persistentdata.EventBeans.Events m8clone() {
            com.wt.statistics.persistentdata.EventBeans.Events events = new com.wt.statistics.persistentdata.EventBeans.Events();
            events.eventTime = this.eventTime;
            events.eventId = this.eventId;
            events.eventLabel = this.eventLabel;
            events.properties = this.properties;
            return events;
        }

        public org.json.JSONObject toJsonObject() {
            org.json.JSONObject eventsObj = new org.json.JSONObject();
            try {
                eventsObj.put(EVENTTIME, this.eventTime);
                eventsObj.put(EVENTID, this.eventId);
                eventsObj.put(EVENTLABEL, this.eventLabel);
                if (android.text.TextUtils.isEmpty(this.properties)) {
                    eventsObj.put("properties", "{}");
                } else {
                    eventsObj.put("properties", new org.json.JSONObject(this.properties));
                }
            } catch (org.json.JSONException e) {
                e.printStackTrace();
            }
            return eventsObj;
        }

        public void toObject(java.lang.String jsonString) {
            org.json.JSONObject obj = null;
            if (jsonString != null) {
                try {
                    obj = new org.json.JSONObject(jsonString);
                } catch (org.json.JSONException e) {
                }
                try {
                    this.eventTime = obj.getString(EVENTTIME);
                    this.eventId = obj.getString(EVENTID);
                    this.eventLabel = obj.getString(EVENTLABEL);
                    this.properties = obj.getString("properties");
                } catch (org.json.JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public static class ActivityEvents {
        public static final java.lang.String DURATION_TIME = "duration_time";
        public static final java.lang.String END_TIME = "end_time";
        public static final java.lang.String PAGE_FROM = "page_from";
        public static final java.lang.String PAGE_NAME = "page_name";
        public static final java.lang.String START_TIME = "start_time";
        public long duration_time;
        public long end_time;
        public long start_time;
        public java.lang.String page_name = "";
        public java.lang.String page_from = "";

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public com.wt.statistics.persistentdata.EventBeans.ActivityEvents m7clone() {
            com.wt.statistics.persistentdata.EventBeans.ActivityEvents events = new com.wt.statistics.persistentdata.EventBeans.ActivityEvents();
            events.page_name = this.page_name;
            events.duration_time = this.duration_time;
            events.start_time = this.start_time;
            events.end_time = this.end_time;
            events.page_from = this.page_from;
            return events;
        }

        public org.json.JSONObject toJsonObject() {
            org.json.JSONObject eventsObj = new org.json.JSONObject();
            try {
                eventsObj.put(PAGE_NAME, this.page_name);
                eventsObj.put(DURATION_TIME, this.duration_time);
                eventsObj.put(END_TIME, this.end_time);
                eventsObj.put(START_TIME, this.start_time);
                eventsObj.put(PAGE_FROM, this.page_from);
            } catch (org.json.JSONException e) {
                e.printStackTrace();
            }
            return eventsObj;
        }

        public void toObject(java.lang.String jsonString) {
            if (jsonString != null) {
                try {
                    org.json.JSONObject obj = new org.json.JSONObject(jsonString);
                    this.page_name = obj.getString(PAGE_NAME);
                    this.duration_time = obj.getLong(DURATION_TIME);
                    this.end_time = obj.getLong(END_TIME);
                    this.start_time = obj.getLong(START_TIME);
                    this.page_from = obj.getString(PAGE_FROM);
                } catch (org.json.JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class FragmentEvents {
        public static final java.lang.String FRAGMENT_DURATION_TIME = "fragment_duration_time";
        public static final java.lang.String FRAGMENT_PAGE_NAME = "fragment_page_name";
        public java.lang.String fragment_duration_time;
        public java.lang.String fragment_page_name;

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public com.wt.statistics.persistentdata.EventBeans.FragmentEvents m9clone() {
            com.wt.statistics.persistentdata.EventBeans.FragmentEvents events = new com.wt.statistics.persistentdata.EventBeans.FragmentEvents();
            events.fragment_page_name = this.fragment_page_name;
            events.fragment_duration_time = this.fragment_duration_time;
            return events;
        }

        public org.json.JSONObject toJsonObject() {
            org.json.JSONObject eventsObj = new org.json.JSONObject();
            try {
                eventsObj.put(FRAGMENT_PAGE_NAME, this.fragment_page_name);
                eventsObj.put(FRAGMENT_DURATION_TIME, this.fragment_duration_time);
            } catch (org.json.JSONException e) {
                e.printStackTrace();
            }
            return eventsObj;
        }

        public void toObject(java.lang.String jsonString) {
            if (jsonString != null) {
                try {
                    org.json.JSONObject obj = new org.json.JSONObject(jsonString);
                    this.fragment_page_name = obj.getString(FRAGMENT_PAGE_NAME);
                    this.fragment_duration_time = obj.getString(FRAGMENT_DURATION_TIME);
                } catch (org.json.JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
