package com.incall.apps.service.kuwoservice;

/* loaded from: classes.dex */
public interface IKuwoService extends android.os.IInterface {
    boolean isKuwoPlaying() throws android.os.RemoteException;

    void openKuwoMusic() throws android.os.RemoteException;

    public static abstract class Stub extends android.os.Binder implements com.incall.apps.service.kuwoservice.IKuwoService {
        private static final java.lang.String DESCRIPTOR = "com.incall.apps.service.kuwoservice.IKuwoService";
        static final int TRANSACTION_isKuwoPlaying = 2;
        static final int TRANSACTION_openKuwoMusic = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.incall.apps.service.kuwoservice.IKuwoService asInterface(android.os.IBinder obj) {
            if (obj == null) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof com.incall.apps.service.kuwoservice.IKuwoService)) {
                return (com.incall.apps.service.kuwoservice.IKuwoService) iin;
            }
            return new com.incall.apps.service.kuwoservice.IKuwoService.Stub.Proxy(obj);
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
                    openKuwoMusic();
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result = isKuwoPlaying();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements com.incall.apps.service.kuwoservice.IKuwoService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.incall.apps.service.kuwoservice.IKuwoService.Stub.DESCRIPTOR;
            }

            @Override // com.incall.apps.service.kuwoservice.IKuwoService
            public void openKuwoMusic() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.kuwoservice.IKuwoService.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.kuwoservice.IKuwoService
            public boolean isKuwoPlaying() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.kuwoservice.IKuwoService.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
