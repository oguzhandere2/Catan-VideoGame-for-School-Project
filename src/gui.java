import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.control.Label;


public class gui extends Application  {
    Stage window;
    Scene scene, scene2, scene3, scene4, scene5;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Catan Game");
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        //window.setMaximized(true);

        //buttons
        Button button1 = new Button();
        button1.setText("New Game");
        button1.setOnAction(e -> window.setScene(scene3));
        Button button2 = new Button();
        button2.setText("Load Game");

        Button button3 = new Button();
        button3.setText("Settings");
        button3.setOnAction(e -> window.setScene(scene2));

        Button button4 = new Button();
        button4.setText("How to play");
        button4.setOnAction(e->window.setScene(scene4));

        Button button5 = new Button();
        button5.setText("Credits");
        button5.setOnAction(e->window.setScene(scene5));

        Button button6 = new Button();
        button6.setText("Quit");
        button6.setOnAction(e-> window.close() );

        Button button7 = new Button ("Audio Settings");

        Button button9 = new Button ("Robbery Mode");
        Button button10 = new Button ("Angel Mode");

        //back buttons
        Button button11 = new Button ("BACK");
        Button button12 = new Button ("BACK");
        Button button8 = new Button ("BACK");
        Button button13 = new Button ("BACK");

        button11.setMaxSize(100,100);
        button12.setMaxSize(100,100);
        button8.setMaxSize(100,100);
        button13.setMaxSize(100,100);

        button8.setOnAction(e -> window.setScene(scene));
        button11.setOnAction(e -> window.setScene(scene));
        button12.setOnAction(e -> window.setScene(scene));
        button13.setOnAction(e -> window.setScene(scene));

        button1.setMaxWidth(500);
        button2.setMaxWidth(500);
        button3.setMaxWidth(500);
        button4.setMaxWidth(500);
        button5.setMaxWidth(500);
        button6.setMaxWidth(500);
        button9.setMaxSize(300,100);
        button10.setMaxSize(300,100);
        button7.setMaxSize(300,100);

        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        System.out.println("file:" + System.getProperty("user.dir") + "/src/catan.jpg");
        //images
        Image image = new Image("file:" + System.getProperty("user.dir") + "/src/catan.jpg");
        ImageView iv = new ImageView();
        Image image2 = new Image("file:" + System.getProperty("user.dir") + "/src/catan.jpg");
        ImageView iv2 = new ImageView();
        Image image3 = new Image("file:" + System.getProperty("user.dir") + "/src/catan.jpg");
        ImageView iv3 = new ImageView();
        Image image4 = new Image("file:" + System.getProperty("user.dir") + "/src/catan.jpg");
        ImageView iv4 = new ImageView();
        Image image5 = new Image("file:" + System.getProperty("user.dir") + "/src/catan.jpg");
        ImageView iv5 = new ImageView();

        iv.setImage(image);
        iv.setFitWidth(2000);
        iv.setFitHeight(1180);

        iv2.setImage(image2);
        iv2.setFitWidth(2000);
        iv2.setFitHeight(1180);

        iv3.setImage(image3);
        iv3.setFitWidth(2000);
        iv3.setFitHeight(1180);

        iv4.setImage(image4);
        iv4.setFitWidth(2000);
        iv4.setFitHeight(1180);

        iv5.setImage(image5);
        iv5.setFitWidth(2000);
        iv5.setFitHeight(1180);

        //mainmenu screen
        VBox centerMenu = new VBox(20);

        Label label2 = new Label("The Settlers of \n       CATAN");
        label2.setFont(new Font("Arial", 30));
        label2.setStyle("-fx-font-weight: bold;");
        centerMenu.getChildren().addAll(label2, button1,button2,button3,button4,button5,button6);
        centerMenu.setAlignment(Pos.CENTER);
        StackPane layout = new StackPane();
        layout.getChildren().add(iv);
        layout.getChildren().add(centerMenu);

        scene = new Scene(layout, screenBounds.getWidth(), screenBounds.getHeight());
        window.setScene(scene);
        window.show();


        //settings screen
        Label label3 = new Label("The Settlers of \n       CATAN");
        label3.setFont(new Font("Arial", 30));
        label3.setStyle("-fx-font-weight: bold;");
        Label label = new Label("Settings");
        label.setFont(new Font("Arial", 30));
        VBox centerMenu2 = new VBox(20);
        centerMenu2.getChildren().addAll(label3, label, button7, button11);
        centerMenu2.setAlignment(Pos.CENTER);
        StackPane layout2 = new StackPane();
        layout2.getChildren().add(iv2);
        layout2.getChildren().add(centerMenu2);
        scene2 = new Scene(layout2, screenBounds.getWidth(), screenBounds.getHeight());


        // new game screen
        Label label4 = new Label("The Settlers of \n       CATAN");
        label4.setFont(new Font("Arial", 30));
        label4.setStyle("-fx-font-weight: bold;");
        Label label5 = new Label("Choose Mode");
        label5.setFont(new Font("Arial", 30));


        VBox centerMenu3 = new VBox (20);
        centerMenu3.getChildren().addAll(label4, label5, button9, button10, button8);
        centerMenu3.setAlignment(Pos.CENTER);

        StackPane layout3 = new StackPane();
        layout3.getChildren().add(iv3);
        layout3.getChildren().add(centerMenu3);

        scene3 = new Scene(layout3, screenBounds.getWidth(), screenBounds.getHeight());

        // howtoplay screen
        Label label6 = new Label("The Settlers of \n       CATAN");
        label6.setFont(new Font("Arial", 30));
        label6.setStyle("-fx-font-weight: bold;");
        Label label7 = new Label("How to Play?");
        label7.setFont(new Font("Arial", 30));


        VBox centerMenu4 = new VBox (20);
        centerMenu4.getChildren().addAll(label6, label7, button12);
        centerMenu4.setAlignment(Pos.CENTER);

        StackPane layout4 = new StackPane();
        layout4.getChildren().add(iv4);
        layout4.getChildren().add(centerMenu4);

        scene4 = new Scene(layout4, screenBounds.getWidth(), screenBounds.getHeight());

        //credits screen

        Label label8 = new Label("The Settlers of \n       CATAN");
        label8.setFont(new Font("Arial", 30));
        label8.setStyle("-fx-font-weight: bold;");
        Label label9 = new Label("Credits");
        label9.setFont(new Font("Arial", 30));


        VBox centerMenu5 = new VBox (20);
        centerMenu5.getChildren().addAll(label8, label9, button13);
        centerMenu5.setAlignment(Pos.CENTER);

        StackPane layout5 = new StackPane();
        layout5.getChildren().add(iv5);
        layout5.getChildren().add(centerMenu5);

        scene5 = new Scene(layout5, screenBounds.getWidth(), screenBounds.getHeight());

    }

}