import javafx.scene.*;

public abstract class Question {
    private String description;
    private String answer;
    protected Parent parent;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public abstract void makeUI();
    public abstract boolean checkAnswer();

    public Parent getParent(){
        return parent;
    }
}