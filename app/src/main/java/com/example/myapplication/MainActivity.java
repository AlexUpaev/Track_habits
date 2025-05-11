package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import org.threeten.bp.LocalDate;
import org.threeten.bp.DayOfWeek;
import org.threeten.bp.format.TextStyle;
import org.threeten.bp.Month;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Ланчер для запуска AddHabitActivity и получения результата
    private ActivityResultLauncher<Intent> addHabitLauncher;

    // Список для хранения привычек
    private List<String> habitList = new ArrayList<>();
    private LinearLayout habitListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Находим контейнер для отображения привычек
        habitListView = findViewById(R.id.habit_list);

        // Инициализация ланчера
        addHabitLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String title = result.getData().getStringExtra("habit_title");
                        String description = result.getData().getStringExtra("habit_description");

                        if (title != null && !title.isEmpty()) {
                            habitList.add(title + "\n" + description);
                            updateHabitListUI();
                        }
                    }
                });

        // Текстовые поля для даты и дня недели
        TextView dateText = findViewById(R.id.date_text);
        TextView dayText = findViewById(R.id.day_text);

        // Получаем текущую дату
        LocalDate currentDate = LocalDate.now();

        // Устанавливаем текст даты: "9 Май"
        String todayDate = currentDate.getDayOfMonth() + " " +
                currentDate.getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault());
        dateText.setText(todayDate);

        // Устанавливаем день недели: "Пятница"
        DayOfWeek currentDayOfWeek = currentDate.getDayOfWeek();
        dayText.setText(currentDayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()));

        // Генерируем календарь
        generateCalendar(currentDate);

        // Обработчик кнопки "Добавить привычку"
        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddHabitActivity.class);
            addHabitLauncher.launch(intent);
        });
    }

    /**
     * Генерирует сетку календаря на основе указанной даты
     */
    private void generateCalendar(LocalDate date) {
        // Определяем первый день месяца
        LocalDate firstDayOfMonth = date.withDayOfMonth(1);

        // Начальный день недели (ПН=0 ... ВС=6)
        int startDayIndex = firstDayOfMonth.getDayOfWeek().getValue() - 1; // ПН=0, ВТ=1 ... ВС=6

        // Количество дней в месяце
        int daysInMonth = date.lengthOfMonth();

        // Создаём список чисел месяца
        List<Integer> monthDays = new ArrayList<>();
        for (int i = 1; i <= daysInMonth; i++) {
            monthDays.add(i);
        }

        // Двумерный массив 6x7 для отображения календаря
        int[][] calendarGrid = new int[6][7];
        int currentDayIndex = 0;

        for (int week = 0; week < 6; week++) {
            for (int day = 0; day < 7; day++) {
                if ((week == 0 && day >= startDayIndex) || (week > 0 && currentDayIndex < monthDays.size())) {
                    calendarGrid[week][day] = monthDays.get(currentDayIndex);
                    currentDayIndex++;
                } else {
                    calendarGrid[week][day] = 0; // Пустая ячейка
                }
            }
        }

        // Находим контейнер для календаря
        LinearLayout calendarGridLayout = findViewById(R.id.calendar_grid);
        calendarGridLayout.removeAllViews(); // Очистка предыдущих данных

        // Заполняем интерфейс
        for (int week = 0; week < 6; week++) {
            LinearLayout weekRow = new LinearLayout(this);
            weekRow.setOrientation(LinearLayout.HORIZONTAL);
            weekRow.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            for (int day = 0; day < 7; day++) {
                int dayNumber = calendarGrid[week][day];

                TextView dayView = new TextView(this);
                dayView.setLayoutParams(new LinearLayout.LayoutParams(
                        0,
                        80,
                        1f // Все ячейки равного размера
                ));
                dayView.setGravity(android.view.Gravity.CENTER);
                dayView.setTextSize(16);

                if (dayNumber != 0) {
                    dayView.setText(String.valueOf(dayNumber));
                } else {
                    dayView.setText(""); // Пустая ячейка
                }

                weekRow.addView(dayView);
            }

            calendarGridLayout.addView(weekRow);
        }
    }

    /**
     * Обновляет список привычек на экране
     */
    private void updateHabitListUI() {
        habitListView.removeAllViews(); // Очистка старых данных

        for (String habit : habitList) {
            TextView habitTextView = new TextView(this);
            habitTextView.setText(habit);
            habitTextView.setTextSize(16);
            habitTextView.setPadding(8, 8, 8, 8);

            habitListView.addView(habitTextView);
        }
    }
}