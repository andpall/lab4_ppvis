import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Main extends Application {
    private static TextField textField = new TextField();
    private static TextField res = new TextField();
    private static boolean trigAvailable = false;
    private static TreeView<Node> treeView = new TreeView<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Calculator");
        primaryStage.getIcons().add(new Image("file:icon.png"));
        primaryStage.setResizable(false);

        treeView.setPrefWidth(150);
        treeView.setEditable(false);
        textField.setEditable(false);
        res.setEditable(false);

        VBox left = new VBox();
        left.getChildren().add(res);
        left.getChildren().add(treeView);

        GridPane gridPane = new GridPane();

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(textField);
        vBox.getChildren().add(gridPane);
        vBox.setPadding(new Insets(10));
        VBox.setMargin(gridPane, new Insets(10));

        BorderPane borderPane = new BorderPane();

        initGridPane(gridPane);


        borderPane.setCenter(vBox);
        borderPane.setLeft(left);

        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void initGridPane(GridPane gridPane) {
        Button[] buttons = {
                new Button("0"), new Button("1"), new Button("2"),
                new Button("3"), new Button("4"), new Button("5"),
                new Button("6"), new Button("7"), new Button("8"),
                new Button("9"), new Button("."), new Button("C"),
                new Button("("), new Button(")"), new Button("+"),
                new Button("-"), new Button("*"), new Button("/"),
                new Button("%"), new Button("1/x"), new Button("sqrt"),
                new Button("trig"), new Button("sin"), new Button("cos"),
                new Button("tg"), new Button("ctg"), new Button("="),
                new Button("CE")
        };

        for (Button button : buttons) {
            button.setPrefSize(50, 50);
            button.setAlignment(Pos.CENTER);
        }

        buttons[22].setVisible(false);
        buttons[23].setVisible(false);
        buttons[24].setVisible(false);
        buttons[25].setVisible(false);

        gridPane.getColumnConstraints().add(new ColumnConstraints(50));
        gridPane.getColumnConstraints().add(new ColumnConstraints(50));
        gridPane.getColumnConstraints().add(new ColumnConstraints(50));
        gridPane.getColumnConstraints().add(new ColumnConstraints(50));
        gridPane.getColumnConstraints().add(new ColumnConstraints(50));
        gridPane.getColumnConstraints().add(new ColumnConstraints(50));
        gridPane.getColumnConstraints().add(new ColumnConstraints(50));

        gridPane.getRowConstraints().add(new RowConstraints(50));
        gridPane.getRowConstraints().add(new RowConstraints(50));
        gridPane.getRowConstraints().add(new RowConstraints(50));
        gridPane.getRowConstraints().add(new RowConstraints(50));

        gridPane.setMaxSize(200, 200);
        gridPane.add(buttons[9], 0, 0);
        gridPane.add(buttons[8], 1, 0);
        gridPane.add(buttons[7], 2, 0);
        gridPane.add(buttons[6], 0, 1);
        gridPane.add(buttons[5], 1, 1);
        gridPane.add(buttons[4], 2, 1);
        gridPane.add(buttons[3], 0, 2);
        gridPane.add(buttons[2], 1, 2);
        gridPane.add(buttons[1], 2, 2);
        gridPane.add(buttons[0], 0, 3);
        gridPane.add(buttons[10], 1, 3);
        gridPane.add(buttons[26], 2, 3);

        gridPane.add(buttons[14], 3, 0);
        gridPane.add(buttons[15], 3, 1);
        gridPane.add(buttons[16], 3, 2);
        gridPane.add(buttons[17], 3, 3);

        gridPane.add(buttons[12], 4, 0);
        gridPane.add(buttons[13], 4, 1);
        gridPane.add(buttons[18], 4, 2);
        gridPane.add(buttons[19], 4, 3);

        gridPane.add(buttons[27], 5, 0);
        gridPane.add(buttons[11], 5, 1);
        gridPane.add(buttons[20], 5, 2);
        gridPane.add(buttons[21], 5, 3);

        gridPane.add(buttons[22], 6, 0);
        gridPane.add(buttons[23], 6, 1);
        gridPane.add(buttons[24], 6, 2);
        gridPane.add(buttons[25], 6, 3);

        EventHandler<ActionEvent> addHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String string = Main.textField.getText();
                string += ((Button) event.getSource()).getText();
                Main.textField.setText(string);
            }
        };

        for (int i = 0; i < buttons.length; ++i) {
            if (i <= 10 || (i >= 12 && i <= 18) || i == 20 || (i >= 22 && i <= 25)) {
                buttons[i].setOnAction(addHandler);
            } else if (i == 11) {
                buttons[i].setOnAction(event -> {
                    String string = Main.textField.getText();
                    if (string.length() != 0) {
                        string = string.substring(0, string.length() - 1);
                        Main.textField.setText(string);
                    }
                });
            }
        }
        buttons[19].setOnAction(event -> {
            String added = "1/";
            Main.textField.setText(added + Main.textField.getText());
        });
        buttons[27].setOnAction(event -> {
            Main.textField.setText("");
            Main.res.setText("");
        });

        buttons[21].setOnAction(event -> {
            Main.trigAvailable = !trigAvailable;
            buttons[22].setVisible(trigAvailable);
            buttons[23].setVisible(trigAvailable);
            buttons[24].setVisible(trigAvailable);
            buttons[25].setVisible(trigAvailable);
        });
        buttons[26].setOnAction(event -> {
            try {
                Node root = Parser.ParseExpression(textField.getText());
                res.setText(Double.toString(root.evaluate()));
                changeTreeView(root);
            }  catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("Error!");
                alert.setContentText("Wrong input!");
                alert.showAndWait();
            }
        });
    }

    private static void changeTreeView(Node root) {
        treeView.setRoot(null);
        TreeItem<Node> treeRoot = new TreeItem<>(root);
        treeView.setRoot(treeRoot);
        fillTreeView(root, treeRoot);
    }

    private static void fillTreeView(Node node, TreeItem<Node> treeItem) {
        if (node instanceof ConstantNode) {
            return;
        } else if (node instanceof UnaryOperationNode) {
            UnaryOperationNode unaryOperationNode = ((UnaryOperationNode) node);
            treeItem.getChildren().add(new TreeItem<>(unaryOperationNode.getChild()));
            fillTreeView(unaryOperationNode.getChild(), treeItem.getChildren().get(0));
        } else if (node instanceof BinaryOperationNode) {
            BinaryOperationNode binaryOperationNode = (BinaryOperationNode) node;
            treeItem.getChildren().add(new TreeItem<>(binaryOperationNode.getLeft()));
            treeItem.getChildren().add(new TreeItem<>(binaryOperationNode.getRight()));
            fillTreeView(binaryOperationNode.getLeft(), treeItem.getChildren().get(0));
            fillTreeView(binaryOperationNode.getRight(), treeItem.getChildren().get(1));
        }
    }

}
