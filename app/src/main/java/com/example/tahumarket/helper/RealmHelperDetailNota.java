package com.example.tahumarket.helper;

import android.util.Log;
import com.example.tahumarket.model.NotaModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelperDetailNota {

    Realm realm;

    public RealmHelperDetailNota(Realm realm){
        this.realm = realm;
    }

    // untuk menyimpan data
    public void savedetail(final NotaModel detailNotaModel){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null){
                    Log.e("Created", "Database was created");
                    Number currentIdNum = realm.where(NotaModel.class).max("iddata");
                    int nextId;
                    if (currentIdNum == null){
                        nextId = 1;
                    }else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    detailNotaModel.setIddata(nextId);
                    NotaModel model = realm.copyToRealmOrUpdate(detailNotaModel);
                }else{
                    Log.e("ppppp", "execute: Database not Exist");
                }
            }
        });
    }

    // untuk memanggil semua data
    public List<NotaModel> getAllDetailNota(){
        RealmResults<NotaModel> results = realm.where(NotaModel.class).findAll();
        return results;
    }

    // untuk memanggil data Detail Nota byidnota
    public List<NotaModel> getAllDetailNotaById(String kodenota){
        RealmResults<NotaModel> results = realm.where(NotaModel.class).equalTo("kodeNota",kodenota).findAll();
        return results;
    }

    public void deleteDetail(String id){
        final RealmResults<NotaModel> model = realm.where(NotaModel.class).equalTo("kodeNota", id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                model.deleteFromRealm(0);
            }
        });
    }
}
