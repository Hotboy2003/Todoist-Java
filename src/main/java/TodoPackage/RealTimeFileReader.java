package TodoPackage;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class RealTimeFileReader
{
    private File file;
    private TextArea outputTextArea;
    private boolean running;

    // конструктор
    public RealTimeFileReader(File file, TextArea outputTextArea)
    {
        this.file = file;
        this.outputTextArea = outputTextArea;
        this.running = false;
    }

    // начать чтение
    public void startReading()
    {
        running = true;

        Thread thread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(file)))
            {
                String line;
                while (running && (line = reader.readLine()) != null)
                {
                    String[] elements = line.split(",");
                    if (elements.length >= 2)
                    {
                        String secondElement = elements[1];

                        Platform.runLater(() ->
                        {
                            outputTextArea.appendText("[ ] " + secondElement + "\n");
                        });
                    }
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        });

        thread.start();
    }

    // прекратить чтение
    public void stopReading() {
        running = false;
    }
}