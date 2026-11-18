package com.incall.apps.spirit.elfservice.callback;

/* loaded from: classes.dex */
public interface IELFDialogCallback extends android.os.IInterface {
    void onClick(int i) throws android.os.RemoteException;

    void onDismiss(int i) throws android.os.RemoteException;

    void onShow() throws android.os.RemoteException;

    void onVoiceResult(java.lang.String str) throws android.os.RemoteException;

    public static abstract class Stub extends android.os.Binder implements com.incall.apps.spirit.elfservice.callback.IELFDialogCallback {
        private static final java.lang.String DESCRIPTOR = "com.incall.apps.spirit.elfservice.callback.IELFDialogCallback";
        static final int TRANSACTION_onClick = 1;
        static final int TRANSACTION_onDismiss = 3;
        static final int TRANSACTION_onShow = 2;
        static final int TRANSACTION_onVoiceResult = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.incall.apps.spirit.elfservice.callback.IELFDialogCallback asInterface(android.os.IBinder obj) {
            if (obj == null) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof com.incall.apps.spirit.elfservice.callback.IELFDialogCallback)) {
                return (com.incall.apps.spirit.elfservice.callback.IELFDialogCallback) iin;
            }
            return new com.incall.apps.spirit.elfservice.callback.IELFDialogCallback.Stub.Proxy(obj);
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
                    int _arg0 = data.readInt();
                    onClick(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    onShow();
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    onDismiss(_arg02);
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg03 = data.readString();
                    onVoiceResult(_arg03);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements com.incall.apps.spirit.elfservice.callback.IELFDialogCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.incall.apps.spirit.elfservice.callback.IELFDialogCallback.Stub.DESCRIPTOR;
            }

            @Override // com.incall.apps.spirit.elfservice.callback.IELFDialogCallback
            public void onClick(int area) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.spirit.elfservice.callback.IELFDialogCallback.Stub.DESCRIPTOR);
                    _data.writeInt(area);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.spirit.elfservice.callback.IELFDialogCallback
            public void onShow() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.spirit.elfservice.callback.IELFDialogCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.spirit.elfservice.callback.IELFDialogCallback
            public void onDismiss(int action) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.spirit.elfservice.callback.IELFDialogCallback.Stub.DESCRIPTOR);
                    _data.writeInt(action);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.spirit.elfservice.callback.IELFDialogCallback
            public void onVoiceResult(java.lang.String cmdId) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.spirit.elfservice.callback.IELFDialogCallback.Stub.DESCRIPTOR);
                    _data.writeString(cmdId);
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
