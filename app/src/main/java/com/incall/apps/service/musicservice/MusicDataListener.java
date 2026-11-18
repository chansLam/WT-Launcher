package com.incall.apps.service.musicservice;

/* loaded from: classes.dex */
public interface MusicDataListener extends android.os.IInterface {
    void onMusicDataChange(com.incall.apps.service.musicservice.MusicData musicData) throws android.os.RemoteException;

    public static abstract class Stub extends android.os.Binder implements com.incall.apps.service.musicservice.MusicDataListener {
        private static final java.lang.String DESCRIPTOR = "com.incall.apps.service.musicservice.MusicDataListener";
        static final int TRANSACTION_onMusicDataChange = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.incall.apps.service.musicservice.MusicDataListener asInterface(android.os.IBinder obj) {
            if (obj == null) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof com.incall.apps.service.musicservice.MusicDataListener)) {
                return (com.incall.apps.service.musicservice.MusicDataListener) iin;
            }
            return new com.incall.apps.service.musicservice.MusicDataListener.Stub.Proxy(obj);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
            com.incall.apps.service.musicservice.MusicData _arg0;
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = com.incall.apps.service.musicservice.MusicData.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    onMusicDataChange(_arg0);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements com.incall.apps.service.musicservice.MusicDataListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.incall.apps.service.musicservice.MusicDataListener.Stub.DESCRIPTOR;
            }

            @Override // com.incall.apps.service.musicservice.MusicDataListener
            public void onMusicDataChange(com.incall.apps.service.musicservice.MusicData data) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.musicservice.MusicDataListener.Stub.DESCRIPTOR);
                    if (data != null) {
                        _data.writeInt(1);
                        data.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
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
