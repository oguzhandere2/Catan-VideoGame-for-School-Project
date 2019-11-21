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
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


public class gui extends Application  {
    Stage window;
    Scene scene, scene2, scene3, scene4, scene5, sceneMap;
    double a = 2000;
    double b= 1000;

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
        button9.setOnAction(e->window.setScene(sceneMap));
        Button button10 = new Button ("Angel Mode");

        //back buttons
        Button button11 = new Button ("BACK");
        Button button12 = new Button ("BACK");
        Button button8 = new Button ("BACK");
        Button button13 = new Button ("BACK");
        Button button14 = new Button ("BACK");

        button11.setMaxSize(100,100);
        button12.setMaxSize(100,100);
        button8.setMaxSize(100,100);
        button13.setMaxSize(100,100);
        button14.setMaxSize(100,100);

        button8.setOnAction(e -> window.setScene(scene));
        button11.setOnAction(e -> window.setScene(scene));
        button12.setOnAction(e -> window.setScene(scene));
        button13.setOnAction(e -> window.setScene(scene));
        button14.setOnAction(e -> window.setScene(scene));

        button1.setMaxWidth(500);
        button2.setMaxWidth(500);
        button3.setMaxWidth(500);
        button4.setMaxWidth(500);
        button5.setMaxWidth(500);
        button6.setMaxWidth(500);
        button9.setMaxSize(300,100);
        button10.setMaxSize(300,100);
        button7.setMaxSize(300,100);

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
        Label label10= new Label ("Mehmet Tolga Tomris \n" +
                "Fatih Çakır \n" +
                "Atakan Arslan \n" +
                "Zeynep Berfin Gökalp \n" +
                "Oğuzhan Dere");
        label10.setStyle("-fx-font-weight: bold;");
        label9.setFont(new Font("Arial", 30));
        label10.setFont(new Font("Arial", 15));

        VBox centerMenu5 = new VBox (20);
        centerMenu5.getChildren().addAll(label8, label9, label10, button13);
        centerMenu5.setAlignment(Pos.CENTER);

        StackPane layout5 = new StackPane();
        layout5.getChildren().add(iv5);
        layout5.getChildren().add(centerMenu5);

        scene5 = new Scene(layout5, screenBounds.getWidth(), screenBounds.getHeight());

        //MAP

        //creating hexagons
        Polygon hexagon1 = new Polygon();
        Polygon hexagon2 = new Polygon();
        Polygon hexagon3 = new Polygon();
        Polygon hexagon4 = new Polygon();
        Polygon hexagon5 = new Polygon();
        Polygon hexagon6 = new Polygon();
        Polygon hexagon7 = new Polygon();
        Polygon hexagon8 = new Polygon();
        Polygon hexagon9 = new Polygon();
        Polygon hexagon10 = new Polygon();
        Polygon hexagon11 = new Polygon();
        Polygon hexagon12 = new Polygon();
        Polygon hexagon13 = new Polygon();
        Polygon hexagon14 = new Polygon();
        Polygon hexagon15 = new Polygon();
        Polygon hexagon16 = new Polygon();
        Polygon hexagon17 = new Polygon();
        Polygon hexagon18 = new Polygon();
        Polygon hexagon19 = new Polygon();

        //setting coordinates of hexagons
        hexagon1.getPoints().addAll((a/2), 50.0,
                (a/2)+35.35, 85.35,
                (a/2)+35.35, 135.35,
                (a/2), 170.7,
                (a/2)-35.35, 135.35,
                (a/2)-35.35, 85.35);

        hexagon2.getPoints().addAll((a/2)-70.7,50.0,
                (a/2)-35.35, 85.35,
                (a/2)-35.35, 135.35,
                (a/2)-70.7, 170.7,
                (a/2)-106.05, 135.5,
                (a/2)-106.05, 85.35);

        hexagon3.getPoints().addAll((a/2)+70.7, 50.0,
                (a/2)+106.5, 85.35,
                (a/2)+106.5, 135.35,
                (a/2)+70.7, 170.7,
                (a/2)+35.35, 135.5,
                (a/2)+35.35, 85.35);

        hexagon4.getPoints().addAll((a/2)+106.5, 135.35,
                (a/2)+141.4, 170.7,
                (a/2)+141.4, 220.7,
                (a/2)+106.5, 256.05,
                (a/2)+70.7, 220.7,
                (a/2)+70.7, 170.7);

        hexagon5.getPoints().addAll((a/2)+141.4, 220.7,
                (a/2)+176.75, 256.05,
                (a/2)+176.75, 306.05,
                (a/2)+141.4, 341.4,
                (a/2)+106.5, 306.5,
                (a/2)+106.5, 256.05);

        hexagon6.getPoints().addAll((a/2)+106.5, 306.5,
                (a/2)+141.4, 341.4,
                (a/2)+141.4, 391.4,
                (a/2)+106.5, 426.75,
                (a/2)+70.7, 391.4,
                (a/2)+70.7, 341.4);

        hexagon7.getPoints().addAll((a/2)+70.7, 391.4,
                (a/2)+106.5, 426.75,
                (a/2)+106.5, 476.75,
                (a/2)+70.7, 512.1,
                (a/2)+35.35, 476.75,
                (a/2)+35.35, 426.75);

        hexagon8.getPoints().addAll((a/2), 391.4,
                (a/2)+35.35, 426.75,
                (a/2)+35.35, 476.75,
                (a/2), 512.1,
                (a/2)-35.35, 476.75,
                (a/2)-35.35, 426.75);

        hexagon9.getPoints().addAll((a/2)-70.7,391.4,
                (a/2)-35.35, 426.75,
                (a/2)-35.35, 476.75,
                (a/2)-70.7, 512.1,
                (a/2)-106.05, 476.75,
                (a/2)-106.05, 426.75);

        hexagon10.getPoints().addAll((a/2)-106.5, 306.5,
                (a/2)-70.7, 341.4,
                (a/2)-70.7, 391.4,
                (a/2)-106.5, 426.75,
                (a/2)-141.4, 391.4,
                (a/2)-141.4, 341.4);

        hexagon11.getPoints().addAll((a/2)-141.4, 220.7,
                (a/2)-106.5, 256.05,
                (a/2)-106.5, 306.05,
                (a/2)-141.4, 341.4,
                (a/2)-176.75, 306.5,
                (a/2)-176.75, 256.05);

        hexagon12.getPoints().addAll((a/2)-106.5, 135.35,
                (a/2)-70.7, 170.7,
                (a/2)-70.7, 220.7,
                (a/2)-106.5, 256.05,
                (a/2)-141.4, 220.7,
                (a/2)-141.4, 170.7);

        hexagon13.getPoints().addAll((a/2)-35.35, 135.35,
                (a/2), 170.7,
                (a/2), 220.7,
                (a/2)-35.35, 256.05,
                (a/2)-70.7, 220.7,
                (a/2)-70.7, 170.7);

        hexagon14.getPoints().addAll((a/2)+35.35, 135.35,
                (a/2)+70.7, 170.7,
                (a/2)+70.7, 220.7,
                (a/2)+35.35, 256.05,
                (a/2), 220.7,
                (a/2), 170.7);

        hexagon15.getPoints().addAll((a/2)-70.7, 220.7,
                (a/2)-35.35, 256.05,
                (a/2)-35.35, 306.05,
                (a/2)-70.7, 341.4,
                (a/2)-106.5, 306.5,
                (a/2)-106.5, 256.05);

        hexagon16.getPoints().addAll((a/2), 220.7,
                (a/2)+35.35, 256.05,
                (a/2)+35.35, 306.05,
                (a/2), 341.4,
                (a/2)-35.35, 306.05,
                (a/2)-35.35, 256.05);

        hexagon17.getPoints().addAll((a/2)+70.7, 220.7,
                (a/2)+106.5, 256.05,
                (a/2)+106.5, 306.5,
                (a/2)+70.7, 341.4,
                (a/2)+35.35, 306.05,
                (a/2)+35.35, 256.05);

        hexagon18.getPoints().addAll((a/2)-35.35, 306.05,
                (a/2), 341.4,
                (a/2), 391.4,
                (a/2)-35.35, 426.75,
                (a/2)-70.7,391.4,
                (a/2)-70.7, 341.4);

        hexagon19.getPoints().addAll((a/2)+35.35, 306.05,
                (a/2)+70.7, 341.4,
                (a/2)+70.7, 391.4,
                (a/2)+35.35, 426.75,
                (a/2), 391.4,
                (a/2), 341.4);

        // changing colors of hexagons
        hexagon1.setFill(Color.DARKGREEN);
        hexagon1.setStroke(Color.BLACK);
        hexagon2.setFill(Color.BROWN);
        hexagon2.setStroke(Color.BLACK);
        hexagon3.setFill(Color.YELLOW);
        hexagon3.setStroke(Color.BLACK);
        hexagon4.setFill(Color.LIGHTGRAY);
        hexagon4.setStroke(Color.BLACK);
        hexagon5.setFill(Color.GREENYELLOW);
        hexagon5.setStroke(Color.BLACK);
        hexagon6.setFill(Color.DARKGREEN);
        hexagon6.setStroke(Color.BLACK);
        hexagon7.setFill(Color.DARKGOLDENROD);
        hexagon7.setStroke(Color.BLACK);
        hexagon8.setFill(Color.YELLOW);
        hexagon8.setStroke(Color.BLACK);
        hexagon9.setFill(Color.DARKGREEN);
        hexagon9.setStroke(Color.BLACK);
        hexagon10.setFill(Color.BROWN);
        hexagon10.setStroke(Color.BLACK);
        hexagon11.setFill(Color.YELLOW);
        hexagon11.setStroke(Color.BLACK);
        hexagon12.setFill(Color.DARKGREEN);
        hexagon12.setStroke(Color.BLACK);
        hexagon13.setFill(Color.LIGHTGRAY);
        hexagon13.setStroke(Color.BLACK);
        hexagon14.setFill(Color.DARKGREEN);
        hexagon14.setStroke(Color.BLACK);
        hexagon15.setFill(Color.GREENYELLOW);
        hexagon15.setStroke(Color.BLACK);
        hexagon16.setFill(Color.BROWN);
        hexagon16.setStroke(Color.BLACK);
        hexagon17.setFill(Color.YELLOW);
        hexagon17.setStroke(Color.BLACK);
        hexagon18.setFill(Color.LIGHTGRAY);
        hexagon18.setStroke(Color.BLACK);
        hexagon19.setFill(Color.GREENYELLOW);
        hexagon19.setStroke(Color.BLACK);

        //Creating a Group object
        StackPane layout6 = new StackPane();
        Group root = new Group(hexagon1,hexagon2,hexagon3,hexagon4,hexagon5,hexagon6,hexagon7,hexagon8,hexagon9,hexagon10,hexagon11,hexagon12,hexagon13,hexagon14,hexagon15,hexagon16,hexagon17,hexagon18,hexagon19);
        layout6.styleProperty().set("-fx-background-color: #0099FF");
        VBox box = new VBox();
        box.getChildren().addAll(button14);
        box.setAlignment(Pos.BOTTOM_CENTER);
        layout6.getChildren().addAll(root, box);
        layout6.setAlignment(Pos.CENTER);
        sceneMap = new Scene(layout6, a, b);

    }
}
