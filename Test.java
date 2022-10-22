import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class Test extends Question{
    public static int numbOfOptions = 4;
    private String[] options;
    private ArrayList<String> labels = new ArrayList<>();
    private ToggleGroup tGroup;
    private static String[] colors = {
        "red","blue","orange","green"
    };

    public Test(){
        labels.add("A");
        labels.add("B");
        labels.add("C");
        labels.add("D");
    }

    public void setOptions(String[] options){
        this.options = new String[options.length];

        for(int i = 0;i < numbOfOptions; i++){
            this.options[i] = options[i];
        }

        Collections.shuffle(Arrays.asList(this.options));
    }

    public String getOptionAt(int index){
        return options[index];
    }

    public String printOptions(){
        StringBuilder sb = new StringBuilder();

        for (int i=0;i<numbOfOptions;i++){
            sb.append(labels.get(i) + ") " + options[i] + "\n");
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return getDescription() + "\n" + printOptions();

    }

    @Override
    public void makeUI(){
        parent = new GridPane();
        GridPane gPane = (GridPane)parent;
        tGroup = new ToggleGroup();
        for(int i=0;i<numbOfOptions;i++){
            RadioButton rb = new RadioButton();
            rb.setToggleGroup(tGroup);
            rb.prefHeightProperty().bind(gPane.heightProperty().divide(2));
            rb.prefWidthProperty().bind(gPane.widthProperty().divide(2));
            rb.setText(getOptionAt(i));
            rb.setStyle("-fx-background-color:"+colors[i]);
            gPane.add(rb, i/2, i%2);
        }
        gPane.setVgap(5);
        gPane.setHgap(5);
    }

    @Override
    public boolean checkAnswer(){
        RadioButton rb = (RadioButton)tGroup.getSelectedToggle();
        String s = null;
        if(rb!=null){
            s = rb.getText();
        }else{
            return false;
        }

        if(s.equals(getAnswer())){
            return true;
        }else{
            return false;
        }
    }
}