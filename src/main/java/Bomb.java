import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.getGameTimer;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;

public class Bomb extends Component {
    private int radius;
    private PhysicsComponent physics;

    public Bomb(int radius) {
        this.radius = radius;
    }

    public void explode(Entity bomb) {
        getGameTimer().runOnceAfter(() -> {
            if (bomb.getType() == EntityTypes.BOMB_ACTIVE) {
                bomb.removeFromWorld();
            }
        }, Duration.seconds(4));
    }

    public PhysicsComponent getPhysics() {
        return physics;
    }
}

