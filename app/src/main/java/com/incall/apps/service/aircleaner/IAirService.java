package com.incall.apps.service.aircleaner;

/* loaded from: classes.dex */
public interface IAirService extends android.os.IInterface {
    int getInLevel() throws android.os.RemoteException;

    int getInPM() throws android.os.RemoteException;

    int getOutLevel() throws android.os.RemoteException;

    int getOutPM() throws android.os.RemoteException;

    int getRecycleType() throws android.os.RemoteException;

    boolean isPlasmaOn() throws android.os.RemoteException;

    boolean isPowerOn() throws android.os.RemoteException;

    boolean isWindOn() throws android.os.RemoteException;

    void removeCallback() throws android.os.RemoteException;

    void setCallback(com.incall.apps.service.aircleaner.IDataChange iDataChange) throws android.os.RemoteException;

    void setIonizerEnable(boolean z, boolean z2) throws android.os.RemoteException;

    public static abstract class Stub extends android.os.Binder implements com.incall.apps.service.aircleaner.IAirService {
        private static final java.lang.String DESCRIPTOR = "com.incall.apps.service.aircleaner.IAirService";
        static final int TRANSACTION_getInLevel = 3;
        static final int TRANSACTION_getInPM = 5;
        static final int TRANSACTION_getOutLevel = 4;
        static final int TRANSACTION_getOutPM = 6;
        static final int TRANSACTION_getRecycleType = 8;
        static final int TRANSACTION_isPlasmaOn = 7;
        static final int TRANSACTION_isPowerOn = 11;
        static final int TRANSACTION_isWindOn = 9;
        static final int TRANSACTION_removeCallback = 2;
        static final int TRANSACTION_setCallback = 1;
        static final int TRANSACTION_setIonizerEnable = 10;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.incall.apps.service.aircleaner.IAirService asInterface(android.os.IBinder obj) {
            if (obj == null) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof com.incall.apps.service.aircleaner.IAirService)) {
                return (com.incall.apps.service.aircleaner.IAirService) iin;
            }
            return new com.incall.apps.service.aircleaner.IAirService.Stub.Proxy(obj);
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
                    com.incall.apps.service.aircleaner.IDataChange _arg0 = com.incall.apps.service.aircleaner.IDataChange.Stub.asInterface(data.readStrongBinder());
                    setCallback(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    removeCallback();
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _result = getInLevel();
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    int _result2 = getOutLevel();
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    int _result3 = getInPM();
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    int _result4 = getOutPM();
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result5 = isPlasmaOn();
                    reply.writeNoException();
                    reply.writeInt(_result5 ? 1 : 0);
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    int _result6 = getRecycleType();
                    reply.writeNoException();
                    reply.writeInt(_result6);
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result7 = isWindOn();
                    reply.writeNoException();
                    reply.writeInt(_result7 ? 1 : 0);
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg02 = data.readInt() != 0;
                    boolean _arg1 = data.readInt() != 0;
                    setIonizerEnable(_arg02, _arg1);
                    reply.writeNoException();
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result8 = isPowerOn();
                    reply.writeNoException();
                    reply.writeInt(_result8 ? 1 : 0);
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements com.incall.apps.service.aircleaner.IAirService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.incall.apps.service.aircleaner.IAirService.Stub.DESCRIPTOR;
            }

            @Override // com.incall.apps.service.aircleaner.IAirService
            public void setCallback(com.incall.apps.service.aircleaner.IDataChange callback) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.aircleaner.IAirService.Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.aircleaner.IAirService
            public void removeCallback() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.aircleaner.IAirService.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.aircleaner.IAirService
            public int getInLevel() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.aircleaner.IAirService.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.aircleaner.IAirService
            public int getOutLevel() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.aircleaner.IAirService.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.aircleaner.IAirService
            public int getInPM() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.aircleaner.IAirService.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.aircleaner.IAirService
            public int getOutPM() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.aircleaner.IAirService.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.aircleaner.IAirService
            public boolean isPlasmaOn() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.aircleaner.IAirService.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.aircleaner.IAirService
            public int getRecycleType() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.aircleaner.IAirService.Stub.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.aircleaner.IAirService
            public boolean isWindOn() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.aircleaner.IAirService.Stub.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.aircleaner.IAirService
            public void setIonizerEnable(boolean enable, boolean playVoice) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.aircleaner.IAirService.Stub.DESCRIPTOR);
                    _data.writeInt(enable ? 1 : 0);
                    _data.writeInt(playVoice ? 1 : 0);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.aircleaner.IAirService
            public boolean isPowerOn() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.aircleaner.IAirService.Stub.DESCRIPTOR);
                    this.mRemote.transact(11, _data, _reply, 0);
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
