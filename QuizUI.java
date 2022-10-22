import javafx.scene.layout.*;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class QuizUI {
    private Image fillImage;
    private Image fillInImage;
    private Image logoImage;
    private ImageView fillView;
    private ImageView fillInView;
    private ImageView logoView;
    
    private BorderPane root;
    private Label description = new Label();
    private Button nextButton = new Button(">");
    private Button preButton = new Button("<");
    private Label timer = new Label("00:00");

    private Timeline line;
    int sTime = 0;
    int mTime = 0;

    public QuizUI(BorderPane root) throws IOException{
        this.root = root;
        if(fillImage == null){
            fillImage = new Image(new FileInputStream("src/res/img/fill.jpg"));
            fillInImage = new Image(new FileInputStream("src/res/img/fillin.png"));
            logoImage = new Image(new FileInputStream("src/res/img/k.png"));
        }
    }

    public void create(){
        addAnimationToTimer();
        fillView = new ImageView(fillImage);
        fillInView = new ImageView(fillInImage);
        logoView = new ImageView(logoImage);
        fillView.fitHeightProperty().bind(root.heightProperty().divide(2.5));
        fillView.fitWidthProperty().bind(fillView.fitHeightProperty().divide(0.5625));

        fillInView.fitHeightProperty().bind(root.heightProperty().divide(2.5));
        fillInView.fitWidthProperty().bind(fillInView.fitHeightProperty().divide(0.4195));

        logoView.fitWidthProperty().bind(root.widthProperty().divide(18));
        logoView.fitHeightProperty().bind(root.widthProperty().divide(32));

        HBox descBox = new HBox(logoView);
        descBox.getChildren().add(description);
        descBox.setAlignment(Pos.CENTER);

        VBox topBox = new VBox(descBox);
        topBox.getChildren().add(timer);
        topBox.setSpacing(5);
        topBox.setAlignment(Pos.CENTER);

        root.setTop(topBox);
        root.setRight(nextButton);
        root.setLeft(preButton);

        for(Node node: root.getChildren()){
            BorderPane.setAlignment(node, Pos.CENTER);
        }
    }

    public void addAnimationToTimer(){
        KeyFrame frame = new KeyFrame(Duration.millis(1000), event -> {changeTimer();});
        line = new Timeline(frame);
        line.setCycleCount(Timeline.INDEFINITE);
        line.play();
    }

    public void changeTimer(){
        sTime++;
        if(sTime==60){
            mTime++;
            sTime = 0;
        }

        if(mTime==60){
            mTime = 0;
        }

        timer.setText(toTimeFormat(sTime, mTime));
    }

    public static String toTimeFormat(int s, int m){
        return String.format("%02d", m)+":"+String.format("%02d", s);
    }

    public void changeQuestion(Question q){
        root.setBottom(q.getParent());
        description.setText(q.getDescription());
        ((Region)root.getBottom()).prefWidthProperty().bind(root.widthProperty());
        ((Region)root.getBottom()).prefHeightProperty().bind(root.heightProperty().divide(2.5));
        if(q.getClass().equals(Test.class)){
            root.setCenter(fillInView);
        }else{
            root.setCenter(fillView);
        }
    }
}