package com.leducanh.main.DetectedObject.fragmentmenu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.L;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leducanh.main.DetectedObject.R;
public class Main extends Fragment {
    DatabaseReference MyRef;
View LAYOUT_MAIN_VIEW;
TextView tv;
public Main(DatabaseReference MyRef){
    this.MyRef = MyRef;
}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LAYOUT_MAIN_VIEW = inflater.inflate(R.layout.fragment_main,container,false);
        tv = LAYOUT_MAIN_VIEW.findViewById(R.id.detected);
        MyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.getValue().toString().isEmpty()) {
                    tv.setText(snapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });;
        return LAYOUT_MAIN_VIEW;
    }
}