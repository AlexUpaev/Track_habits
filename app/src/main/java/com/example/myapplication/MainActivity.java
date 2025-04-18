package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Настройка текста даты
        TextView dateText = findViewById(R.id.date_text);
        TextView dayText = findViewById(R.id.day_text);
        dateText.setText("СЕГОДНЯ");
        dayText.setText("18 мар.");

        // Обработчик кнопки добавления привычки
        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Логика добавления новой привычки
            }
        });

        // Обработчики табов
        Button allTab = findViewById(R.id.all_tab);
        Button morningTab = findViewById(R.id.morning_tab);
        Button dayTab = findViewById(R.id.day_tab);
        Button eveningTab = findViewById(R.id.evening_tab);

        allTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Логика отображения всех привычек
            }
        });

        morningTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Логика отображения привычек утром
            }
        });

        dayTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Логика отображения привычек днем
            }
        });

        eveningTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Логика отображения привычек вечером
            }
        });
    }
}