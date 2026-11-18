package com.incall.apps.spirit.elfservice;

/* loaded from: classes.dex */
public interface IELFService extends android.os.IInterface {
    void cancelELFDialog(java.lang.String str) throws android.os.RemoteException;

    void showELFDialog(com.incall.apps.spirit.elfservice.option.BaseDialogOption baseDialogOption, com.incall.apps.spirit.elfservice.callback.IELFDialogCallback iELFDialogCallback) throws android.os.RemoteException;

    void updateELFDialog(java.lang.String str, com.incall.apps.spirit.elfservice.option.BaseDialogOption baseDialogOption) throws android.os.RemoteException;

    void updateElFDialogByKey(java.lang.String str, com.incall.apps.spirit.elfservice.option.BaseDialogOption baseDialogOption, java.lang.String str2) throws android.os.RemoteException;

    public static abstract class Stub extends android.os.Binder implements com.incall.apps.spirit.elfservice.IELFService {
        private static final java.lang.String DESCRIPTOR = "com.incall.apps.spirit.elfservice.IELFService";
        static final int TRANSACTION_cancelELFDialog = 2;
        static final int TRANSACTION_showELFDialog = 1;
        static final int TRANSACTION_updateELFDialog = 3;
        static final int TRANSACTION_updateElFDialogByKey = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.incall.apps.spirit.elfservice.IELFService asInterface(android.os.IBinder obj) {
            if (obj == null) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof com.incall.apps.spirit.elfservice.IELFService)) {
                return (com.incall.apps.spirit.elfservice.IELFService) iin;
            }
            return new com.incall.apps.spirit.elfservice.IELFService.Stub.Proxy(obj);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
            com.incall.apps.spirit.elfservice.option.BaseDialogOption _arg1;
            com.incall.apps.spirit.elfservice.option.BaseDialogOption _arg12;
            com.incall.apps.spirit.elfservice.option.BaseDialogOption _arg0;
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = com.incall.apps.spirit.elfservice.option.BaseDialogOption.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    com.incall.apps.spirit.elfservice.callback.IELFDialogCallback _arg13 = com.incall.apps.spirit.elfservice.callback.IELFDialogCallback.Stub.asInterface(data.readStrongBinder());
                    showELFDialog(_arg0, _arg13);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg02 = data.readString();
                    cancelELFDialog(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg03 = data.readString();
                    if (data.readInt() != 0) {
                        _arg12 = com.incall.apps.spirit.elfservice.option.BaseDialogOption.CREATOR.createFromParcel(data);
                    } else {
                        _arg12 = null;
                    }
                    updateELFDialog(_arg03, _arg12);
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg04 = data.readString();
                    if (data.readInt() != 0) {
                        _arg1 = com.incall.apps.spirit.elfservice.option.BaseDialogOption.CREATOR.createFromParcel(data);
                    } else {
                        _arg1 = null;
                    }
                    java.lang.String _arg2 = data.readString();
                    updateElFDialogByKey(_arg04, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements com.incall.apps.spirit.elfservice.IELFService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.incall.apps.spirit.elfservice.IELFService.Stub.DESCRIPTOR;
            }

            @Override // com.incall.apps.spirit.elfservice.IELFService
            public void showELFDialog(com.incall.apps.spirit.elfservice.option.BaseDialogOption option, com.incall.apps.spirit.elfservice.callback.IELFDialogCallback callback) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.spirit.elfservice.IELFService.Stub.DESCRIPTOR);
                    if (option != null) {
                        _data.writeInt(1);
                        option.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.spirit.elfservice.IELFService
            public void cancelELFDialog(java.lang.String title) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.spirit.elfservice.IELFService.Stub.DESCRIPTOR);
                    _data.writeString(title);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.spirit.elfservice.IELFService
            public void updateELFDialog(java.lang.String title, com.incall.apps.spirit.elfservice.option.BaseDialogOption option) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.spirit.elfservice.IELFService.Stub.DESCRIPTOR);
                    _data.writeString(title);
                    if (option != null) {
                        _data.writeInt(1);
                        option.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.spirit.elfservice.IELFService
            public void updateElFDialogByKey(java.lang.String title, com.incall.apps.spirit.elfservice.option.BaseDialogOption option, java.lang.String key) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.spirit.elfservice.IELFService.Stub.DESCRIPTOR);
                    _data.writeString(title);
                    if (option != null) {
                        _data.writeInt(1);
                        option.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(key);
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
