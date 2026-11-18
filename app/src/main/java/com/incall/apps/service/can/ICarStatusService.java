package com.incall.apps.service.can;

/* loaded from: classes.dex */
public interface ICarStatusService extends android.os.IInterface {
    void addCarStatusListener(com.incall.apps.service.can.CarStatusListener carStatusListener) throws android.os.RemoteException;

    void closeLog() throws android.os.RemoteException;

    void openLog() throws android.os.RemoteException;

    void removeCarStatusListener(com.incall.apps.service.can.CarStatusListener carStatusListener) throws android.os.RemoteException;

    public static abstract class Stub extends android.os.Binder implements com.incall.apps.service.can.ICarStatusService {
        private static final java.lang.String DESCRIPTOR = "com.incall.apps.service.can.ICarStatusService";
        static final int TRANSACTION_addCarStatusListener = 1;
        static final int TRANSACTION_closeLog = 4;
        static final int TRANSACTION_openLog = 3;
        static final int TRANSACTION_removeCarStatusListener = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.incall.apps.service.can.ICarStatusService asInterface(android.os.IBinder obj) {
            if (obj == null) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof com.incall.apps.service.can.ICarStatusService)) {
                return (com.incall.apps.service.can.ICarStatusService) iin;
            }
            return new com.incall.apps.service.can.ICarStatusService.Stub.Proxy(obj);
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
                    com.incall.apps.service.can.CarStatusListener _arg0 = com.incall.apps.service.can.CarStatusListener.Stub.asInterface(data.readStrongBinder());
                    addCarStatusListener(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    com.incall.apps.service.can.CarStatusListener _arg02 = com.incall.apps.service.can.CarStatusListener.Stub.asInterface(data.readStrongBinder());
                    removeCarStatusListener(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    openLog();
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    closeLog();
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements com.incall.apps.service.can.ICarStatusService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.incall.apps.service.can.ICarStatusService.Stub.DESCRIPTOR;
            }

            @Override // com.incall.apps.service.can.ICarStatusService
            public void addCarStatusListener(com.incall.apps.service.can.CarStatusListener listener) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.can.ICarStatusService.Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.can.ICarStatusService
            public void removeCarStatusListener(com.incall.apps.service.can.CarStatusListener listener) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.can.ICarStatusService.Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.can.ICarStatusService
            public void openLog() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.can.ICarStatusService.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.can.ICarStatusService
            public void closeLog() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.can.ICarStatusService.Stub.DESCRIPTOR);
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
