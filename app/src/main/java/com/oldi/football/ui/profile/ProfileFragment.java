package com.oldi.football.ui.profile;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.oldi.football.Connection;
import com.oldi.football.R;
import com.oldi.football.SharedPrefLS;

import java.io.InputStream;
import java.net.URL;

public class ProfileFragment extends Fragment {
    private Bitmap bitmap;


    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        long startTimer = System.currentTimeMillis();

        ImageView image = view.findViewById(R.id.imageProf);
        TextView text;

        try{
            String url = Connection.getProfileUrl();
            System.out.println(url);
            bitmap = BitmapFactory.decodeStream((InputStream)new URL(Connection.imagesUrl + url).getContent());
        } catch (Exception ex) {
            bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.red_logo);
            ex.printStackTrace();
        }
        try{
            image.setImageBitmap(bitmap);
        } catch (Exception ex) {
            ex.printStackTrace();
            image.setImageResource(R.drawable.ic_profile);
        }
        try {
            text = view.findViewById(R.id.nameProf);
            text.setText(Connection.getName());
            text = view.findViewById(R.id.posProf);
            text.setText(Connection.getPosition());
            text = view.findViewById(R.id.footProf);
            text.setText(Connection.getFoot());
            text = view.findViewById(R.id.teamProf);
            text.setText(Connection.getTeam());
            text =  view.findViewById(R.id.lastPayProf);
            text.setText(Connection.getLastPay());
            text = view.findViewById(R.id.abonProf);
            text.setText(Connection.getAbonement());
            text = view.findViewById(R.id.ageProf);
            text.setText(Connection.getAge());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("-------------------------------------------PROFILE_CREATED_IN_"+((System.currentTimeMillis() - startTimer*1.0)/1000) + "_SECONDS-------------------------------------");
    }

    @Override
    public void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}