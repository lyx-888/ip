package yxbot;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private YXBot yxbot;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image yxbotImage = new Image(this.getClass().getResourceAsStream("/images/yxbot.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        String welcomeMessage = "Hello! I'm YXBot\nWhat can I do for you?";
        dialogContainer.getChildren().add(DialogBox.getYxbotDialog(welcomeMessage, yxbotImage));
    }

    /** Injects the YXBot instance */
    public void setYxbot(YXBot y) {
        yxbot = y;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing YXBot's reply.
     * Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.trim().isEmpty()) {
            return;
        }

        String response = getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getYxbotDialog(response, yxbotImage)
        );

        userInput.clear();
    }

    /**
     * Gets response from YXBot for the given input.
     */
    private String getResponse(String input) {
        try {
            if (input.equalsIgnoreCase("bye")) {
                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                        System.exit(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
                return "Bye. Hope to see you again soon!";
            }

            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            java.io.PrintStream ps = new java.io.PrintStream(baos);
            java.io.PrintStream oldOut = System.out;

            System.setOut(ps);

            Command command = Parser.parse(input);

            if (command instanceof FindCommand) {
                command.execute(yxbot.getTasks(), yxbot.getUi(), yxbot.getStorage());
            } else {
                yxbot.executeCommand(command);
            }

            System.out.flush();
            System.setOut(oldOut);

            String output = baos.toString().trim();


            output = output.replace("____________________________________________________________\n", "");
            output = output.replace("\n____________________________________________________________", "");

            if (output.isEmpty()) {
                return "Command executed successfully.";
            }

            return output;

        } catch (YXBotException e) {
            return "Error: " + e.getMessage();
        }
    }
}
