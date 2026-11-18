package com.autopai.applist.adapter;

/* loaded from: classes.dex */
public class AppListAdapter extends android.support.v7.widget.RecyclerView.Adapter<com.autopai.applist.adapter.AppListAdapter.RvViewHolder> implements android.view.View.OnClickListener {
    private static final java.lang.String TAG = "AppListAdapter";
    private java.util.List<com.autopai.applist.model.infos.AppInfo> appInfo;
    private android.database.ContentObserver mContentObserver;
    private android.content.Context mContext;
    private com.autopai.applist.adapter.AppListAdapter.OnItemClickListener mOnItemClickListener;
    private int mUpgrade;
    private com.autopai.applist.view.CustomLinearLayout mUpgradeView;
    private boolean isRegister = false;
    final android.os.Handler mHandler = new android.os.Handler(new android.os.Handler.Callback() { // from class: com.autopai.applist.adapter.AppListAdapter.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(android.os.Message msg) {
            return true;
        }
    });

    public interface OnItemClickListener {
        void onItemClick(android.view.View view);
    }

    public AppListAdapter(android.content.Context context) {
        this.mContext = context;
        setHasStableIds(true);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public long getItemId(int position) {
        return position;
    }

    public void setData(java.util.List<com.autopai.applist.model.infos.AppInfo> appInfo) {
        if (appInfo == null) {
            appInfo = new java.util.ArrayList<>();
        }
        android.util.Log.i(TAG, "setNewData: appInfo.size() = " + appInfo.size());
        this.appInfo = appInfo;
        sort();
        notifyDataSetChanged();
    }

    public void changeIcons() {
        for (com.autopai.applist.model.infos.AppInfo app : this.appInfo) {
            app.updateIcon(this.mContext);
        }
        notifyDataSetChanged();
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public com.autopai.applist.adapter.AppListAdapter.RvViewHolder onCreateViewHolder(@android.support.annotation.NonNull android.view.ViewGroup parent, int viewType) {
        android.view.View view = createItemView(parent);
        android.util.Log.e(TAG, "onCreateViewHolder: " + view.hashCode());
        return new com.autopai.applist.adapter.AppListAdapter.RvViewHolder(view);
    }

    private android.view.View createItemView(android.view.ViewGroup parent) {
        return new com.autopai.applist.view.CustomLinearLayout(parent.getContext());
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(@android.support.annotation.NonNull com.autopai.applist.adapter.AppListAdapter.RvViewHolder holder, int position) {
        android.util.Log.i(TAG, "onBindViewHolder: int position" + position);
        com.autopai.applist.model.infos.AppInfo appInfo = getItem(position);
        holder.customLinearLayout.setTag(appInfo);
        holder.customLinearLayout.setOnClickListener(this);
        holder.customLinearLayout.getTextView().setText(appInfo.title.toString());
        if (appInfo.componentName != null) {
            if (appInfo.componentName.getPackageName().equals(com.autopai.applist.model.config.AppsConfig.APPS_SOFT_MANAGER)) {
                this.mUpgradeView = holder.customLinearLayout;
                if (!this.isRegister) {
                    registerChangeBadgeView();
                    this.isRegister = true;
                }
                if (isUpgrade()) {
                    this.mUpgradeView.setShowBadge(true);
                    return;
                } else {
                    this.mUpgradeView.setShowBadge(false);
                    return;
                }
            }
            holder.customLinearLayout.getImageView().setImageDrawable(appInfo.iconDrawable);
            return;
        }
        holder.customLinearLayout.getImageView().setImageDrawable(appInfo.iconDrawable);
    }

    private com.autopai.applist.model.infos.AppInfo getItem(int position) {
        com.autopai.applist.model.infos.AppInfo app = this.appInfo.get(position);
        android.util.Log.i(TAG, "getItem: appInfo : " + ((java.lang.Object) app.title) + "--" + app.intent);
        return app;
    }

    public void add(java.util.List<com.autopai.applist.model.infos.AppInfo> data) {
        for (com.autopai.applist.model.infos.AppInfo app : data) {
            if (!this.appInfo.contains(app)) {
                this.appInfo.add(app);
            }
        }
        sort();
        notifyDataSetChanged();
    }

    public void update(java.util.List<com.autopai.applist.model.infos.AppInfo> data) {
        for (com.autopai.applist.model.infos.AppInfo app : data) {
            int index = this.appInfo.indexOf(app);
            if (index != -1) {
                this.appInfo.remove(index);
                this.appInfo.add(index, app);
            }
        }
        sort();
        notifyDataSetChanged();
    }

    public void remove(java.util.List<com.autopai.applist.model.infos.AppInfo> data) {
        java.util.List<com.autopai.applist.model.infos.AppInfo> remove = new java.util.ArrayList<>();
        for (com.autopai.applist.model.infos.AppInfo app : data) {
            if (!this.appInfo.contains(app)) {
                remove.add(app);
            }
        }
        this.appInfo.removeAll(remove);
        sort();
        notifyDataSetChanged();
    }

    private void sort() {
        if (com.autopai.applist.model.config.AppsConfig.FILTER_APP_LIST) {
            java.util.Collections.sort(this.appInfo, new java.util.Comparator<com.autopai.applist.model.infos.AppInfo>() { // from class: com.autopai.applist.adapter.AppListAdapter.2
                @Override // java.util.Comparator
                public int compare(com.autopai.applist.model.infos.AppInfo o1, com.autopai.applist.model.infos.AppInfo o2) {
                    return java.lang.Long.compare(o1.id, o2.id);
                }
            });
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (this.appInfo != null) {
            return this.appInfo.size();
        }
        return 0;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(android.view.View view) {
        if (this.mOnItemClickListener != null) {
            this.mOnItemClickListener.onItemClick(view);
        }
    }

    public void setOnItemClickListener(com.autopai.applist.adapter.AppListAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    class RvViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        com.autopai.applist.view.CustomLinearLayout customLinearLayout;

        public RvViewHolder(android.view.View itemView) {
            super(itemView);
            this.customLinearLayout = (com.autopai.applist.view.CustomLinearLayout) itemView;
        }
    }

    public void registerChangeBadgeView() {
        this.mContentObserver = new android.database.ContentObserver(this.mHandler) { // from class: com.autopai.applist.adapter.AppListAdapter.3
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange, android.net.Uri uri) {
                super.onChange(selfChange, uri);
                int upgrade = android.provider.Settings.System.getInt(com.autopai.applist.adapter.AppListAdapter.this.mContext.getContentResolver(), com.autopai.applist.model.config.AppsConfig.KEY_BADGE, -1);
                if (com.autopai.applist.adapter.AppListAdapter.this.mUpgrade != upgrade) {
                    android.util.Log.d(com.autopai.applist.adapter.AppListAdapter.TAG, "badge onChange: " + upgrade);
                    if (com.autopai.applist.adapter.AppListAdapter.this.mUpgradeView != null) {
                        switch (upgrade) {
                            case 0:
                                com.autopai.applist.adapter.AppListAdapter.this.mUpgradeView.setShowBadge(false);
                                break;
                            case 1:
                                com.autopai.applist.adapter.AppListAdapter.this.mUpgradeView.setShowBadge(true);
                                break;
                        }
                        com.autopai.applist.adapter.AppListAdapter.this.notifyDataSetChanged();
                    }
                    com.autopai.applist.adapter.AppListAdapter.this.mUpgrade = upgrade;
                }
            }
        };
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, this.mContentObserver);
    }

    private boolean isUpgrade() {
        this.mUpgrade = android.provider.Settings.System.getInt(this.mContext.getContentResolver(), com.autopai.applist.model.config.AppsConfig.KEY_BADGE, -1);
        switch (this.mUpgrade) {
            case 0:
                android.util.Log.d(TAG, "isUpgrade: hide badge");
                break;
            case 1:
                android.util.Log.d(TAG, "isUpgrade: show badge");
                break;
            default:
                android.util.Log.e(TAG, "isUpgrade: acquire upgrade is fail");
                break;
        }
        return false;
    }
}
