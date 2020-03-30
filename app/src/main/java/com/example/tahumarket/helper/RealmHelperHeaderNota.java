package com.example.tahumarket.helper;

import android.util.Log;

import com.example.tahumarket.model.HeaderNotaModel;
import com.example.tahumarket.model.NotaModel;

import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelperHeaderNota {

    Realm realm;

    public RealmHelperHeaderNota(Realm realm){
        this.realm = realm;
    }

    // untuk menyimpan data
    public void saveheader(final HeaderNotaModel headerNotaModel){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null){
                    Log.e("Created", "Database was created");
                    Number currentIdNum = realm.where(HeaderNotaModel.class).max("iddata");
                    int nextId;
                    if (currentIdNum == null){
                        nextId = 1;
                    }else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    headerNotaModel.setIddata(nextId);
                    HeaderNotaModel model = realm.copyToRealmOrUpdate(headerNotaModel);
                }else{
                    Log.e("ppppp", "execute: Database not Exist");
                }
            }
        });
    }

    // untuk memanggil semua data
    public List<HeaderNotaModel> getAllHeader(){
        RealmResults<HeaderNotaModel> results = realm.where(HeaderNotaModel.class).findAll();
        return results;
    }

    // untuk memanggil data by TransDate
    public List<HeaderNotaModel> getHeaderNotaByDate(String transDate){
        RealmResults<HeaderNotaModel> results = realm.where(HeaderNotaModel.class).equalTo("transdate",transDate).findAll();
        return results;
    }

    //untuk menghapus nota by noNota
    public void deleteHeader(String id){
        final RealmResults<HeaderNotaModel> model = realm.where(HeaderNotaModel.class).equalTo("noNota", id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                model.deleteFromRealm(0);
            }
        });
    }

}
