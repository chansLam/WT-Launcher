package com.autopai.applist.view;

/* loaded from: classes.dex */
public class AppsContentView extends android.widget.FrameLayout implements com.autopai.applist.adapter.AppListAdapter.OnItemClickListener {
    private static final java.lang.String TAG = "AppsContentView-hurley";
    private com.autopai.applist.adapter.AppListAdapter mAdapter;
    private com.autopai.applist.manager.AppsManager mAppsManager;
    private android.content.Context mContext;
    private android.os.Handler mHandler;
    private boolean mIsSafeModeEnabled;
    private com.autopai.applist.view.AppsContentView.OnAppClickListener mOnAppClickListener;
    private com.autopai.applist.manager.PagingItemDecoration mPagingItemDecoration;
    private com.autopai.support.view.TopRightView mTopRightView;

    public interface OnAppClickListener {
        void onAppClick();
    }

    public AppsContentView(@android.support.annotation.NonNull android.content.Context context) {
        super(context);
        this.mHandler = new android.os.Handler(new android.os.Handler.Callback() { // from class: com.autopai.applist.view.AppsContentView.1
            @Override // android.os.Handler.Callback
            public boolean handleMessage(android.os.Message message) {
                return false;
            }
        });
        init(context);
    }

    public AppsContentView(@android.support.annotation.NonNull android.content.Context context, @android.support.annotation.Nullable android.util.AttributeSet attrs) {
        super(context, attrs);
        this.mHandler = new android.os.Handler(new android.os.Handler.Callback() { // from class: com.autopai.applist.view.AppsContentView.1
            @Override // android.os.Handler.Callback
            public boolean handleMessage(android.os.Message message) {
                return false;
            }
        });
        init(context);
    }

    public AppsContentView(@android.support.annotation.NonNull android.content.Context context, @android.support.annotation.Nullable android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mHandler = new android.os.Handler(new android.os.Handler.Callback() { // from class: com.autopai.applist.view.AppsContentView.1
            @Override // android.os.Handler.Callback
            public boolean handleMessage(android.os.Message message) {
                return false;
            }
        });
        init(context);
    }

    private void init(android.content.Context context) {
        android.util.Log.i(TAG, "init = " + java.lang.System.currentTimeMillis());
        this.mContext = context;
        setThemeType();
        com.autopai.applist.model.config.AppsConfig.mCarType = getCurrentCarType();
        initView(context);
        initData();
        initManager(context);
        this.mIsSafeModeEnabled = context.getPackageManager().isSafeMode();
    }

    private void setThemeType() {
        int theme = com.incall.apps.service.setting.SettingServiceManager.getInstance().getTheme();
        if (theme == 0) {
            theme = com.incall.apps.service.setting.SettingServiceManager.getInstance().getTheme(getContext());
        }
        android.util.Log.e(TAG, "getThemeType: " + theme);
        switch (theme) {
            case 0:
                android.util.Log.e(TAG, "getThemeType: Theme change fail");
                break;
            case 1:
                android.util.Log.e(TAG, "getThemeType: Theme Classic");
                com.autopai.applist.model.config.AppsConfig.setCurrentTheme(0);
                break;
            case 2:
                android.util.Log.e(TAG, "getThemeType: Theme Sport");
                com.autopai.applist.model.config.AppsConfig.setCurrentTheme(1);
                break;
            case 3:
                android.util.Log.e(TAG, "getThemeType: Theme Simple");
                com.autopai.applist.model.config.AppsConfig.setCurrentTheme(2);
                break;
        }
    }

    private void initData() {
        java.util.List<com.autopai.applist.model.infos.AppInfo> mAppInfo = new java.util.ArrayList<>();
        java.util.Locale locale = java.util.Locale.getDefault();
        android.util.Log.d(TAG, "initData: " + locale.getLanguage());
        if (locale.getLanguage().equals("en")) {
            for (int i = 0; i < com.autopai.applist.model.config.AppsConfig.favoritesAppList.size(); i++) {
                com.autopai.applist.model.infos.AppInfo appInfo = new com.autopai.applist.model.infos.AppInfo(getContext(), i, com.autopai.applist.model.config.AppsConfig.packageEnNameAppList.get(i), com.autopai.applist.model.config.AppsConfig.favoritesAppList.get(i));
                mAppInfo.add(appInfo);
            }
        } else {
            for (int i2 = 0; i2 < com.autopai.applist.model.config.AppsConfig.favoritesAppList.size(); i2++) {
                com.autopai.applist.model.infos.AppInfo appInfo2 = new com.autopai.applist.model.infos.AppInfo(getContext(), i2, com.autopai.applist.model.config.AppsConfig.packageNameAppList.get(i2), com.autopai.applist.model.config.AppsConfig.favoritesAppList.get(i2));
                mAppInfo.add(appInfo2);
            }
        }
        this.mAdapter.setData(mAppInfo);
    }

    private void initView(android.content.Context context) {
        setBackground(context);
        com.autopai.applist.view.CustomRecyclerView mRecyclerView = new com.autopai.applist.view.CustomRecyclerView(context);
        mRecyclerView.setPadding(80, 60, 80, 84);
        mRecyclerView.setLayoutParams(new android.widget.FrameLayout.LayoutParams(-1, -1));
        mRecyclerView.setFitsSystemWindows(true);
        mRecyclerView.setOverScrollMode(2);
        this.mAdapter = new com.autopai.applist.adapter.AppListAdapter(getContext());
        com.autopai.applist.manager.HorizontalPageLayoutManager layoutManager = new com.autopai.applist.manager.HorizontalPageLayoutManager(2, 5);
        addView(mRecyclerView);
        mRecyclerView.setLayoutManager(layoutManager);
        this.mPagingItemDecoration = new com.autopai.applist.manager.PagingItemDecoration(this.mContext);
        mRecyclerView.addItemDecoration(this.mPagingItemDecoration);
        mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.setOnItemClickListener(this);
        initTopRightView();
        com.autopai.applist.manager.DataStatisticsManager.initSdk(context);
    }

    private void initTopRightView() {
        try {
            this.mTopRightView = new com.autopai.support.view.TopRightView(getContext());
            this.mTopRightView.setThemeType(1);
            android.widget.FrameLayout.LayoutParams params = new android.widget.FrameLayout.LayoutParams(-2, -2);
            params.gravity = 53;
            this.mTopRightView.setLayoutParams(params);
            addView(this.mTopRightView);
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "mTopRightView is error" + e.getMessage());
            e.getMessage();
        }
    }

    private void initManager(android.content.Context context) {
        android.util.Log.i(TAG, "initManager");
        this.mAppsManager = new com.autopai.applist.manager.AppsManager(context);
        this.mAppsManager.setCallback(new com.autopai.applist.callback.AppsManagerCallback() { // from class: com.autopai.applist.view.AppsContentView.2
            @Override // com.autopai.applist.callback.AppsManagerCallback
            public void changeTheme() {
                android.util.Log.i(com.autopai.applist.view.AppsContentView.TAG, "changeTheme");
                com.autopai.applist.view.AppsContentView.this.mHandler.post(new java.lang.Runnable() { // from class: com.autopai.applist.view.AppsContentView.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        com.autopai.applist.view.AppsContentView.this.setBackground(com.autopai.applist.view.AppsContentView.this.getContext());
                        com.autopai.applist.view.AppsContentView.this.mPagingItemDecoration.initColor();
                        com.autopai.applist.view.AppsContentView.this.mAdapter.changeIcons();
                    }
                });
            }

            @Override // com.autopai.applist.callback.AppsManagerCallback
            public void onAppDataChange(java.util.List<com.autopai.applist.model.infos.AppInfo> data) {
                android.util.Log.i(com.autopai.applist.view.AppsContentView.TAG, "onAppDataChange-data.size()=" + data.size());
                com.autopai.applist.view.AppsContentView.this.mAdapter.setData(data);
            }

            @Override // com.autopai.applist.callback.AppsManagerCallback
            public void localeChanged() {
                android.util.Log.i(com.autopai.applist.view.AppsContentView.TAG, "localeChanged");
                com.autopai.applist.view.AppsContentView.this.post(new java.lang.Runnable() { // from class: com.autopai.applist.view.AppsContentView.2.2
                    @Override // java.lang.Runnable
                    public void run() {
                        com.autopai.applist.view.AppsContentView.this.mAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override // com.autopai.applist.callback.AppsManagerCallback
            public void setAppListAdapter(java.util.List<com.autopai.applist.model.infos.AppInfo> data) {
                android.util.Log.i(com.autopai.applist.view.AppsContentView.TAG, "setAppListAdapter");
                com.autopai.applist.view.AppsContentView.this.setAppListAdapterMethod(com.autopai.applist.view.AppsContentView.this.mAdapter, data);
            }
        });
    }

    public void setAppListAdapterMethod(com.autopai.applist.adapter.AppListAdapter adapter, java.util.List<com.autopai.applist.model.infos.AppInfo> data) {
        adapter.setData(data);
    }

    @Override // com.autopai.applist.adapter.AppListAdapter.OnItemClickListener
    public void onItemClick(android.view.View view) {
        android.util.Log.i(TAG, "onClickAppsIcon: View view = " + view);
        java.lang.Object tag = view.getTag();
        if (!(tag instanceof com.autopai.applist.model.infos.AppInfo)) {
            throw new java.lang.IllegalArgumentException("Input must be a App icon ");
        }
        com.autopai.applist.model.infos.AppInfo appInfo = (com.autopai.applist.model.infos.AppInfo) tag;
        android.content.ComponentName componentName = appInfo.componentName;
        android.content.Intent intent = appInfo.intent;
        android.util.Log.i(TAG, "onClickAppsIcon: componentName = " + componentName);
        android.util.Log.i(TAG, "onClickAppsIcon: intent = " + intent);
        if (componentName != null) {
            com.autopai.applist.manager.DataStatisticsManager.sendEvents(getContext(), componentName.getPackageName(), null);
        } else {
            com.autopai.applist.manager.DataStatisticsManager.sendEvents(getContext(), appInfo.packageName, null);
        }
        if (intent != null && componentName != null) {
            try {
                if (componentName.getPackageName().equals("com.tencent.wecarspeech") && componentName.getClassName().equals(com.autopai.applist.model.config.AppsConfig.APPS_TENCENT_WECARSPEECH_MORE_CLASSNAME)) {
                    int wt_speech_ready = android.provider.Settings.System.getInt(this.mContext.getContentResolver(), com.autopai.applist.model.config.AppsConfig.WT_SPEECH_READY);
                    if (wt_speech_ready != 1) {
                        com.autopai.applist.ToastUtils.showToast(this.mContext, this.mContext.getString(com.android.launcherWT.R.string.speech_content), 1);
                        return;
                    }
                }
            } catch (android.provider.Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            boolean flag = startAppsActivity(view, intent, tag);
            if (flag) {
                android.util.Log.i(TAG, "startActivity is success ");
                return;
            } else {
                android.util.Log.i(TAG, "startActivity is fail ");
                return;
            }
        }
        android.util.Log.e(TAG, "onItemClick intent: " + intent + ", componentName" + componentName);
    }

    private boolean startAppsActivity(android.view.View view, android.content.Intent intent, java.lang.Object tag) {
        boolean success = false;
        if (this.mIsSafeModeEnabled && !com.autopai.common.utils.common.Utilities.isSystemApp(this.mContext, intent)) {
            android.widget.Toast.makeText(this.mContext, com.autopai.common.utils.common.Utilities.getResourcesID(getContext(), "safe_mode_error", "string"), 0).show();
            return false;
        }
        if (this.mOnAppClickListener != null) {
            this.mOnAppClickListener.onAppClick();
        }
        try {
            success = startActivity(view, intent, tag);
        } catch (android.content.ActivityNotFoundException e) {
            android.widget.Toast.makeText(this.mContext, com.autopai.common.utils.common.Utilities.getResourcesID(getContext(), "activity_not_found", "string"), 0).show();
            android.util.Log.e(TAG, "Unable to launch. tag=" + tag + " intent=" + intent, e);
        }
        return success;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        android.util.Log.d(TAG, "onAttachedToWindow:");
        super.onAttachedToWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        this.mAppsManager.unInit();
        if (this.mTopRightView != null) {
            this.mTopRightView.destroy();
        }
        super.onDetachedFromWindow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBackground(android.content.Context context) {
        android.content.res.Resources resources;
        int bgId;
        if (com.autopai.applist.model.config.AppsConfig.mResourceConfig != null) {
            resources = com.autopai.applist.model.config.AppsConfig.mResourceConfig;
            bgId = com.autopai.applist.model.config.AppsConfig.mResourceConfig.getIdentifier(com.autopai.applist.model.config.AppsConfig.mConfigBackground, "drawable", com.autopai.applist.model.config.AppsConfig.RESOURCE_CONFIG_PACKAGE_NAME);
            if (bgId <= 0) {
                resources = getResources();
                bgId = com.autopai.common.utils.common.Utilities.getResourcesID(context, com.autopai.applist.manager.AppIconManager.getAppListBGResourceId(), "drawable");
            }
        } else {
            resources = getResources();
            bgId = com.autopai.common.utils.common.Utilities.getResourcesID(context, com.autopai.applist.manager.AppIconManager.getAppListBGResourceId(), "drawable");
        }
        android.util.Log.i(TAG, "setBackground: bgId=" + bgId);
        if (bgId > 0) {
            setBackground(resources.getDrawable(bgId));
        }
    }

    private boolean startActivity(android.view.View v, android.content.Intent intent, java.lang.Object tag) {
        intent.addFlags(268435456);
        com.autopai.applist.model.compat.LauncherAppsCompat launcherApps = com.autopai.applist.model.compat.LauncherAppsCompat.getInstance(this.mContext);
        com.autopai.applist.model.compat.UserManagerCompat userManager = com.autopai.applist.model.compat.UserManagerCompat.getInstance(this.mContext);
        com.autopai.applist.model.compat.UserHandleCompat user = null;
        if (intent.hasExtra(com.autopai.applist.model.infos.AppInfo.EXTRA_PROFILE)) {
            long serialNumber = intent.getLongExtra(com.autopai.applist.model.infos.AppInfo.EXTRA_PROFILE, -1L);
            user = userManager.getUserForSerialNumber(serialNumber);
        }
        if (user == null || user.equals(com.autopai.applist.model.compat.UserHandleCompat.myUserHandle())) {
            this.mContext.startActivity(intent, null);
            return true;
        }
        launcherApps.startActivityForProfile(intent.getComponent(), user, intent.getSourceBounds(), null);
        return true;
    }

    public void setOnAppClickListener(com.autopai.applist.view.AppsContentView.OnAppClickListener onAppClickListener) {
        this.mOnAppClickListener = onAppClickListener;
    }

    private java.lang.String getCurrentCarType() {
        java.lang.String curCarType = "";
        try {
            curCarType = com.incall.proxy.setting.SettingManager.getInstance().getCurCarType();
        } catch (java.lang.NoClassDefFoundError e) {
            e.printStackTrace();
        }
        android.util.Log.i(TAG, "getCurrentCarType: curCarType == " + curCarType);
        return curCarType;
    }
}
