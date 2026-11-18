package com.incall.apps.service.setting;

/* loaded from: classes.dex */
public interface ICommonViewShow extends android.os.IInterface {
    void isShowAirCleanView(boolean z) throws android.os.RemoteException;

    void isShowPlayContentView(boolean z) throws android.os.RemoteException;

    void isShowTopRightView(boolean z) throws android.os.RemoteException;

    void onSlideViewVisible(boolean z) throws android.os.RemoteException;

    public static abstract class Stub extends android.os.Binder implements com.incall.apps.service.setting.ICommonViewShow {
        private static final java.lang.String DESCRIPTOR = "com.incall.apps.service.setting.ICommonViewShow";
        static final int TRANSACTION_isShowAirCleanView = 2;
        static final int TRANSACTION_isShowPlayContentView = 3;
        static final int TRANSACTION_isShowTopRightView = 1;
        static final int TRANSACTION_onSlideViewVisible = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.incall.apps.service.setting.ICommonViewShow asInterface(android.os.IBinder obj) {
            if (obj == null) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof com.incall.apps.service.setting.ICommonViewShow)) {
                return (com.incall.apps.service.setting.ICommonViewShow) iin;
            }
            return new com.incall.apps.service.setting.ICommonViewShow.Stub.Proxy(obj);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
            boolean _arg0;
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0;
                    isShowTopRightView(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0;
                    isShowAirCleanView(_arg0);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0;
                    isShowPlayContentView(_arg0);
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt() != 0;
                    onSlideViewVisible(_arg0);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements com.incall.apps.service.setting.ICommonViewShow {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.incall.apps.service.setting.ICommonViewShow.Stub.DESCRIPTOR;
            }

            @Override // com.incall.apps.service.setting.ICommonViewShow
            public void isShowTopRightView(boolean isShow) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.setting.ICommonViewShow.Stub.DESCRIPTOR);
                    _data.writeInt(isShow ? 1 : 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.setting.ICommonViewShow
            public void isShowAirCleanView(boolean isShow) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.setting.ICommonViewShow.Stub.DESCRIPTOR);
                    _data.writeInt(isShow ? 1 : 0);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.setting.ICommonViewShow
            public void isShowPlayContentView(boolean isShow) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.setting.ICommonViewShow.Stub.DESCRIPTOR);
                    _data.writeInt(isShow ? 1 : 0);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.setting.ICommonViewShow
            public void onSlideViewVisible(boolean isShow) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.setting.ICommonViewShow.Stub.DESCRIPTOR);
                    _data.writeInt(isShow ? 1 : 0);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
