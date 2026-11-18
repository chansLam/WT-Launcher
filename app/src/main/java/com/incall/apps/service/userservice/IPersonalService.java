package com.incall.apps.service.userservice;

/* loaded from: classes.dex */
public interface IPersonalService extends android.os.IInterface {
    void addAccountChangelistener(com.incall.apps.service.userservice.AccountChangeListener accountChangeListener) throws android.os.RemoteException;

    boolean cancelCollectRadio(int i, int i2) throws android.os.RemoteException;

    boolean cancelCollectRadios(java.util.List<com.incall.apps.service.userservice.beans.RadioCollect> list) throws android.os.RemoteException;

    boolean collectRadio(com.incall.apps.service.userservice.beans.RadioCollect radioCollect) throws android.os.RemoteException;

    boolean collectRadios(java.util.List<com.incall.apps.service.userservice.beans.RadioCollect> list) throws android.os.RemoteException;

    boolean deleteOtherAppInfo(int i, java.lang.String str) throws android.os.RemoteException;

    boolean deleteRadioCollects(int i) throws android.os.RemoteException;

    boolean deleteUser(java.lang.String str) throws android.os.RemoteException;

    java.util.Map getAllOtherAppInfo(int i) throws android.os.RemoteException;

    java.util.List<com.incall.apps.service.userservice.beans.User> getAllUser() throws android.os.RemoteException;

    int getCollectSizeByType(int i) throws android.os.RemoteException;

    java.lang.String getOtherAppInfo(int i, java.lang.String str) throws android.os.RemoteException;

    java.util.List<com.incall.apps.service.userservice.beans.RadioCollect> getRadioCollectList(int i, boolean z) throws android.os.RemoteException;

    com.incall.apps.service.userservice.beans.Seats getSeatAdjustment(java.lang.String str) throws android.os.RemoteException;

    java.lang.String getTuid() throws android.os.RemoteException;

    com.incall.apps.service.userservice.beans.UserLogin getUserLogin() throws android.os.RemoteException;

    java.lang.String getUuid() throws android.os.RemoteException;

    boolean isCarRunning() throws android.os.RemoteException;

    boolean isOnline() throws android.os.RemoteException;

    boolean isRadioCollected(int i, int i2) throws android.os.RemoteException;

    com.incall.apps.service.userservice.beans.Apps queryAppsInfo(java.lang.String str) throws android.os.RemoteException;

    com.incall.apps.service.userservice.beans.Keys queryKeysInfo(java.lang.String str) throws android.os.RemoteException;

    com.incall.apps.service.userservice.beans.User queryUserInfo(java.lang.String str) throws android.os.RemoteException;

    void removeAccountChangelistener(com.incall.apps.service.userservice.AccountChangeListener accountChangeListener) throws android.os.RemoteException;

    boolean saveAppsInfo(com.incall.apps.service.userservice.beans.Apps apps) throws android.os.RemoteException;

    boolean saveOtherAppInfo(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean saveSeatAdjustment(com.incall.apps.service.userservice.beans.Seats seats) throws android.os.RemoteException;

    boolean saveUserInfo(com.incall.apps.service.userservice.beans.User user) throws android.os.RemoteException;

    boolean savekeysInfo(com.incall.apps.service.userservice.beans.Keys keys) throws android.os.RemoteException;

    boolean setUserLogin(com.incall.apps.service.userservice.beans.UserLogin userLogin) throws android.os.RemoteException;

    void setUserState(int i) throws android.os.RemoteException;

    void uploadbehavior(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    public static abstract class Stub extends android.os.Binder implements com.incall.apps.service.userservice.IPersonalService {
        private static final java.lang.String DESCRIPTOR = "com.incall.apps.service.userservice.IPersonalService";
        static final int TRANSACTION_addAccountChangelistener = 4;
        static final int TRANSACTION_cancelCollectRadio = 24;
        static final int TRANSACTION_cancelCollectRadios = 28;
        static final int TRANSACTION_collectRadio = 23;
        static final int TRANSACTION_collectRadios = 27;
        static final int TRANSACTION_deleteOtherAppInfo = 16;
        static final int TRANSACTION_deleteRadioCollects = 30;
        static final int TRANSACTION_deleteUser = 9;
        static final int TRANSACTION_getAllOtherAppInfo = 17;
        static final int TRANSACTION_getAllUser = 8;
        static final int TRANSACTION_getCollectSizeByType = 29;
        static final int TRANSACTION_getOtherAppInfo = 15;
        static final int TRANSACTION_getRadioCollectList = 26;
        static final int TRANSACTION_getSeatAdjustment = 19;
        static final int TRANSACTION_getTuid = 31;
        static final int TRANSACTION_getUserLogin = 2;
        static final int TRANSACTION_getUuid = 32;
        static final int TRANSACTION_isCarRunning = 20;
        static final int TRANSACTION_isOnline = 21;
        static final int TRANSACTION_isRadioCollected = 25;
        static final int TRANSACTION_queryAppsInfo = 13;
        static final int TRANSACTION_queryKeysInfo = 11;
        static final int TRANSACTION_queryUserInfo = 7;
        static final int TRANSACTION_removeAccountChangelistener = 5;
        static final int TRANSACTION_saveAppsInfo = 12;
        static final int TRANSACTION_saveOtherAppInfo = 14;
        static final int TRANSACTION_saveSeatAdjustment = 18;
        static final int TRANSACTION_saveUserInfo = 6;
        static final int TRANSACTION_savekeysInfo = 10;
        static final int TRANSACTION_setUserLogin = 1;
        static final int TRANSACTION_setUserState = 3;
        static final int TRANSACTION_uploadbehavior = 22;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.incall.apps.service.userservice.IPersonalService asInterface(android.os.IBinder obj) {
            if (obj == null) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof com.incall.apps.service.userservice.IPersonalService)) {
                return (com.incall.apps.service.userservice.IPersonalService) iin;
            }
            return new com.incall.apps.service.userservice.IPersonalService.Stub.Proxy(obj);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
            com.incall.apps.service.userservice.beans.RadioCollect _arg0;
            com.incall.apps.service.userservice.beans.Seats _arg02;
            com.incall.apps.service.userservice.beans.Apps _arg03;
            com.incall.apps.service.userservice.beans.Keys _arg04;
            com.incall.apps.service.userservice.beans.User _arg05;
            com.incall.apps.service.userservice.beans.UserLogin _arg06;
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg06 = com.incall.apps.service.userservice.beans.UserLogin.CREATOR.createFromParcel(data);
                    } else {
                        _arg06 = null;
                    }
                    boolean _result = setUserLogin(_arg06);
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    com.incall.apps.service.userservice.beans.UserLogin _result2 = getUserLogin();
                    reply.writeNoException();
                    if (_result2 != null) {
                        reply.writeInt(1);
                        _result2.writeToParcel(reply, 1);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg07 = data.readInt();
                    setUserState(_arg07);
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    com.incall.apps.service.userservice.AccountChangeListener _arg08 = com.incall.apps.service.userservice.AccountChangeListener.Stub.asInterface(data.readStrongBinder());
                    addAccountChangelistener(_arg08);
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    com.incall.apps.service.userservice.AccountChangeListener _arg09 = com.incall.apps.service.userservice.AccountChangeListener.Stub.asInterface(data.readStrongBinder());
                    removeAccountChangelistener(_arg09);
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg05 = com.incall.apps.service.userservice.beans.User.CREATOR.createFromParcel(data);
                    } else {
                        _arg05 = null;
                    }
                    boolean _result3 = saveUserInfo(_arg05);
                    reply.writeNoException();
                    reply.writeInt(_result3 ? 1 : 0);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg010 = data.readString();
                    com.incall.apps.service.userservice.beans.User _result4 = queryUserInfo(_arg010);
                    reply.writeNoException();
                    if (_result4 != null) {
                        reply.writeInt(1);
                        _result4.writeToParcel(reply, 1);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    java.util.List<com.incall.apps.service.userservice.beans.User> _result5 = getAllUser();
                    reply.writeNoException();
                    reply.writeTypedList(_result5);
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg011 = data.readString();
                    boolean _result6 = deleteUser(_arg011);
                    reply.writeNoException();
                    reply.writeInt(_result6 ? 1 : 0);
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg04 = com.incall.apps.service.userservice.beans.Keys.CREATOR.createFromParcel(data);
                    } else {
                        _arg04 = null;
                    }
                    boolean _result7 = savekeysInfo(_arg04);
                    reply.writeNoException();
                    reply.writeInt(_result7 ? 1 : 0);
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg012 = data.readString();
                    com.incall.apps.service.userservice.beans.Keys _result8 = queryKeysInfo(_arg012);
                    reply.writeNoException();
                    if (_result8 != null) {
                        reply.writeInt(1);
                        _result8.writeToParcel(reply, 1);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg03 = com.incall.apps.service.userservice.beans.Apps.CREATOR.createFromParcel(data);
                    } else {
                        _arg03 = null;
                    }
                    boolean _result9 = saveAppsInfo(_arg03);
                    reply.writeNoException();
                    reply.writeInt(_result9 ? 1 : 0);
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg013 = data.readString();
                    com.incall.apps.service.userservice.beans.Apps _result10 = queryAppsInfo(_arg013);
                    reply.writeNoException();
                    if (_result10 != null) {
                        reply.writeInt(1);
                        _result10.writeToParcel(reply, 1);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg014 = data.readInt();
                    java.lang.String _arg1 = data.readString();
                    java.lang.String _arg2 = data.readString();
                    boolean _result11 = saveOtherAppInfo(_arg014, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeInt(_result11 ? 1 : 0);
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg015 = data.readInt();
                    java.lang.String _arg12 = data.readString();
                    java.lang.String _result12 = getOtherAppInfo(_arg015, _arg12);
                    reply.writeNoException();
                    reply.writeString(_result12);
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg016 = data.readInt();
                    java.lang.String _arg13 = data.readString();
                    boolean _result13 = deleteOtherAppInfo(_arg016, _arg13);
                    reply.writeNoException();
                    reply.writeInt(_result13 ? 1 : 0);
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg017 = data.readInt();
                    java.util.Map _result14 = getAllOtherAppInfo(_arg017);
                    reply.writeNoException();
                    reply.writeMap(_result14);
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg02 = com.incall.apps.service.userservice.beans.Seats.CREATOR.createFromParcel(data);
                    } else {
                        _arg02 = null;
                    }
                    boolean _result15 = saveSeatAdjustment(_arg02);
                    reply.writeNoException();
                    reply.writeInt(_result15 ? 1 : 0);
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg018 = data.readString();
                    com.incall.apps.service.userservice.beans.Seats _result16 = getSeatAdjustment(_arg018);
                    reply.writeNoException();
                    if (_result16 != null) {
                        reply.writeInt(1);
                        _result16.writeToParcel(reply, 1);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result17 = isCarRunning();
                    reply.writeNoException();
                    reply.writeInt(_result17 ? 1 : 0);
                    return true;
                case 21:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result18 = isOnline();
                    reply.writeNoException();
                    reply.writeInt(_result18 ? 1 : 0);
                    return true;
                case 22:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg019 = data.readString();
                    java.lang.String _arg14 = data.readString();
                    uploadbehavior(_arg019, _arg14);
                    reply.writeNoException();
                    return true;
                case 23:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = com.incall.apps.service.userservice.beans.RadioCollect.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    boolean _result19 = collectRadio(_arg0);
                    reply.writeNoException();
                    reply.writeInt(_result19 ? 1 : 0);
                    return true;
                case 24:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg020 = data.readInt();
                    int _arg15 = data.readInt();
                    boolean _result20 = cancelCollectRadio(_arg020, _arg15);
                    reply.writeNoException();
                    reply.writeInt(_result20 ? 1 : 0);
                    return true;
                case 25:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg021 = data.readInt();
                    int _arg16 = data.readInt();
                    boolean _result21 = isRadioCollected(_arg021, _arg16);
                    reply.writeNoException();
                    reply.writeInt(_result21 ? 1 : 0);
                    return true;
                case 26:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg022 = data.readInt();
                    boolean _arg17 = data.readInt() != 0;
                    java.util.List<com.incall.apps.service.userservice.beans.RadioCollect> _result22 = getRadioCollectList(_arg022, _arg17);
                    reply.writeNoException();
                    reply.writeTypedList(_result22);
                    return true;
                case 27:
                    data.enforceInterface(DESCRIPTOR);
                    java.util.List<com.incall.apps.service.userservice.beans.RadioCollect> _arg023 = data.createTypedArrayList(com.incall.apps.service.userservice.beans.RadioCollect.CREATOR);
                    boolean _result23 = collectRadios(_arg023);
                    reply.writeNoException();
                    reply.writeInt(_result23 ? 1 : 0);
                    return true;
                case 28:
                    data.enforceInterface(DESCRIPTOR);
                    java.util.List<com.incall.apps.service.userservice.beans.RadioCollect> _arg024 = data.createTypedArrayList(com.incall.apps.service.userservice.beans.RadioCollect.CREATOR);
                    boolean _result24 = cancelCollectRadios(_arg024);
                    reply.writeNoException();
                    reply.writeInt(_result24 ? 1 : 0);
                    return true;
                case 29:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg025 = data.readInt();
                    int _result25 = getCollectSizeByType(_arg025);
                    reply.writeNoException();
                    reply.writeInt(_result25);
                    return true;
                case 30:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg026 = data.readInt();
                    boolean _result26 = deleteRadioCollects(_arg026);
                    reply.writeNoException();
                    reply.writeInt(_result26 ? 1 : 0);
                    return true;
                case 31:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _result27 = getTuid();
                    reply.writeNoException();
                    reply.writeString(_result27);
                    return true;
                case 32:
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _result28 = getUuid();
                    reply.writeNoException();
                    reply.writeString(_result28);
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements com.incall.apps.service.userservice.IPersonalService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR;
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public boolean setUserLogin(com.incall.apps.service.userservice.beans.UserLogin loginUser) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    if (loginUser != null) {
                        _data.writeInt(1);
                        loginUser.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public com.incall.apps.service.userservice.beans.UserLogin getUserLogin() throws android.os.RemoteException {
                com.incall.apps.service.userservice.beans.UserLogin _result;
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = com.incall.apps.service.userservice.beans.UserLogin.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public void setUserState(int mode) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public void addAccountChangelistener(com.incall.apps.service.userservice.AccountChangeListener callback) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public void removeAccountChangelistener(com.incall.apps.service.userservice.AccountChangeListener callback) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public boolean saveUserInfo(com.incall.apps.service.userservice.beans.User user) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public com.incall.apps.service.userservice.beans.User queryUserInfo(java.lang.String userId) throws android.os.RemoteException {
                com.incall.apps.service.userservice.beans.User _result;
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    _data.writeString(userId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = com.incall.apps.service.userservice.beans.User.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public java.util.List<com.incall.apps.service.userservice.beans.User> getAllUser() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    java.util.List<com.incall.apps.service.userservice.beans.User> _result = _reply.createTypedArrayList(com.incall.apps.service.userservice.beans.User.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public boolean deleteUser(java.lang.String userId) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    _data.writeString(userId);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public boolean savekeysInfo(com.incall.apps.service.userservice.beans.Keys key) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    if (key != null) {
                        _data.writeInt(1);
                        key.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public com.incall.apps.service.userservice.beans.Keys queryKeysInfo(java.lang.String userId) throws android.os.RemoteException {
                com.incall.apps.service.userservice.beans.Keys _result;
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    _data.writeString(userId);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = com.incall.apps.service.userservice.beans.Keys.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public boolean saveAppsInfo(com.incall.apps.service.userservice.beans.Apps app) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    if (app != null) {
                        _data.writeInt(1);
                        app.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public com.incall.apps.service.userservice.beans.Apps queryAppsInfo(java.lang.String userId) throws android.os.RemoteException {
                com.incall.apps.service.userservice.beans.Apps _result;
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    _data.writeString(userId);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = com.incall.apps.service.userservice.beans.Apps.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public boolean saveOtherAppInfo(int mode, java.lang.String key, java.lang.String value) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    _data.writeString(key);
                    _data.writeString(value);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public java.lang.String getOtherAppInfo(int mode, java.lang.String key) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    _data.writeString(key);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    java.lang.String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public boolean deleteOtherAppInfo(int mode, java.lang.String key) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    _data.writeString(key);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public java.util.Map getAllOtherAppInfo(int mode) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    java.lang.ClassLoader cl = getClass().getClassLoader();
                    java.util.Map _result = _reply.readHashMap(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public boolean saveSeatAdjustment(com.incall.apps.service.userservice.beans.Seats seat) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    if (seat != null) {
                        _data.writeInt(1);
                        seat.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public com.incall.apps.service.userservice.beans.Seats getSeatAdjustment(java.lang.String userId) throws android.os.RemoteException {
                com.incall.apps.service.userservice.beans.Seats _result;
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    _data.writeString(userId);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = com.incall.apps.service.userservice.beans.Seats.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public boolean isCarRunning() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public boolean isOnline() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public void uploadbehavior(java.lang.String dtOption, java.lang.String dataStr) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    _data.writeString(dtOption);
                    _data.writeString(dataStr);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public boolean collectRadio(com.incall.apps.service.userservice.beans.RadioCollect radio) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    if (radio != null) {
                        _data.writeInt(1);
                        radio.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public boolean cancelCollectRadio(int frequency, int type) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    _data.writeInt(frequency);
                    _data.writeInt(type);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public boolean isRadioCollected(int frequency, int type) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    _data.writeInt(frequency);
                    _data.writeInt(type);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public java.util.List<com.incall.apps.service.userservice.beans.RadioCollect> getRadioCollectList(int type, boolean isAsc) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(isAsc ? 1 : 0);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    java.util.List<com.incall.apps.service.userservice.beans.RadioCollect> _result = _reply.createTypedArrayList(com.incall.apps.service.userservice.beans.RadioCollect.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public boolean collectRadios(java.util.List<com.incall.apps.service.userservice.beans.RadioCollect> radios) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    _data.writeTypedList(radios);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public boolean cancelCollectRadios(java.util.List<com.incall.apps.service.userservice.beans.RadioCollect> radios) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    _data.writeTypedList(radios);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public int getCollectSizeByType(int type) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public boolean deleteRadioCollects(int type) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public java.lang.String getTuid() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                    java.lang.String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.incall.apps.service.userservice.IPersonalService
            public java.lang.String getUuid() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(com.incall.apps.service.userservice.IPersonalService.Stub.DESCRIPTOR);
                    this.mRemote.transact(32, _data, _reply, 0);
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
