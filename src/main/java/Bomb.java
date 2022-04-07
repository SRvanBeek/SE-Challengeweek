import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Bomb extends Component {
    private int radius;
    private PhysicsComponent physics;

    public Bomb(int radius) {
        this.radius = radius;
    }

    public void explode(Entity bomb) {
        getGameTimer().runOnceAfter(() -> {
            if (bomb.getType() == EntityTypes.BOMB_ACTIVE) {
                spawn("explosion", bomb.getX(), bomb.getY());

                for (int i = 1; i <= radius; i++) {
                    Entity explosion = spawn("explosion", bomb.getX() + 64 * i, bomb.getY());
                }

                for (int i = 1; i <= radius; i++) {
                    spawn("explosion", bomb.getX() - 64 * i, bomb.getY());

                }
                for (int i = 1; i <= radius; i++) {
                    spawn("explosion", bomb.getX(), bomb.getY() + 64 * i);

                }
                for (int i = 1; i <= radius; i++) {
                    spawn("explosion", bomb.getX(), bomb.getY() - 64 * i);

                }

                FXGL.play("explode.wav");
                System.out.println("Explode sound");
                bomb.removeFromWorld();
            }
        }, Duration.seconds(4));
    }




    public PhysicsComponent getPhysics() {
        return physics;
    }
}

