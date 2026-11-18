package com.incall.apps.service.userservice;

/* loaded from: classes.dex */
public interface AccountChangeListener extends android.os.IInterface {
    void onAccoutnLogin() throws android.os.RemoteException;

    void onAccoutnLogout() throws android.os.RemoteException;

    void onAccoutnSwitch() throws android.os.RemoteException;

    public static abstract class Stub extends android.os.Binder implements com.incall.apps.service.userservice.AccountChangeListener {
        private static final java.lang.String DESCRIPTOR = "com.incall.apps.service.userservice.AccountChangeListener";
        static final int TRANSACTION_onAccoutnLogin = 1;
        static final int TRANSACTION_onAccoutnLogout = 3;
        static final int TRANSACTION_onAccoutnSwitch = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.incall.apps.service.userservice.AccountChangeListener asInterface(android.os.IBinder obj) {
            if (obj == null) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof com.incall.apps.service.userservice.AccountChangeListener)) {
                return (com.incall.apps.service.userservice.AccountChangeListener) iin;
            }
            return new com.incall.apps.service.userservice.AccountChangeListener.Stub.Proxy(obj);
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
                    onAccoutnLogin();
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    onAccoutnSwitch();
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    onAccoutnLogout();
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements com.incall.apps.service.userservice.AccountChangeListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.incall.apps.service.userservice.AccountChangeListener.Stub.DESCRIPTOR;
            }

            @Override // com.incall.apps.service.userservice.AccountChangeListener
            public void onAccoutnLogin() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.AccountChangeListener.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.AccountChangeListener
            public void onAccoutnSwitch() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.AccountChangeListener.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.AccountChangeListener
            public void onAccoutnLogout() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.AccountChangeListener.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
