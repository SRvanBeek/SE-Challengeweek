import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class StartMenu extends FXGLMenu {

    public StartMenu(MenuType type) {
        super(type);

        final BorderPane root = new BorderPane();
        root.setMinHeight(getAppHeight());
        root.setMinWidth(getAppWidth());

        //Background Image
        final Image background = new Image("/assets/textures/background.png", 1920, 1080, false, true);
        final ImageView bgView = new ImageView(background);

        //Title Image
        final Image title = new Image("/assets/textures/title.png", 820, 337, false, true);
        final ImageView titleView = new ImageView(title);
        titleView.setTranslateX(80);
        titleView.setTranslateY(20);

        //Player1 Image
        final Image player1 = new Image("/assets/textures/player1_closeup.png", 189, 174, false, true);
        final ImageView player1View = new ImageView(player1);
        player1View.setTranslateY(350);
        player1View.setTranslateX(150);

        //Player2 Image
        final Image player2 = new Image("/assets/textures/player2_closeup.png", 189, 174, false, true);
        final ImageView player2View = new ImageView(player2);
        player2View.setTranslateY(350);
        player2View.setTranslateX(620);

        //VS Graphic
        final Image vs = new Image("/assets/textures/vs_graphic.png", 280, 172, false, true);
        final ImageView vsView = new ImageView(vs);
        vsView.setTranslateX(340);
        vsView.setTranslateY(350);

        final Button startButton = createButton("Start game");
        final Button exitButton = createButton("Exit game");

        startButton.setOnAction(e -> {
            fireNewGame();
        });

        exitButton.setOnAction(e -> {
            fireExit();
        });

        final VBox buttonBox = new VBox(10);
        buttonBox.getChildren().addAll(startButton, exitButton);
        buttonBox.setTranslateX(330);
        buttonBox.setTranslateY(600);

        getContentRoot().getChildren().add(bgView);
        getContentRoot().getChildren().add(titleView);
        getContentRoot().getChildren().add(buttonBox);
        getContentRoot().getChildren().addAll(player1View, vsView, player2View);

    }

    public Button createButton(String text) {
        final Button button = new Button(text);
        button.setStyle("" +
                "-fx-background-color: #654b00;" +
                "-fx-background-radius: 10;" +
                "-fx-background-insets: 0,1,2,3,0;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 10 20 10 20;" +
                "-fx-min-width: 300px;"
        );

        button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                button.setStyle("" +
                        "-fx-background-color: #b6b6b6;" +
                        "-fx-background-radius: 10;" +
                        "-fx-background-insets: 0,1,2,3,0;" +
                        "-fx-text-fill: #654b00;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 10 20 10 20;" +
                        "-fx-min-width: 300px;"
                );
            }
        });

        button.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                button.setStyle("" +
                        "-fx-background-color: #654b00;" +
                        "-fx-background-radius: 10;" +
                        "-fx-background-insets: 0,1,2,3,0;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 10 20 10 20;" +
                        "-fx-min-width: 300px;"
                );
            }
        });

        return button;
    }

}
