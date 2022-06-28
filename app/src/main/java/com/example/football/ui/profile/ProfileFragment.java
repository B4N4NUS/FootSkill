package com.example.football.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.football.TabbedActivity;
import com.example.football.databinding.ActivityTabbedBinding;
import com.example.football.ui.main.PageViewModel;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private PageViewModel pageViewModel;
    private ActivityTabbedBinding binding;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView text = getView().findViewById(R.id.profileLabel);
        text.setText(MainActivity.rawUser.toString());

        ImageView image = getView().findViewById(R.id.imageProf);
        TextView name = getView().findViewById(R.id.nameProf);
        TextView pos = getView().findViewById(R.id.posProf);
        TextView foot = getView().findViewById(R.id.footProf);
        TextView team = getView().findViewById(R.id.teamProf);
        Button exit = getView().findViewById(R.id.exitProf);

        exit.setOnClickListener(e-> {
//            Intent switchActivityIntent = new Intent(getActivity(), MainActivity.class);
//            switchActivityIntent.putExtra("message", "From: " + TabbedActivity.class.getSimpleName());
//            startActivity(switchActivityIntent);
            getActivity().onBackPressed();
            Saver.Save(getActivity(), "","");
            //getActivity().setContentView(R.layout.activity_main);
        });

        try{
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            //System.out.println(MainActivity.rawUser.getJSONObject("avatar").getString("url"));
            String url = "";
            JSONObject array = MainActivity.rawUser.getJSONObject("avatar");
            url = array.getString("url");
            System.out.println(url);
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(Connection.imagesUrl + url).getContent());
            image.setImageBitmap(bitmap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            name.setText(MainActivity.rawUser.getString("firstname") + " " + MainActivity.rawUser.getString("lastname"));
            pos.setText(MainActivity.rawUser.getString("playerPosition"));
            foot.setText(MainActivity.rawUser.getString("lead_leg"));
            team.setText(MainActivity.rawUser.getString("team"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }
}