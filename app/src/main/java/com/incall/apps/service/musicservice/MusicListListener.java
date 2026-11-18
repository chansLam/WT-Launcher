package com.incall.apps.service.musicservice;

/* loaded from: classes.dex */
public interface MusicListListener extends android.os.IInterface {
    void onListChange(java.lang.String str) throws android.os.RemoteException;

    public static abstract class Stub extends android.os.Binder implements com.incall.apps.service.musicservice.MusicListListener {
        private static final java.lang.String DESCRIPTOR = "com.incall.apps.service.musicservice.MusicListListener";
        static final int TRANSACTION_onListChange = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.incall.apps.service.musicservice.MusicListListener asInterface(android.os.IBinder obj) {
            if (obj == null) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof com.incall.apps.service.musicservice.MusicListListener)) {
                return (com.incall.apps.service.musicservice.MusicListListener) iin;
            }
            return new com.incall.apps.service.musicservice.MusicListListener.Stub.Proxy(obj);
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
                    onListChange(_arg0);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements com.incall.apps.service.musicservice.MusicListListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.incall.apps.service.musicservice.MusicListListener.Stub.DESCRIPTOR;
            }

            @Override // com.incall.apps.service.musicservice.MusicListListener
            public void onListChange(java.lang.String json) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.musicservice.MusicListListener.Stub.DESCRIPTOR);
                    _data.writeString(json);
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
