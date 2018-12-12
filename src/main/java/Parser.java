import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
    private String path;
    Parser(String path) {
        this.path = path;
    }

    public void parse(Paper paper) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String json = sb.toString();
            JSONObject jsonObject = new JSONObject(json);

            paper.getBarCode().setUser_id(jsonObject.getJSONObject("barcode").getInt("user_id"));
            paper.getBarCode().setForm_id(jsonObject.getJSONObject("barcode").getInt("form_id"));

            paper.getTitle().setText(jsonObject.getJSONObject("title").getString("text"));

            paper.getHeader().setText(jsonObject.getJSONObject("header").getString("text"));

            int length_questions = jsonObject.getJSONObject("body").getJSONArray("questions").length();
            Questions[] questions = new Questions[length_questions];
            for (int i = 0; i < questions.length; i++) {
                questions[i] = new Questions();
            }
            for (int i = 0; i < length_questions; i++) {
               questions[i].setTitle(jsonObject.getJSONObject("body").getJSONArray("questions")
                        .getJSONObject(i).getString("title"));
                questions[i].setMulti_marks_header(jsonObject.getJSONObject("body").getJSONArray("questions")
                        .getJSONObject(i).getString("multi_marks_header"));
                questions[i].setMulti_question_header(jsonObject.getJSONObject("body").getJSONArray("questions")
                        .getJSONObject(i).getString("multi_question_header"));
               int length_answer = jsonObject.getJSONObject("body").getJSONArray("questions")
                                   .getJSONObject(i).getJSONArray("answers").length();

               Answers[] answers = new Answers[length_answer];
                for (int j = 0; j < answers.length; j++) {
                    answers[j] = new Answers();
                }
                for (int j = 0; j < length_answer; j++) {
                    answers[j].setText(jsonObject.getJSONObject("body").getJSONArray("questions")
                            .getJSONObject(i).getJSONArray("answers").getJSONObject(j).getString("text"));
                    answers[j].setMark_name(jsonObject.getJSONObject("body").getJSONArray("questions")
                            .getJSONObject(i).getJSONArray("answers").getJSONObject(j).getString("mark_name"));
                }
                questions[i].setAnswers(answers);

                int length_marks = jsonObject.getJSONObject("body").getJSONArray("questions")
                        .getJSONObject(i).getJSONArray("multi_marks").length();
                String[] multi_marks = new String[length_marks];
                for (int j = 0; j < multi_marks.length; j++) {
                    multi_marks[j] = jsonObject.getJSONObject("body").getJSONArray("questions")
                            .getJSONObject(i).getJSONArray("multi_marks").getString(j);
                }
                questions[i].setAnswers(answers);
                questions[i].setMulti_marks(multi_marks);
            }
            paper.getBody().setQuestions(questions);

            paper.getFooter().setText(jsonObject.getJSONObject("footer").getString("text"));
        }
    }
}
