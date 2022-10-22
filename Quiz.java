import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Quiz {
    private String name;
    private static ArrayList<Question> questions = new ArrayList<>();

    public Quiz(){
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void addQuestion(Question q) {

        questions.add(q);
    }

    public ArrayList<Question> getQuestions(){
        return questions;
    }
    
    //метод для того что бы разделить вопросы на два класса.
    public static Quiz loadFromFile(String filename) throws FileNotFoundException, InvalidQuizFormatException {
        Quiz quiz = new Quiz();

        Scanner sc = new Scanner(new File(filename));
        ArrayList<String> allText = new ArrayList<>();

        while (sc.hasNextLine()){
            allText.add(sc.nextLine());
        }
        allText.add("");

        int count = 0;
        for(int i=0;i<allText.size();i++){
            if(allText.get(i).isEmpty()){
                if(count==2){
                    FillIn question = new FillIn();
                    question.setDescription(allText.get(i-count));
                    question.setAnswer(allText.get(i-count+1));
                    quiz.addQuestion(question);
                }

                else if(count==Test.numbOfOptions+1){
                    Test question = new Test();
                    question.setDescription(allText.get(i-count));
                    question.setAnswer(allText.get(i-count+1));
                    
                    String[] options = new String[Test.numbOfOptions];
                    for(int j=1;j<=Test.numbOfOptions;j++){
                        options[j-1] = allText.get(i-j);
                    }
                    question.setOptions(options);
                    quiz.addQuestion(question);
                }else{
                    throw new InvalidQuizFormatException("Incorrect quiz format!");
                }

                count = 0;

            }else{
                count++;
            }
        }

        return quiz;
    }

    //метод для того что бы запустить проект
    public void start(){
        for(Question q: questions){
            q.makeUI();
        }
    }
}