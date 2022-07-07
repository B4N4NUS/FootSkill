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
import com.example.football.MainActivity;
import com.example.football.R;
import com.example.football.Saver;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class ProfileFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ImageView image = getView().findViewById(R.id.imageProf);
        TextView name = getView().findViewById(R.id.nameProf);
        TextView pos = getView().findViewById(R.id.posProf);
        TextView foot = getView().findViewById(R.id.footProf);
        TextView team = getView().findViewById(R.id.teamProf);
        TextView lastPay = getView().findViewById(R.id.lastPayProf);
        TextView abon = getView().findViewById(R.id.abonProf);
        TextView age = getView().findViewById(R.id.ageProf);
        Button exit = getView().findViewById(R.id.exitProf);

        exit.setOnClickListener(e-> {
            getActivity().onBackPressed();
            Saver.SaveAut(getActivity(), "","");
            //getActivity().setContentView(R.layout.activity_main);
        });

        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //System.out.println(MainActivity.rawUser.getJSONObject("avatar").getString("url"));
            String url = "";
            if (MainActivity.rawUser == null) {
                throw new Exception("No server connection presented");
            }
            JSONObject array = MainActivity.rawUser.getJSONObject("avatar");
            url = array.getString("url");
            System.out.println(url);
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(Connection.imagesUrl + url).getContent());
            image.setImageBitmap(bitmap);
        } catch (Exception ex) {
            ex.printStackTrace();
            image.setImageResource(R.drawable.ic_profile);
        }
        try {
            name.setText(MainActivity.rawUser.getString("firstname") + " " + MainActivity.rawUser.getString("lastname"));
            pos.setText(MainActivity.rawUser.getString("playerPosition"));
            foot.setText(MainActivity.rawUser.getString("lead_leg"));
            team.setText(MainActivity.rawUser.getString("team"));
            lastPay.setText(MainActivity.rawUser.getString("date_of_last_pay"));
            abon.setText(MainActivity.rawUser.getString("variant_of_subscription"));
            String[] rawAge = MainActivity.rawUser.getString("birthday").split("-");
            age.setText(rawAge.length == 3?rawAge[2] + "." + rawAge[1] + "." + rawAge[0] : "null");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}