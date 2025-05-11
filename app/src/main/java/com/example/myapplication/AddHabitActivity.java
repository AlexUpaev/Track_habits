package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;

public class AddHabitActivity extends AppCompatActivity {

    private EditText habitTitleEditText;
    private EditText habitDescriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        // Находим элементы по их ID
        habitTitleEditText = findViewById(R.id.habit_title_edit_text);
        habitDescriptionEditText = findViewById(R.id.habit_description_edit_text);

        Button saveButton = findViewById(R.id.save_button);

        // Обработчик кнопки "Сохранить"
        saveButton.setOnClickListener(v -> {
            String title = habitTitleEditText.getText().toString().trim();
            String description = habitDescriptionEditText.getText().toString().trim();

            // Проверка на пустые значения
            if (title.isEmpty()) {
                Toast.makeText(this, "Введите название привычки", Toast.LENGTH_SHORT).show();
                return;
            }

            // Создаём Intent для передачи данных обратно
            Intent resultIntent = new Intent();
            resultIntent.putExtra("habit_title", title);
            resultIntent.putExtra("habit_description", description);

            // Устанавливаем результат и закрываем активность
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}