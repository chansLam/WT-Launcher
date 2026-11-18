package com.incall.apps.service.setting;

/* loaded from: classes.dex */
public interface ISkinChanageListener extends android.os.IInterface {
    void onSkinChanage(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    public static abstract class Stub extends android.os.Binder implements com.incall.apps.service.setting.ISkinChanageListener {
        private static final java.lang.String DESCRIPTOR = "com.incall.apps.service.setting.ISkinChanageListener";
        static final int TRANSACTION_onSkinChanage = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.incall.apps.service.setting.ISkinChanageListener asInterface(android.os.IBinder obj) {
            if (obj == null) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof com.incall.apps.service.setting.ISkinChanageListener)) {
                return (com.incall.apps.service.setting.ISkinChanageListener) iin;
            }
            return new com.incall.apps.service.setting.ISkinChanageListener.Stub.Proxy(obj);
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
                    java.lang.String _arg0 = data.readString();
                    java.lang.String _arg1 = data.readString();
                    onSkinChanage(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements com.incall.apps.service.setting.ISkinChanageListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.incall.apps.service.setting.ISkinChanageListener.Stub.DESCRIPTOR;
            }

            @Override // com.incall.apps.service.setting.ISkinChanageListener
            public void onSkinChanage(java.lang.String path, java.lang.String actions) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.setting.ISkinChanageListener.Stub.DESCRIPTOR);
                    _data.writeString(path);
                    _data.writeString(actions);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
