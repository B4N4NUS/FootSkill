package com.oldi.football.ui.stats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.oldi.football.OnSwipeTouchListener;
import com.oldi.football.R;

public class MoreComparisonActivity extends AppCompatActivity {
    private boolean swipeLR = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_comparison);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        MoreCompPart part = findViewById(R.id.comp_stat1);
        part.SetText("Сила удара"," – измеряется специальным профессиональным радаром с 5, 10,15 метров в зависимости от возраста с целью выявления технической составляющей элемента и последующей корректировки.", "https://drive.google.com/file/d/1eegW5wUxhrF6umQx_ezOZvwW81iG8NyE/view");
        part = findViewById(R.id.comp_stat2);
        part.SetText("Высота прыжка"," – тест проводится на оборудовании Vert, которое позволяет корректно оценить силу, технику вертикального прыжка.", "https://drive.google.com/file/d/1TEeeLV1Gokx88i_yJtqWHEh_c7N_2q1s/view");
        part = findViewById(R.id.comp_stat3);
        part.SetText("Длина прыжка"," – тест, направленный на оценку скоростно-силовых способностей и мощности.", "https://drive.google.com/file/d/1EUwIMm2j4xIhuUbfKCAv8Cm3dV7aXLBX/view?usp=sharing");
        part = findViewById(R.id.comp_stat4);
        part.SetText("Реакция"," – тестирование проводится на оборудовании Blazepod на умение быстро принимать решение и приспосабливаться к меняющимся факторам, реагируя нажатиями на определенный датчик вокруг спортсмена.", "https://drive.google.com/file/d/1fnz3G9iAsll_rQCtTigA8C3s1KDTBG7X/view");
        part = findViewById(R.id.comp_stat5);
        part.SetText("Скорость бега"," – выявляется проф. оборудованием на дистанции 10 метров с места и 10 метров с разбега, с целью выявления технической составляющей элемента и последующей корректировки.", "https://drive.google.com/file/d/1E_Vr1r6m4OAnbxV6DrQui1OTEettVtp_/view", "https://drive.google.com/file/d/16wd-Y24LADyweZlidqyfkfln1axUjpCc/view");
        part = findViewById(R.id.comp_stat6);
        part.SetText("Agility test"," – тестирование ловкости 5-10-5 метров, показывает способность спортсмена ускоряться, изменять направление и повторно ускоряться как можно быстрее. Проводится на оборудовании BlazePOD.", "https://drive.google.com/file/d/1cLpLeXc4TzNbgUfaLLYThSy2wBKKzsge/view?usp=sharing");
        part = findViewById(R.id.comp_stat7);
        part.SetText("FootSkill test"," – тестирование контроля мяча, оценки пространства, скорости принятия решения с мячом и завершения в виде передачи мяча. Проходит на специальных стенках-ребаундерах с добавление тренажёров Xlight.", "https://drive.google.com/file/d/1hr7koIJ_xpuixmTlc_3wY5K6oMIdFA1N/view?usp=sharing");
        part = findViewById(R.id.comp_stat8);
        part.SetText("Точность удара"," – тест, направленный на оценку ситуации с завершением в виде удара по воротам с 9, 10, 11 метров в определенную зону, которую показывает приложение.", "https://drive.google.com/file/d/1sLm25Vw3GAKu6wQ-ZPWaE7JL0nZ-ENtJ/view");

        View scroll = findViewById(R.id.more_comp_scroll);
        scroll.setOnTouchListener(new OnSwipeTouchListener(scroll.getContext()) {
            public void onSwipeTop() {
                super.onSwipeTop();
                System.out.println("top");
            }

            public void onSwipeRight() {
                swipeLR = true;
                onBackPressed();
                System.out.println("right");
            }

            public void onSwipeLeft() {
                swipeLR = false;
                onBackPressed();
                System.out.println("left");
            }

            public void onSwipeBottom() {
                super.onSwipeBottom();
                System.out.println("bot");
            }
        });
    }
    @Override
    public void finish() {
        super.finish();
        if (android.os.Build.VERSION.SDK_INT > 29) {
            if (swipeLR) {
                overridePendingTransition(R.anim.slide_lr, R.anim.slide_lr_out);
            } else {
                overridePendingTransition(R.anim.slide_rl, R.anim.slide_rl_out);
            }
        }
        System.gc();
    }

    @Override
    public void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}