package com.incall.apps.service.musicservice;

/* loaded from: classes.dex */
public interface IMusicService extends android.os.IInterface {
    void addMusicDataListener(com.incall.apps.service.musicservice.MusicDataListener musicDataListener) throws android.os.RemoteException;

    void addMusicListListener(com.incall.apps.service.musicservice.MusicListListener musicListListener) throws android.os.RemoteException;

    void changeMode() throws android.os.RemoteException;

    void next() throws android.os.RemoteException;

    void play(java.lang.String str) throws android.os.RemoteException;

    void playOrPause() throws android.os.RemoteException;

    void prev() throws android.os.RemoteException;

    void removeMusicDataListener(com.incall.apps.service.musicservice.MusicDataListener musicDataListener) throws android.os.RemoteException;

    void removeMusicListListener(com.incall.apps.service.musicservice.MusicListListener musicListListener) throws android.os.RemoteException;

    void setMusicDataListener(com.incall.apps.service.musicservice.MusicDataListener musicDataListener) throws android.os.RemoteException;

    void setMusicListListener(com.incall.apps.service.musicservice.MusicListListener musicListListener) throws android.os.RemoteException;

    void setMusicPlayerCallback(com.incall.apps.service.musicservice.MusicPlayerCallback musicPlayerCallback) throws android.os.RemoteException;

    void updateMusicData(com.incall.apps.service.musicservice.MusicData musicData) throws android.os.RemoteException;

    void updateMusicList(java.lang.String str) throws android.os.RemoteException;

    public static abstract class Stub extends android.os.Binder implements com.incall.apps.service.musicservice.IMusicService {
        private static final java.lang.String DESCRIPTOR = "com.incall.apps.service.musicservice.IMusicService";
        static final int TRANSACTION_addMusicDataListener = 11;
        static final int TRANSACTION_addMusicListListener = 9;
        static final int TRANSACTION_changeMode = 6;
        static final int TRANSACTION_next = 4;
        static final int TRANSACTION_play = 2;
        static final int TRANSACTION_playOrPause = 3;
        static final int TRANSACTION_prev = 5;
        static final int TRANSACTION_removeMusicDataListener = 12;
        static final int TRANSACTION_removeMusicListListener = 10;
        static final int TRANSACTION_setMusicDataListener = 14;
        static final int TRANSACTION_setMusicListListener = 13;
        static final int TRANSACTION_setMusicPlayerCallback = 1;
        static final int TRANSACTION_updateMusicData = 7;
        static final int TRANSACTION_updateMusicList = 8;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.incall.apps.service.musicservice.IMusicService asInterface(android.os.IBinder obj) {
            if (obj == null) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof com.incall.apps.service.musicservice.IMusicService)) {
                return (com.incall.apps.service.musicservice.IMusicService) iin;
            }
            return new com.incall.apps.service.musicservice.IMusicService.Stub.Proxy(obj);
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
                    com.incall.apps.service.musicservice.MusicPlayerCallback _arg02 = com.incall.apps.service.musicservice.MusicPlayerCallback.Stub.asInterface(data.readStrongBinder());
                    setMusicPlayerCallback(_arg02);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg03 = data.readString();
                    play(_arg03);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    playOrPause();
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    next();
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    prev();
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    changeMode();
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = com.incall.apps.service.musicservice.MusicData.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    updateMusicData(_arg0);
                    reply.writeNoException();
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg04 = data.readString();
                    updateMusicList(_arg04);
                    reply.writeNoException();
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    com.incall.apps.service.musicservice.MusicListListener _arg05 = com.incall.apps.service.musicservice.MusicListListener.Stub.asInterface(data.readStrongBinder());
                    addMusicListListener(_arg05);
                    reply.writeNoException();
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    com.incall.apps.service.musicservice.MusicListListener _arg06 = com.incall.apps.service.musicservice.MusicListListener.Stub.asInterface(data.readStrongBinder());
                    removeMusicListListener(_arg06);
                    reply.writeNoException();
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    com.incall.apps.service.musicservice.MusicDataListener _arg07 = com.incall.apps.service.musicservice.MusicDataListener.Stub.asInterface(data.readStrongBinder());
                    addMusicDataListener(_arg07);
                    reply.writeNoException();
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    com.incall.apps.service.musicservice.MusicDataListener _arg08 = com.incall.apps.service.musicservice.MusicDataListener.Stub.asInterface(data.readStrongBinder());
                    removeMusicDataListener(_arg08);
                    reply.writeNoException();
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    com.incall.apps.service.musicservice.MusicListListener _arg09 = com.incall.apps.service.musicservice.MusicListListener.Stub.asInterface(data.readStrongBinder());
                    setMusicListListener(_arg09);
                    reply.writeNoException();
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    com.incall.apps.service.musicservice.MusicDataListener _arg010 = com.incall.apps.service.musicservice.MusicDataListener.Stub.asInterface(data.readStrongBinder());
                    setMusicDataListener(_arg010);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements com.incall.apps.service.musicservice.IMusicService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.incall.apps.service.musicservice.IMusicService.Stub.DESCRIPTOR;
            }

            @Override // com.incall.apps.service.musicservice.IMusicService
            public void setMusicPlayerCallback(com.incall.apps.service.musicservice.MusicPlayerCallback callback) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.musicservice.IMusicService.Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.musicservice.IMusicService
            public void play(java.lang.String path) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.musicservice.IMusicService.Stub.DESCRIPTOR);
                    _data.writeString(path);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.musicservice.IMusicService
            public void playOrPause() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.musicservice.IMusicService.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.musicservice.IMusicService
            public void next() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.musicservice.IMusicService.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.musicservice.IMusicService
            public void prev() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.musicservice.IMusicService.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.musicservice.IMusicService
            public void changeMode() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.musicservice.IMusicService.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.musicservice.IMusicService
            public void updateMusicData(com.incall.apps.service.musicservice.MusicData data) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.musicservice.IMusicService.Stub.DESCRIPTOR);
                    if (data != null) {
                        _data.writeInt(1);
                        data.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.musicservice.IMusicService
            public void updateMusicList(java.lang.String json) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.musicservice.IMusicService.Stub.DESCRIPTOR);
                    _data.writeString(json);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.musicservice.IMusicService
            public void addMusicListListener(com.incall.apps.service.musicservice.MusicListListener listener) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.musicservice.IMusicService.Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.musicservice.IMusicService
            public void removeMusicListListener(com.incall.apps.service.musicservice.MusicListListener listener) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.musicservice.IMusicService.Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.musicservice.IMusicService
            public void addMusicDataListener(com.incall.apps.service.musicservice.MusicDataListener listener) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.musicservice.IMusicService.Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.musicservice.IMusicService
            public void removeMusicDataListener(com.incall.apps.service.musicservice.MusicDataListener listener) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.musicservice.IMusicService.Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.musicservice.IMusicService
            public void setMusicListListener(com.incall.apps.service.musicservice.MusicListListener listener) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.musicservice.IMusicService.Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.musicservice.IMusicService
            public void setMusicDataListener(com.incall.apps.service.musicservice.MusicDataListener listener) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.musicservice.IMusicService.Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
