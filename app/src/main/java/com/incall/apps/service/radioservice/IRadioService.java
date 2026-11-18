package com.incall.apps.service.radioservice;

/* loaded from: classes.dex */
public interface IRadioService extends android.os.IInterface {
    boolean collectLocalRadio(int i, int i2) throws android.os.RemoteException;

    boolean collectLocalRadioList(java.lang.String str, int i) throws android.os.RemoteException;

    boolean collectXmlyPlayHistory(java.lang.String str) throws android.os.RemoteException;

    boolean collectXmlySearchHistory(java.lang.String str) throws android.os.RemoteException;

    boolean collectXmlySound(java.lang.String str) throws android.os.RemoteException;

    boolean collectXmlySoundList(java.lang.String str) throws android.os.RemoteException;

    boolean deleteAllXmlyPlayHistory() throws android.os.RemoteException;

    boolean deleteAllXmlySearchHistory() throws android.os.RemoteException;

    int deleteCollectLocalRadio(int i, int i2) throws android.os.RemoteException;

    int deleteCollectXmlySound(long j) throws android.os.RemoteException;

    int deleteXmlyPlayHistory(long j) throws android.os.RemoteException;

    boolean deleteXmlySearchHistory(java.lang.String str) throws android.os.RemoteException;

    java.lang.String getAllRadioCollectList(boolean z) throws android.os.RemoteException;

    java.lang.String getAllXmlyPlayHistory() throws android.os.RemoteException;

    java.lang.String getAllXmlySearchHistory() throws android.os.RemoteException;

    java.lang.String getLocalCollectBeanList(int i, boolean z) throws android.os.RemoteException;

    boolean getShowOnlineRadio() throws android.os.RemoteException;

    java.lang.String getXmlyCollectBeanList(boolean z) throws android.os.RemoteException;

    java.lang.String getcollectXmlySound(long j) throws android.os.RemoteException;

    boolean isCollectLocalRadio(int i, int i2) throws android.os.RemoteException;

    boolean isCollectXmlySound(long j) throws android.os.RemoteException;

    boolean isExistXmlyPlayHistory(long j) throws android.os.RemoteException;

    boolean isExistXmlySearchHistory(java.lang.String str) throws android.os.RemoteException;

    boolean updateCollectRadio(int i, int i2, int i3) throws android.os.RemoteException;

    boolean updateXmlySearchHistoryTime(java.lang.String str) throws android.os.RemoteException;

    public static abstract class Stub extends android.os.Binder implements com.incall.apps.service.radioservice.IRadioService {
        private static final java.lang.String DESCRIPTOR = "com.incall.apps.service.radioservice.IRadioService";
        static final int TRANSACTION_collectLocalRadio = 2;
        static final int TRANSACTION_collectLocalRadioList = 3;
        static final int TRANSACTION_collectXmlyPlayHistory = 13;
        static final int TRANSACTION_collectXmlySearchHistory = 19;
        static final int TRANSACTION_collectXmlySound = 8;
        static final int TRANSACTION_collectXmlySoundList = 24;
        static final int TRANSACTION_deleteAllXmlyPlayHistory = 15;
        static final int TRANSACTION_deleteAllXmlySearchHistory = 21;
        static final int TRANSACTION_deleteCollectLocalRadio = 4;
        static final int TRANSACTION_deleteCollectXmlySound = 10;
        static final int TRANSACTION_deleteXmlyPlayHistory = 14;
        static final int TRANSACTION_deleteXmlySearchHistory = 20;
        static final int TRANSACTION_getAllRadioCollectList = 25;
        static final int TRANSACTION_getAllXmlyPlayHistory = 16;
        static final int TRANSACTION_getAllXmlySearchHistory = 22;
        static final int TRANSACTION_getLocalCollectBeanList = 6;
        static final int TRANSACTION_getShowOnlineRadio = 1;
        static final int TRANSACTION_getXmlyCollectBeanList = 23;
        static final int TRANSACTION_getcollectXmlySound = 9;
        static final int TRANSACTION_isCollectLocalRadio = 5;
        static final int TRANSACTION_isCollectXmlySound = 11;
        static final int TRANSACTION_isExistXmlyPlayHistory = 12;
        static final int TRANSACTION_isExistXmlySearchHistory = 17;
        static final int TRANSACTION_updateCollectRadio = 7;
        static final int TRANSACTION_updateXmlySearchHistoryTime = 18;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.incall.apps.service.radioservice.IRadioService asInterface(android.os.IBinder obj) {
            if (obj == null) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof com.incall.apps.service.radioservice.IRadioService)) {
                return (com.incall.apps.service.radioservice.IRadioService) iin;
            }
            return new com.incall.apps.service.radioservice.IRadioService.Stub.Proxy(obj);
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
                    boolean _result = getShowOnlineRadio();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    boolean _result2 = collectLocalRadio(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeInt(_result2 ? 1 : 0);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg02 = data.readString();
                    int _arg12 = data.readInt();
                    boolean _result3 = collectLocalRadioList(_arg02, _arg12);
                    reply.writeNoException();
                    reply.writeInt(_result3 ? 1 : 0);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg03 = data.readInt();
                    int _arg13 = data.readInt();
                    int _result4 = deleteCollectLocalRadio(_arg03, _arg13);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg04 = data.readInt();
                    int _arg14 = data.readInt();
                    boolean _result5 = isCollectLocalRadio(_arg04, _arg14);
                    reply.writeNoException();
                    reply.writeInt(_result5 ? 1 : 0);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg05 = data.readInt();
                    boolean _arg15 = data.readInt() != 0;
                    java.lang.String _result6 = getLocalCollectBeanList(_arg05, _arg15);
                    reply.writeNoException();
                    reply.writeString(_result6);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg06 = data.readInt();
                    int _arg16 = data.readInt();
                    int _arg2 = data.readInt();
                    boolean _result7 = updateCollectRadio(_arg06, _arg16, _arg2);
                    reply.writeNoException();
                    reply.writeInt(_result7 ? 1 : 0);
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg07 = data.readString();
                    boolean _result8 = collectXmlySound(_arg07);
                    reply.writeNoException();
                    reply.writeInt(_result8 ? 1 : 0);
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    long _arg08 = data.readLong();
                    java.lang.String _result9 = getcollectXmlySound(_arg08);
                    reply.writeNoException();
                    reply.writeString(_result9);
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    long _arg09 = data.readLong();
                    int _result10 = deleteCollectXmlySound(_arg09);
                    reply.writeNoException();
                    reply.writeInt(_result10);
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    long _arg010 = data.readLong();
                    boolean _result11 = isCollectXmlySound(_arg010);
                    reply.writeNoException();
                    reply.writeInt(_result11 ? 1 : 0);
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    long _arg011 = data.readLong();
                    boolean _result12 = isExistXmlyPlayHistory(_arg011);
                    reply.writeNoException();
                    reply.writeInt(_result12 ? 1 : 0);
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg012 = data.readString();
                    boolean _result13 = collectXmlyPlayHistory(_arg012);
                    reply.writeNoException();
                    reply.writeInt(_result13 ? 1 : 0);
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    long _arg013 = data.readLong();
                    int _result14 = deleteXmlyPlayHistory(_arg013);
                    reply.writeNoException();
                    reply.writeInt(_result14);
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result15 = deleteAllXmlyPlayHistory();
                    reply.writeNoException();
                    reply.writeInt(_result15 ? 1 : 0);
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _result16 = getAllXmlyPlayHistory();
                    reply.writeNoException();
                    reply.writeString(_result16);
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg014 = data.readString();
                    boolean _result17 = isExistXmlySearchHistory(_arg014);
                    reply.writeNoException();
                    reply.writeInt(_result17 ? 1 : 0);
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg015 = data.readString();
                    boolean _result18 = updateXmlySearchHistoryTime(_arg015);
                    reply.writeNoException();
                    reply.writeInt(_result18 ? 1 : 0);
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg016 = data.readString();
                    boolean _result19 = collectXmlySearchHistory(_arg016);
                    reply.writeNoException();
                    reply.writeInt(_result19 ? 1 : 0);
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg017 = data.readString();
                    boolean _result20 = deleteXmlySearchHistory(_arg017);
                    reply.writeNoException();
                    reply.writeInt(_result20 ? 1 : 0);
                    return true;
                case 21:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result21 = deleteAllXmlySearchHistory();
                    reply.writeNoException();
                    reply.writeInt(_result21 ? 1 : 0);
                    return true;
                case 22:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _result22 = getAllXmlySearchHistory();
                    reply.writeNoException();
                    reply.writeString(_result22);
                    return true;
                case 23:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg018 = data.readInt() != 0;
                    java.lang.String _result23 = getXmlyCollectBeanList(_arg018);
                    reply.writeNoException();
                    reply.writeString(_result23);
                    return true;
                case 24:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg019 = data.readString();
                    boolean _result24 = collectXmlySoundList(_arg019);
                    reply.writeNoException();
                    reply.writeInt(_result24 ? 1 : 0);
                    return true;
                case 25:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _arg020 = data.readInt() != 0;
                    java.lang.String _result25 = getAllRadioCollectList(_arg020);
                    reply.writeNoException();
                    reply.writeString(_result25);
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements com.incall.apps.service.radioservice.IRadioService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR;
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public boolean getShowOnlineRadio() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public boolean collectLocalRadio(int frequent, int type) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    _data.writeInt(frequent);
                    _data.writeInt(type);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public boolean collectLocalRadioList(java.lang.String radioString, int type) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    _data.writeString(radioString);
                    _data.writeInt(type);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public int deleteCollectLocalRadio(int frequent, int radioType) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    _data.writeInt(frequent);
                    _data.writeInt(radioType);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public boolean isCollectLocalRadio(int frequent, int radioType) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    _data.writeInt(frequent);
                    _data.writeInt(radioType);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public java.lang.String getLocalCollectBeanList(int type, boolean isAsc) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(isAsc ? 1 : 0);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    java.lang.String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public boolean updateCollectRadio(int fromFrequency, int toFrequency, int radioType) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    _data.writeInt(fromFrequency);
                    _data.writeInt(toFrequency);
                    _data.writeInt(radioType);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public boolean collectXmlySound(java.lang.String soundString) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    _data.writeString(soundString);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public java.lang.String getcollectXmlySound(long dataId) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    _data.writeLong(dataId);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    java.lang.String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public int deleteCollectXmlySound(long id) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    _data.writeLong(id);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public boolean isCollectXmlySound(long id) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    _data.writeLong(id);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public boolean isExistXmlyPlayHistory(long id) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    _data.writeLong(id);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public boolean collectXmlyPlayHistory(java.lang.String beanString) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    _data.writeString(beanString);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public int deleteXmlyPlayHistory(long id) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    _data.writeLong(id);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public boolean deleteAllXmlyPlayHistory() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public java.lang.String getAllXmlyPlayHistory() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    java.lang.String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public boolean isExistXmlySearchHistory(java.lang.String searString) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    _data.writeString(searString);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public boolean updateXmlySearchHistoryTime(java.lang.String searString) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    _data.writeString(searString);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public boolean collectXmlySearchHistory(java.lang.String searString) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    _data.writeString(searString);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public boolean deleteXmlySearchHistory(java.lang.String searString) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    _data.writeString(searString);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public boolean deleteAllXmlySearchHistory() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public java.lang.String getAllXmlySearchHistory() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    java.lang.String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public java.lang.String getXmlyCollectBeanList(boolean isAsc) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    _data.writeInt(isAsc ? 1 : 0);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    java.lang.String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public boolean collectXmlySoundList(java.lang.String soundListString) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    _data.writeString(soundListString);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.radioservice.IRadioService
            public java.lang.String getAllRadioCollectList(boolean isAsc) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.radioservice.IRadioService.Stub.DESCRIPTOR);
                    _data.writeInt(isAsc ? 1 : 0);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    java.lang.String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
