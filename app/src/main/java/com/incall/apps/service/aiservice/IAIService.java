package com.incall.apps.service.aiservice;

/* loaded from: classes.dex */
public interface IAIService extends android.os.IInterface {
    void addCallback(com.incall.apps.service.aiservice.callback.IAICallback iAICallback) throws android.os.RemoteException;

    void clickBournForecast() throws android.os.RemoteException;

    void clickCompany() throws android.os.RemoteException;

    void clickHome() throws android.os.RemoteException;

    java.lang.String getBournForecastData() throws android.os.RemoteException;

    void removeCallback(com.incall.apps.service.aiservice.callback.IAICallback iAICallback) throws android.os.RemoteException;

    void requestNetwork(java.lang.String str, java.util.Map map, com.incall.apps.service.aiservice.callback.INetworkCallback iNetworkCallback) throws android.os.RemoteException;

    void uploadBehavior(java.lang.String str) throws android.os.RemoteException;

    public static abstract class Stub extends android.os.Binder implements com.incall.apps.service.aiservice.IAIService {
        private static final java.lang.String DESCRIPTOR = "com.incall.apps.service.aiservice.IAIService";
        static final int TRANSACTION_addCallback = 1;
        static final int TRANSACTION_clickBournForecast = 7;
        static final int TRANSACTION_clickCompany = 6;
        static final int TRANSACTION_clickHome = 5;
        static final int TRANSACTION_getBournForecastData = 3;
        static final int TRANSACTION_removeCallback = 2;
        static final int TRANSACTION_requestNetwork = 8;
        static final int TRANSACTION_uploadBehavior = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.incall.apps.service.aiservice.IAIService asInterface(android.os.IBinder obj) {
            if (obj == null) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof com.incall.apps.service.aiservice.IAIService)) {
                return (com.incall.apps.service.aiservice.IAIService) iin;
            }
            return new com.incall.apps.service.aiservice.IAIService.Stub.Proxy(obj);
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
                    com.incall.apps.service.aiservice.callback.IAICallback _arg0 = com.incall.apps.service.aiservice.callback.IAICallback.Stub.asInterface(data.readStrongBinder());
                    addCallback(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    com.incall.apps.service.aiservice.callback.IAICallback _arg02 = com.incall.apps.service.aiservice.callback.IAICallback.Stub.asInterface(data.readStrongBinder());
                    removeCallback(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _result = getBournForecastData();
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg03 = data.readString();
                    uploadBehavior(_arg03);
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    clickHome();
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    clickCompany();
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    clickBournForecast();
                    reply.writeNoException();
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg04 = data.readString();
                    java.lang.ClassLoader cl = getClass().getClassLoader();
                    java.util.Map _arg1 = data.readHashMap(cl);
                    com.incall.apps.service.aiservice.callback.INetworkCallback _arg2 = com.incall.apps.service.aiservice.callback.INetworkCallback.Stub.asInterface(data.readStrongBinder());
                    requestNetwork(_arg04, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements com.incall.apps.service.aiservice.IAIService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.incall.apps.service.aiservice.IAIService.Stub.DESCRIPTOR;
            }

            @Override // com.incall.apps.service.aiservice.IAIService
            public void addCallback(com.incall.apps.service.aiservice.callback.IAICallback callback) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.aiservice.IAIService.Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.aiservice.IAIService
            public void removeCallback(com.incall.apps.service.aiservice.callback.IAICallback callback) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.aiservice.IAIService.Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.aiservice.IAIService
            public java.lang.String getBournForecastData() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.aiservice.IAIService.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    java.lang.String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.aiservice.IAIService
            public void uploadBehavior(java.lang.String type) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.aiservice.IAIService.Stub.DESCRIPTOR);
                    _data.writeString(type);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.aiservice.IAIService
            public void clickHome() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.aiservice.IAIService.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.aiservice.IAIService
            public void clickCompany() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.aiservice.IAIService.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.aiservice.IAIService
            public void clickBournForecast() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.aiservice.IAIService.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.aiservice.IAIService
            public void requestNetwork(java.lang.String url, java.util.Map params, com.incall.apps.service.aiservice.callback.INetworkCallback callback) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.aiservice.IAIService.Stub.DESCRIPTOR);
                    _data.writeString(url);
                    _data.writeMap(params);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
