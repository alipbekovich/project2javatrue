import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class FillIn extends  Question{

    private TextField textField;
    @Override
    public String toString() {
        String str = getDescription();
        str = str.replace("{blank}", "______");
        return str;
    }

    @Override
    public void makeUI(){
        textField = new TextField();
        textField.setMaxWidth(300);
        textField.setPrefWidth(300);
        Label label = new Label("Type your answer here:");
        parent = new VBox();
        VBox box = (VBox)parent;
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(textField);
        box.getChildren().add(label);
        box.setSpacing(15);
    }

    @Override
    public boolean checkAnswer(){
        String s =  textField.getText();
        String answer = getAnswer().toLowerCase();
        if(s == null){
            return false;
        }

        if(answer.equals(s.toLowerCase())){
            return true;
        }else{
            return false;
        }
    }
}