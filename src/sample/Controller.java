package sample;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Controller extends Main {

    public VBox vB;
    public TextField fieldURL;

    public ProgressBar progressBar;
    public ProgressIndicator progressIndicator;


    // The method begins the process of downloading
    public void createCont() throws Exception{
       try {
           fieldURL.setText(fieldURL.getText());
           Task<Void> task = new DownloadTask(fieldURL.getText());
           progressBar.progressProperty().bind(task.progressProperty());

//       Доделать индикатор-----------------------------------------------------------------------------------------------
//        Task<Void> task1 = new DownloadTask(fieldURL.getText());
//        progressIndicator.progressProperty().bind(task1.progressProperty());

//        fieldURL.clear();
           Thread thread = new Thread(task);
           thread.setDaemon(true);
           thread.start();
       }
       catch (Exception e){
           System.out.println("Exception!!!!!!!!!!!!!!!!!");
       }
    }

    private class DownloadTask extends Task<Void> {

        public String url;

        public DownloadTask(String  fieldURL) {
            url = fieldURL;
        }

        @Override
        protected Void call() throws Exception {
            String ext = url.substring(url.lastIndexOf("."), url.length());
            URLConnection connection = new URL(url).openConnection();
            long fileLength = connection.getContentLengthLong();
            String nameFile = url.substring(url.lastIndexOf("/")+1, url.lastIndexOf("."));
            try (InputStream is = connection.getInputStream();
                 OutputStream os = Files.newOutputStream(Paths.get("C:\\Users\\Dacha\\git\\FirstBrowser\\download\\" + nameFile + ext))) {
                System.out.println(url);
                long nread = 0L;
                byte[] buf = new byte[8192];
                int n;
                while ((n = is.read(buf)) > 0) {
                    os.write(buf, 0, n);
                    nread += n;
                    updateProgress(nread, fileLength);
                }
            }
            return null;
        }

        @Override
        protected void failed() {
            System.out.println("failed");
        }

        @Override
        protected void succeeded() {
            System.out.println("downloaded");

        }
    }
    // Method  opens а window Download...
    public void downloadFile(ActionEvent actionEvent) throws IOException {

        System.out.println(fieldURL.getText() + " In downLoadFile");
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("download.fxml"));
        stage.setTitle("Download...");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.show();



    }
    // Method close window
    public void closeWindow(ActionEvent actionEvent) {
        Node sourse =(Node)actionEvent.getSource();
        Stage stage =(Stage)sourse.getScene().getWindow();
        stage.hide();
    }
}



