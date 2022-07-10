package com.example.football.ui.profile;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.football.Connection;
import com.example.football.R;
import com.example.football.SharedPrefLS;

import java.io.InputStream;
import java.net.URL;

public class ProfileFragment extends Fragment {
    private Bitmap bitmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String url = Connection.getUrl();
            System.out.println(url);
            bitmap = BitmapFactory.decodeStream((InputStream)new URL(Connection.imagesUrl + url).getContent());
        } catch (Exception ex) {
            bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.real_logo);
            ex.printStackTrace();
        }
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
        TextView name = view.findViewById(R.id.nameProf);
        TextView pos = view.findViewById(R.id.posProf);
        TextView foot = view.findViewById(R.id.footProf);
        TextView team = view.findViewById(R.id.teamProf);
        TextView lastPay = view.findViewById(R.id.lastPayProf);
        TextView abon = view.findViewById(R.id.abonProf);
        TextView age = view.findViewById(R.id.ageProf);
        Button exit = view.findViewById(R.id.exitProf);

        exit.setOnClickListener(e-> {
            getActivity().onBackPressed();
            SharedPrefLS.SaveAut(getActivity(), "","");
        });

        try{
            image.setImageBitmap(bitmap);
        } catch (Exception ex) {
            ex.printStackTrace();
            image.setImageResource(R.drawable.ic_profile);
        }
        try {
            name.setText(Connection.getName());
            pos.setText(Connection.getPosition());
            foot.setText(Connection.getFoot());
            team.setText(Connection.getTeam());
            lastPay.setText(Connection.getLastPay());
            abon.setText(Connection.getAbonement());
            age.setText(Connection.getAge());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("-------------------------------------------PROFILE_CREATED_IN_"+((System.currentTimeMillis() - startTimer*1.0)/1000) + "_SECONDS-------------------------------------");
    }
}