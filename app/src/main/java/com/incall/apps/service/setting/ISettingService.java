package com.incall.apps.service.setting;

/* loaded from: classes.dex */
public interface ISettingService extends android.os.IInterface {
    void colseHUScreen(java.lang.String str) throws android.os.RemoteException;

    void dispatchSkinBroadcast(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int getTheme() throws android.os.RemoteException;

    void isShowAirCleanView(boolean z) throws android.os.RemoteException;

    void isShowPlayContentView(boolean z) throws android.os.RemoteException;

    void isShowTopRightView(boolean z) throws android.os.RemoteException;

    void openHUScreen() throws android.os.RemoteException;

    void registerSkinChangeListener(com.incall.apps.service.setting.ISkinChanageListener iSkinChanageListener) throws android.os.RemoteException;

    void registerThemeChangeListener(com.incall.apps.service.setting.IOnThemeChange iOnThemeChange) throws android.os.RemoteException;

    void setOnCommonViewShowListener(com.incall.apps.service.setting.ICommonViewShow iCommonViewShow) throws android.os.RemoteException;

    void setSlideViewVisible(boolean z) throws android.os.RemoteException;

    boolean setTheme(int i) throws android.os.RemoteException;

    void unRegisterSkinChangeListener(com.incall.apps.service.setting.ISkinChanageListener iSkinChanageListener) throws android.os.RemoteException;

    void unRegisterThemeChangeListener(com.incall.apps.service.setting.IOnThemeChange iOnThemeChange) throws android.os.RemoteException;

    public static abstract class Stub extends android.os.Binder implements com.incall.apps.service.setting.ISettingService {
        private static final java.lang.String DESCRIPTOR = "com.incall.apps.service.setting.ISettingService";
        static final int TRANSACTION_colseHUScreen = 10;
        static final int TRANSACTION_dispatchSkinBroadcast = 14;
        static final int TRANSACTION_getTheme = 4;
        static final int TRANSACTION_isShowAirCleanView = 6;
        static final int TRANSACTION_isShowPlayContentView = 7;
        static final int TRANSACTION_isShowTopRightView = 5;
        static final int TRANSACTION_openHUScreen = 11;
        static final int TRANSACTION_registerSkinChangeListener = 12;
        static final int TRANSACTION_registerThemeChangeListener = 1;
        static final int TRANSACTION_setOnCommonViewShowListener = 8;
        static final int TRANSACTION_setSlideViewVisible = 9;
        static final int TRANSACTION_setTheme = 3;
        static final int TRANSACTION_unRegisterSkinChangeListener = 13;
        static final int TRANSACTION_unRegisterThemeChangeListener = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.incall.apps.service.setting.ISettingService asInterface(android.os.IBinder obj) {
            if (obj == null) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof com.incall.apps.service.setting.ISettingService)) {
                return (com.incall.apps.service.setting.ISettingService) iin;
            }
            return new com.incall.apps.service.setting.ISettingService.Stub.Proxy(obj);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    com.incall.apps.service.setting.IOnThemeChange _arg0 = com.incall.apps.service.setting.IOnThemeChange.Stub.asInterface(data.readStrongBinder());
                    registerThemeChangeListener(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    com.incall.apps.service.setting.IOnThemeChange _arg02 = com.incall.apps.service.setting.IOnThemeChange.Stub.asInterface(data.readStrongBinder());
                    unRegisterThemeChangeListener(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg03 = data.readInt();
                    boolean _result = setTheme(_arg03);
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    int _result2 = getTheme();
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg04 = data.readInt() != 0;
                    isShowTopRightView(_arg04);
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg05 = data.readInt() != 0;
                    isShowAirCleanView(_arg05);
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg06 = data.readInt() != 0;
                    isShowPlayContentView(_arg06);
                    reply.writeNoException();
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    com.incall.apps.service.setting.ICommonViewShow _arg07 = com.incall.apps.service.setting.ICommonViewShow.Stub.asInterface(data.readStrongBinder());
                    setOnCommonViewShowListener(_arg07);
                    reply.writeNoException();
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg08 = data.readInt() != 0;
                    setSlideViewVisible(_arg08);
                    reply.writeNoException();
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg09 = data.readString();
                    colseHUScreen(_arg09);
                    reply.writeNoException();
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    openHUScreen();
                    reply.writeNoException();
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    com.incall.apps.service.setting.ISkinChanageListener _arg010 = com.incall.apps.service.setting.ISkinChanageListener.Stub.asInterface(data.readStrongBinder());
                    registerSkinChangeListener(_arg010);
                    reply.writeNoException();
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    com.incall.apps.service.setting.ISkinChanageListener _arg011 = com.incall.apps.service.setting.ISkinChanageListener.Stub.asInterface(data.readStrongBinder());
                    unRegisterSkinChangeListener(_arg011);
                    reply.writeNoException();
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg012 = data.readString();
                    java.lang.String _arg1 = data.readString();
                    dispatchSkinBroadcast(_arg012, _arg1);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements com.incall.apps.service.setting.ISettingService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.incall.apps.service.setting.ISettingService.Stub.DESCRIPTOR;
            }

            @Override // com.incall.apps.service.setting.ISettingService
            public void registerThemeChangeListener(com.incall.apps.service.setting.IOnThemeChange onThemeChange) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.setting.ISettingService.Stub.DESCRIPTOR);
                    _data.writeStrongBinder(onThemeChange != null ? onThemeChange.asBinder() : null);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.setting.ISettingService
            public void unRegisterThemeChangeListener(com.incall.apps.service.setting.IOnThemeChange onThemeChange) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.setting.ISettingService.Stub.DESCRIPTOR);
                    _data.writeStrongBinder(onThemeChange != null ? onThemeChange.asBinder() : null);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.setting.ISettingService
            public boolean setTheme(int theme) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.setting.ISettingService.Stub.DESCRIPTOR);
                    _data.writeInt(theme);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.setting.ISettingService
            public int getTheme() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.setting.ISettingService.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.setting.ISettingService
            public void isShowTopRightView(boolean isShow) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.setting.ISettingService.Stub.DESCRIPTOR);
                    _data.writeInt(isShow ? 1 : 0);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.setting.ISettingService
            public void isShowAirCleanView(boolean isShow) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.setting.ISettingService.Stub.DESCRIPTOR);
                    _data.writeInt(isShow ? 1 : 0);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.setting.ISettingService
            public void isShowPlayContentView(boolean isShow) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.setting.ISettingService.Stub.DESCRIPTOR);
                    _data.writeInt(isShow ? 1 : 0);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.setting.ISettingService
            public void setOnCommonViewShowListener(com.incall.apps.service.setting.ICommonViewShow commonViewShow) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.setting.ISettingService.Stub.DESCRIPTOR);
                    _data.writeStrongBinder(commonViewShow != null ? commonViewShow.asBinder() : null);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.setting.ISettingService
            public void setSlideViewVisible(boolean isShow) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.setting.ISettingService.Stub.DESCRIPTOR);
                    _data.writeInt(isShow ? 1 : 0);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.setting.ISettingService
            public void colseHUScreen(java.lang.String type) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.setting.ISettingService.Stub.DESCRIPTOR);
                    _data.writeString(type);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.setting.ISettingService
            public void openHUScreen() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.setting.ISettingService.Stub.DESCRIPTOR);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.setting.ISettingService
            public void registerSkinChangeListener(com.incall.apps.service.setting.ISkinChanageListener onSkinChange) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.setting.ISettingService.Stub.DESCRIPTOR);
                    _data.writeStrongBinder(onSkinChange != null ? onSkinChange.asBinder() : null);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.setting.ISettingService
            public void unRegisterSkinChangeListener(com.incall.apps.service.setting.ISkinChanageListener onSkinChanged) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.setting.ISettingService.Stub.DESCRIPTOR);
                    _data.writeStrongBinder(onSkinChanged != null ? onSkinChanged.asBinder() : null);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.setting.ISettingService
            public void dispatchSkinBroadcast(java.lang.String path, java.lang.String actions) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.setting.ISettingService.Stub.DESCRIPTOR);
                    _data.writeString(path);
                    _data.writeString(actions);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
