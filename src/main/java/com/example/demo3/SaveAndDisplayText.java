package com.example.demo3;

import TodoPackage.RealTimeFileReader;
import TodoPackage.Todo;
import TodoPackage.TodoList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class SaveAndDisplayText extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        // создание необходимых объектов
        TodoList todoList = new TodoList();
        final LocalDateTime[] time = {LocalDateTime.now()};
        String filePath = TodoList.getRepositoryPath(time[0]);
        File file = new File(filePath);

        // Создание окна с выводящимися тудушками и установка стилей
        TextArea outputTextArea = new TextArea();
        outputTextArea.setStyle("-fx-font-size: 20px;");
        outputTextArea.setEditable(false); // Запретить редактирование текста

        // устанавливаем отслеживание изменений в файле
        RealTimeFileReader reader = new RealTimeFileReader(file, outputTextArea);
        reader.startReading();

        // создание поля ввода текста и установка стилей
        TextArea inputTextArea = new TextArea();
        inputTextArea.setPrefRowCount(1); // Устанавливаем максимальное количество строк равным 1

        // создание кнопок
        Button saveButton = new Button("Добавить");
        Button yesterdayButton = new Button("-1 день");
        Button tommorowButton = new Button("+1 день");
        Button todayButton = new Button("Сегодня");

        // создание заголовка и установка стилей
        Label titleLabel = new Label("Todoist");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Установить предпочтительные размеры для текстового поля вывода
        outputTextArea.setPrefWidth(700);
        outputTextArea.setPrefHeight(400);

        // Обработчик события нажатия на кнопку "Добавить"
        saveButton.setOnAction(event ->
        {
            String inputText = inputTextArea.getText(); // получаем текст

            // если текст не пустой, создаем туду и добавляем в файл
            if (!inputText.isEmpty())
            {
                Todo todo = new Todo(inputText);
                todoList.addTodo(todo, time[0]);
                outputTextArea.appendText("[ ] " + inputText + "\n");
                inputTextArea.clear(); // Очистка inputTextArea после сохранения
            }
        });

        // Обработчик события нажатия на кнопку "-1 день"
        yesterdayButton.setOnAction(event ->
        {
            // Вычитание одного дня
            time[0] = time[0].minusDays(1);
            outputTextArea.clear();
            setLabel(time[0], titleLabel);

            printTodos(time[0], outputTextArea);
        });

        // Обработчик события нажатия на кнопку "+1 день"
        tommorowButton.setOnAction(event ->
        {
            time[0] = time[0].plusDays(1);
            outputTextArea.clear();
            setLabel(time[0], titleLabel);

            printTodos(time[0], outputTextArea);
        });

        // Обработчик события нажатия на кнопку "Сегодня"
        todayButton.setOnAction(event ->
        {
            time[0] = LocalDateTime.now();
            outputTextArea.clear();
            titleLabel.setText("Todoist");

            printTodos(time[0], outputTextArea);
        });

        // Создание контейнера и добавление элементов управления
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(saveButton, yesterdayButton, tommorowButton, todayButton);

        VBox root = new VBox(10);
        root.getChildren().addAll(titleLabel, outputTextArea, inputTextArea, buttonBox);
        root.setPadding(new Insets(10));

        // Создание сцены и добавление контейнера на сцену
        Scene scene = new Scene(root, 700, 700);
        scene.getStylesheets().add("resources/hello-view.fxml");

        // Установка сцены на подмостки и отображение окна
        primaryStage.setTitle("Todoist");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // установить заголовок
    public void setLabel(LocalDateTime time, Label titleLabel)
    {
        LocalDate currentDate = LocalDateTime.now().toLocalDate();
        LocalDate labelDate = time.toLocalDate();

        if (labelDate.isEqual(currentDate))
        {
            titleLabel.setText("Todoist");
        }
        else
        {
            titleLabel.setText("Todoist :: " + time.format(DateTimeFormatter.ofPattern("MM-dd")));
        }
    }

    // вывести тудушки
    public void printTodos(LocalDateTime time, TextArea outputTextArea)
    {
        String filePath = TodoList.getRepositoryPath(time);

        File file = new File(filePath);
        RealTimeFileReader reader = new RealTimeFileReader(file, outputTextArea);
        reader.startReading();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}