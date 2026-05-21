import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class NotepadApp extends Application {

    private TextArea textArea;
    private File currentFile;

    @Override
    public void start(Stage stage) {
        textArea = new TextArea();

        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem newItem = new MenuItem("New");
        MenuItem openItem = new MenuItem("Open");
        MenuItem saveItem = new MenuItem("Save");
        MenuItem saveAsItem = new MenuItem("Save As");
        MenuItem exitItem = new MenuItem("Exit");

        fileMenu.getItems().addAll(newItem, openItem, saveItem, saveAsItem, new SeparatorMenuItem(), exitItem);

        Menu editMenu = new Menu("Edit");
        MenuItem cutItem = new MenuItem("Cut");
        MenuItem copyItem = new MenuItem("Copy");
        MenuItem pasteItem = new MenuItem("Paste");

        editMenu.getItems().addAll(cutItem, copyItem, pasteItem);

        menuBar.getMenus().addAll(fileMenu, editMenu);

        newItem.setOnAction(e -> newFile());
        openItem.setOnAction(e -> openFile(stage));
        saveItem.setOnAction(e -> saveFile(stage));
        saveAsItem.setOnAction(e -> saveFileAs(stage));
        exitItem.setOnAction(e -> stage.close());

        cutItem.setOnAction(e -> textArea.cut());
        copyItem.setOnAction(e -> textArea.copy());
        pasteItem.setOnAction(e -> textArea.paste());

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(textArea);

        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("JavaFX Notepad");
        stage.setScene(scene);
        stage.show();
    }

    private void newFile() {
        textArea.clear();
        currentFile = null;
    }

    private void openFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                textArea.clear();

                String line;
                while ((line = reader.readLine()) != null) {
                    textArea.appendText(line + "\n");
                }

                currentFile = file;
            } catch (IOException e) {
                showError("Could not open file.");
            }
        }
    }

    private void saveFile(Stage stage) {
        if (currentFile == null) {
            saveFileAs(stage);
        } else {
            writeFile(currentFile);
        }
    }

    private void saveFileAs(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");

        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            currentFile = file;
            writeFile(file);
        }
    }

    private void writeFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(textArea.getText());
        } catch (IOException e) {
            showError("Could not save file.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}