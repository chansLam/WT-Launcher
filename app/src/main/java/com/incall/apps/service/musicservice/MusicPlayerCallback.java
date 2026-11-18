package com.incall.apps.service.musicservice;

/* loaded from: classes.dex */
public interface MusicPlayerCallback extends android.os.IInterface {
    void changeMode() throws android.os.RemoteException;

    void next() throws android.os.RemoteException;

    void play(java.lang.String str) throws android.os.RemoteException;

    void playOrPause() throws android.os.RemoteException;

    void prev() throws android.os.RemoteException;

    public static abstract class Stub extends android.os.Binder implements com.incall.apps.service.musicservice.MusicPlayerCallback {
        private static final java.lang.String DESCRIPTOR = "com.incall.apps.service.musicservice.MusicPlayerCallback";
        static final int TRANSACTION_changeMode = 5;
        static final int TRANSACTION_next = 3;
        static final int TRANSACTION_play = 1;
        static final int TRANSACTION_playOrPause = 2;
        static final int TRANSACTION_prev = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.incall.apps.service.musicservice.MusicPlayerCallback asInterface(android.os.IBinder obj) {
            if (obj == null) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof com.incall.apps.service.musicservice.MusicPlayerCallback)) {
                return (com.incall.apps.service.musicservice.MusicPlayerCallback) iin;
            }
            return new com.incall.apps.service.musicservice.MusicPlayerCallback.Stub.Proxy(obj);
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
                    play(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    playOrPause();
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    next();
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    prev();
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    changeMode();
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements com.incall.apps.service.musicservice.MusicPlayerCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.incall.apps.service.musicservice.MusicPlayerCallback.Stub.DESCRIPTOR;
            }

            @Override // com.incall.apps.service.musicservice.MusicPlayerCallback
            public void play(java.lang.String path) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.musicservice.MusicPlayerCallback.Stub.DESCRIPTOR);
                    _data.writeString(path);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.musicservice.MusicPlayerCallback
            public void playOrPause() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.musicservice.MusicPlayerCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.musicservice.MusicPlayerCallback
            public void next() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.musicservice.MusicPlayerCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.musicservice.MusicPlayerCallback
            public void prev() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.musicservice.MusicPlayerCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.musicservice.MusicPlayerCallback
            public void changeMode() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.musicservice.MusicPlayerCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
