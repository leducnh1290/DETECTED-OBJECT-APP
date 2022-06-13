package com.leducanh.main.DataBase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataBase {
   public static DatabaseReference myRef;

    public DataBase(FirebaseDatabase database,String data_index) {
        this.myRef = database.getReference(data_index);
    }
}
