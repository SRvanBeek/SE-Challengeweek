import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static com.almasb.fxgl.dsl.FXGL.getGameTimer;
import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;


public class PlayerComponent extends Component {

    private String name;

    private int speed = 100;
    private int bombCount = 1;
    private int bombsPlaced = 0;
    private int power = 1;
    private int health = 3;
    private int playerNumber;
    private int invisFrames = 0;

    
    private PhysicsComponent physics;
    private AnimatedTexture texture;
    private String direction = "down";
    private AnimationChannel initialAnimation;
    private AnimationChannel newAnimation;

    Image image = image("player_1_move_down.png");
    private ObservableList<Node> nodeList = getGameScene().getUINodes();

    public PlayerComponent(int playerNumber) {
        this.playerNumber = playerNumber;
        this.initialAnimation = new AnimationChannel(
                image,
                4,
                31,
                41,
                Duration.seconds(1),
                0,
                3
        );
        texture = new AnimatedTexture(initialAnimation);
        texture.loop();
    }


    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }
    public String getAnimationOnMovement() {
        String animationName = "player_" + playerNumber + "_";
        animationName += physics.isMovingX() || physics.isMovingY() ? "move_" : "idle_";
        animationName += direction + ".png";
        return animationName;
    }


    @Override
    public void onUpdate(double tpf) {
        Image chosenAnimationImage = image(getAnimationOnMovement());
        if (chosenAnimationImage != texture.getImage()) {
            image = chosenAnimationImage;
            texture.setImage(image);
        }
        if (health <= 0) {
            stopXMovement();
            stopYMovement();
            Image deadImage = image("player_" + playerNumber + "_dead.png");
            if (deadImage != texture.getImage()) {
                image = deadImage;
                texture.setImage(image);
            }
        }
    }


    public void left() {
        direction = "left";
        physics.setVelocityX(-speed);
    }


    public void right() {
        direction = "right";
        physics.setVelocityX(speed);
    }


    public void up() {
        direction = "Up";
        physics.setVelocityY(-speed);
    }


    public void down() {
        direction = "down";
        physics.setVelocityY(speed);
    }


    public void stopYMovement() {
        physics.setVelocityY(0);
    }


    public void stopXMovement() {
        physics.setVelocityX(0);
    }


    public void placeBomb(Entity bomb) {
        if (bombsPlaced == bombCount) {
            bomb.removeFromWorld();
            return;
        }
        if (bomb.getType() == EntityTypes.BOMB_ACTIVE) {
            bombsPlaced++;
            bomb.getComponent(BombComponent.class).explode(bomb);
            getGameTimer().runOnceAfter(() -> {
                bombsPlaced--;
            }, Duration.seconds(4));
        }
    }

    
    public void loseHealth() {
        if (invisFrames == 0) {
            invisFrames = 1;
            health--;
            getGameTimer().runOnceAfter(() -> {
                invisFrames = 0;
            }, Duration.millis(600));
        }
    }

    public void speedUp() {
        speed += 30;
    }

    public void powerUp() {
        power++;
    }

    public void bombCountUp() {
        bombCount++;
    }

    public String getName() {
        return this.name;
    }


    public int getSpeed() {
        return this.speed;
    }


    public int getPower() {
        return this.power;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }


    public int getHealth() {
        return health;
    }


    public void adjustHeartsPlayerOne() {

        if (this.health == 2) {
            nodeList.get(2).setOpacity(0);
        } else if (this.health == 1) {
            nodeList.get(1).setOpacity(0);
        } else if (this.health == 0) {
            nodeList.get(0).setOpacity(0);
        }

    }

    public void adjustHeartsPlayerTwo() {

        if (this.health == 2) {
            nodeList.get(5).setOpacity(0);
        } else if (this.health == 1) {
            nodeList.get(4).setOpacity(0);
        } else if (this.health == 0) {
            nodeList.get(3).setOpacity(0);
        }
    }
}
