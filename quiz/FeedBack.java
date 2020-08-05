package engine.quiz;

public class FeedBack {
    private final boolean success;
    private final String feedback;

    public FeedBack(boolean success) {
        this.success = success;
        feedback = success ? "Congratulations, you're right!" : "Wrong answer! Please, try again.";
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}
