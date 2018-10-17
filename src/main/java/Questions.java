public class Questions {
    private String title;
    private String multi_marks_header;
    private String multi_question_header;
    private String[] multi_marks;
    private Answers[] answers;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Answers[] getAnswers() {
        return answers;
    }

    public void setAnswers(Answers[] answers) {
        this.answers = answers;
    }

    public String getMulti_marks_header() {
        return multi_marks_header;
    }

    public void setMulti_marks_header(String multi_marks_header) {
        this.multi_marks_header = multi_marks_header;
    }

    public String getMulti_question_header() {
        return multi_question_header;
    }

    public void setMulti_question_header(String multi_question_header) {
        this.multi_question_header = multi_question_header;
    }

    public String[] getMulti_marks() {
        return multi_marks;
    }

    public void setMulti_marks(String[] multi_marks) {
        this.multi_marks = multi_marks;
    }
}
