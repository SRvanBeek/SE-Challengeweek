import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;
import static com.almasb.fxgl.dsl.FXGLForKtKt.image;


public class Player extends Component {

    private Integer movementSpeed = 200;
    private String direction = " ";

    private PhysicsComponent physics;
    private AnimatedTexture texture;

    public Player() {
        AnimationChannel initialAnimation = new AnimationChannel(
                image("player_idle_" + direction + ".png"),
                2,
                72,
                72,
                Duration.seconds(1),
                0,
                1
        );
        texture = new AnimatedTexture(initialAnimation);
        texture.loop();
    }


    public void left() {
        direction = "left";
        physics.setVelocityX(-movementSpeed);
    }


    public void right() {
        direction = "right";
        physics.setVelocityX(movementSpeed);
    }

    public void rightEnd() {
        direction = "right";
        physics.setVelocityX(movementSpeed = 0);
    }


    public void up() {
        direction = "up";
        physics.setVelocityY(-movementSpeed);
    }

    public void down() {
        direction = "down";
        physics.setVelocityY(movementSpeed);
    }
}
