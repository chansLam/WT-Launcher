package com.incall.apps.service.aiservice.callback;

/* loaded from: classes.dex */
public interface INetworkCallback extends android.os.IInterface {
    void onError(int i, java.lang.String str) throws android.os.RemoteException;

    void onSuccess(java.lang.String str) throws android.os.RemoteException;

    public static abstract class Stub extends android.os.Binder implements com.incall.apps.service.aiservice.callback.INetworkCallback {
        private static final java.lang.String DESCRIPTOR = "com.incall.apps.service.aiservice.callback.INetworkCallback";
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.incall.apps.service.aiservice.callback.INetworkCallback asInterface(android.os.IBinder obj) {
            if (obj == null) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof com.incall.apps.service.aiservice.callback.INetworkCallback)) {
                return (com.incall.apps.service.aiservice.callback.INetworkCallback) iin;
            }
            return new com.incall.apps.service.aiservice.callback.INetworkCallback.Stub.Proxy(obj);
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
                    onSuccess(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    java.lang.String _arg1 = data.readString();
                    onError(_arg02, _arg1);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements com.incall.apps.service.aiservice.callback.INetworkCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.incall.apps.service.aiservice.callback.INetworkCallback.Stub.DESCRIPTOR;
            }

            @Override // com.incall.apps.service.aiservice.callback.INetworkCallback
            public void onSuccess(java.lang.String data) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.aiservice.callback.INetworkCallback.Stub.DESCRIPTOR);
                    _data.writeString(data);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.aiservice.callback.INetworkCallback
            public void onError(int type, java.lang.String msg) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.aiservice.callback.INetworkCallback.Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(msg);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
