import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

public class QuizMaker extends Application{
    ArrayList<Question> questions;
    Quiz quiz;
    QuizUI ui;
    Scene scene;
    BorderPane rootPane;
    int index = 0;
    Button nextButton, preButton;
    ImageView resultView;
    final boolean isShuffle = true;
    MediaPlayer player;
    @Override
    public void start(Stage primaryStage){
        Button chooseButton = new Button("Choose file");
        rootPane = new BorderPane();
        rootPane.setCenter(chooseButton);
        File mediaFile = new File("src/res/kahoot_music.mp3");
        Media music = new Media(mediaFile.toURI().toString());
        player = new MediaPlayer(music);
        FileChooser fileChooser = new FileChooser();
        chooseButton.setOnAction(event -> {
            try{
                File file = fileChooser.showOpenDialog(primaryStage);
                player.play();
                resultView = new ImageView(new Image(new FileInputStream("src/res/img/result.png")));
                quiz = Quiz.loadFromFile(file.getAbsolutePath());
                quiz.start();
                questions = quiz.getQuestions();
                if(isShuffle){
                    Collections.shuffle(questions);
                }
                ui = new QuizUI(rootPane);
                ui.create();
                index--;
                onNext();
                setAction();
            }catch(InvalidQuizFormatException e){
                System.out.println(e.getMessage());
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        });

        scene = new Scene(rootPane,800,600);
        primaryStage.setTitle("kahoot");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setAction(){
        nextButton = (Button)rootPane.getRight();
        preButton = (Button)rootPane.getLeft();
        preButton.setDisable(true);
        nextButton.setOnAction(e -> {onNext();});
        preButton.setOnAction(e -> {onPrevious();});
    }

    public void onNext(){
        index++;
        if(index == questions.size()-1){
            nextButton.setText("END");
        }

        if(index!=questions.size()){
            ui.changeQuestion(questions.get(index));
        }else{
            endQuiz();
        }

        if(index!=0){
            preButton.setDisable(false);
        }
    }

    public void onPrevious(){
        index--;
        if(index==0){
            preButton.setDisable(true);
        }

        if(index != questions.size()-1){
            nextButton.setText(">");
        }

        ui.changeQuestion(questions.get(index));
    }

    public void endQuiz(){
        double rightCount = 0;
        for(Question q: questions){
            if(q.checkAnswer()){
                rightCount++;
            }
        }

        Label result = new Label("Your result:");
        Label persent = new Label(rightCount/questions.size()*100+"%");
        Label number = new Label((int)rightCount+"/"+questions.size()+" correct");
        String timeString = QuizUI.toTimeFormat(ui.sTime, ui.mTime);
        Label quizTime = new Label(timeString);
        Button endButton = new Button("Close quiz");
        endButton.setPrefSize(400,80);
        endButton.setStyle("-fx-background-color:red");
        Button showButton = new Button("Show answer");
        showButton.setPrefSize(400, 80);
        showButton.setStyle("-fx-background-color:blue");
        VBox endBox = new VBox(result);
        resultView.fitHeightProperty().bind(rootPane.heightProperty().divide(2.5));
        resultView.fitWidthProperty().bind(resultView.fitHeightProperty().divide(0.5761));
        endBox.getChildren().addAll(persent,number,quizTime,endButton,showButton,resultView);
        endBox.setSpacing(15);
        scene.setRoot(endBox);
        endBox.setAlignment(Pos.CENTER);
        endButton.setOnAction(event -> {
            System.exit(0);
        });
    }
    public static void main(String[] args) throws FileNotFoundException, InvalidQuizFormatException{
        launch(args);
    }
}