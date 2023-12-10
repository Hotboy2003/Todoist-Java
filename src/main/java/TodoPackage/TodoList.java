package TodoPackage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TodoList
{
    private List<Todo> todos;

    // конструктор
    public TodoList() {
        this.todos = new ArrayList<>();
    }

    // добавление тудушек
    public void addTodo(Todo todo, LocalDateTime time)
    {
        List<Todo> todos = getTodos(LocalDateTime.now()); // получаем тудушки
        todos.add(todo);
        storeTodos(todos, time); // сохраняем
    }

    // получить тудушки
    private List<Todo> getTodos(LocalDateTime time)
    {
        String filePath = getRepositoryPath(time); // получить путь до файла

        List<Todo> todos = new ArrayList<>();

        // если файл не существует, вернем пустой массив
        if (!new File(filePath).exists())
        {
            return todos;
        }

        // прочитать файл
        String content = null;
        try
        {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        String[] lines = content.split("\n"); // получить массив по разделителю

        // создать тудушку на основе прочитанного файла
        for (String line : lines)
        {
            String[] values = line.split(",");
            Todo todo = new Todo(values[1]);
            todo.setId(values[0]);
            todo.setCompleted(Boolean.parseBoolean(values[2]));
            todo.setCreated_at(Long.parseLong(values[3]));
            if (!values[4].equals("null"))
            {
                todo.setUpdated_at(Long.parseLong(values[4]));
            }
            if (!values[5].equals("null"))
            {
                todo.setCompleted_at(Long.parseLong(values[5]));
            }
            todos.add(todo);
        }
        return todos;
    }

    // сохранение тудушек в файл
    private void storeTodos(List<Todo> todos, LocalDateTime time)
    {
        String filePath = getRepositoryPath(time); // получить путь до файла
        StringBuilder content = new StringBuilder();
        File file = new File(filePath);

        // записать тудушки в строку
        for (Todo todo : todos)
        {
            content.append(todo.getId()).append(",")
                    .append(todo.getTitle()).append(",")
                    .append(todo.isCompleted()).append(",")
                    .append(todo.getCreated_at()).append(",")
                    .append(todo.getUpdated_at()).append(",")
                    .append(todo.getCompleted_at()).append("\n");
        }

        // записать строку в файл
        try
        {
            // если файл не существует
            if (!file.exists())
            {
                file.createNewFile(); // создать новый файл
            }

            FileWriter writer = new FileWriter(file);
            writer.write(content.toString());
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // получить название файла
    public static String getRepositoryPath(LocalDateTime time)
    {
        String formattedTime = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String fileName = formattedTime + ".txt";
        return fileName;
    }
}
