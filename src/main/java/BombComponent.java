import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.almasb.fxgl.dsl.FXGL.*;

public class BombComponent extends Component {
    private ArrayList<Entity> entities;
    private int radius;
    private PhysicsComponent physics;


    private int radiusLeft;
    private int radiusRight;
    private int radiusUp;
    private int radiusDown;


    public BombComponent(int radius) {
        this.radius = radius;

        entities = getGameWorld().getEntities();
    }

    public void checkCollisionRight(Entity bomb) {
        if (bomb.getType() == EntityTypes.BOMB_ACTIVE) {

            for (int i = 1; i <= radius; i++) {
                Entity cc = spawn("collisionCheck", bomb.getX() + 64 * i, bomb.getY());
                for (int j = 0; j < entities.size(); j++) {
                    if (cc.isColliding(entities.get(j))) {
                        if (entities.get(j).getType() == EntityTypes.EXPLODABLE_BLOCK || entities.get(j).getType() == EntityTypes.PLATFORM) {
                            radiusRight = i;
                            cc.removeFromWorld();
                            return;
                        }
                    }
                }
                getGameTimer().runOnceAfter(cc::removeFromWorld, Duration.seconds(0.1));
            }
        }
    }

    public void checkCollisionLeft(Entity bomb) {
        if (bomb.getType() == EntityTypes.BOMB_ACTIVE) {
            for (int i = 1; i <= radius; i++) {
                Entity cc = spawn("collisionCheck", bomb.getX() - 64 * i, bomb.getY());
                for (int j = 0; j < entities.size(); j++) {
                    if (cc.isColliding(entities.get(j))) {
                        if (entities.get(j).getType() == EntityTypes.EXPLODABLE_BLOCK || entities.get(j).getType() == EntityTypes.PLATFORM) {
                            radiusLeft = i;
                            cc.removeFromWorld();
                            return;
                        }
                    }
                }
                getGameTimer().runOnceAfter(cc::removeFromWorld, Duration.seconds(0.1));
            }
        }
    }

    public void checkCollisionDown(Entity bomb) {
        if (bomb.getType() == EntityTypes.BOMB_ACTIVE) {
            for (int i = 1; i <= radius; i++) {
                Entity cc = spawn("collisionCheck", bomb.getX(), bomb.getY() + 64 * i);
                for (int j = 0; j < entities.size(); j++) {
                    if (cc.isColliding(entities.get(j))) {
                        if (entities.get(j).getType() == EntityTypes.EXPLODABLE_BLOCK || entities.get(j).getType() == EntityTypes.PLATFORM) {
                            radiusDown = i;
                            cc.removeFromWorld();
                            return;
                        }
                    }
                }
                getGameTimer().runOnceAfter(cc::removeFromWorld, Duration.seconds(0.1));
            }
        }
    }

    public void checkCollisionUp(Entity bomb) {
        if (bomb.getType() == EntityTypes.BOMB_ACTIVE) {
            for (int i = 1; i <= radius; i++) {
                Entity cc = spawn("collisionCheck", bomb.getX(), bomb.getY() - 64 * i);
                for (int j = 0; j < entities.size(); j++) {
                    if (cc.isColliding(entities.get(j))) {
                        if (entities.get(j).getType() == EntityTypes.EXPLODABLE_BLOCK || entities.get(j).getType() == EntityTypes.PLATFORM) {
                            radiusUp = i;
                            cc.removeFromWorld();
                            return;
                        }
                    }
                }
                getGameTimer().runOnceAfter(cc::removeFromWorld, Duration.seconds(0.1));
            }
        }
    }

    public void explode(Entity bomb) {
        radiusLeft = radius;
        radiusRight = radius;
        radiusDown = radius;
        radiusUp = radius;

        getGameTimer().runOnceAfter(() -> {
            checkCollisionRight(bomb);
            checkCollisionLeft(bomb);
            checkCollisionUp(bomb);
            checkCollisionDown(bomb);

//            System.out.println("left\t- " + radiusLeft);
//            System.out.println("Right\t- " + radiusRight);
//            System.out.println("Down\t- " + radiusDown);
//            System.out.println("Up\t- " + radiusUp);
                }, Duration.seconds(2.8));

        getGameTimer().runOnceAfter(() -> {
            if (bomb.getType() == EntityTypes.BOMB_ACTIVE) {
                spawn("explosion", bomb.getX(), bomb.getY());

                for (int i = 1; i <= radiusRight; i++) {
                    spawn("explosion", bomb.getX() + 64 * i, bomb.getY());
                }

                for (int i = 1; i <= radiusLeft; i++) {
                    spawn("explosion", bomb.getX() - 64 * i, bomb.getY());
                }
                for (int i = 1; i <= radiusDown; i++) {
                    spawn("explosion", bomb.getX(), bomb.getY() + 64 * i);

                }
                for (int i = 1; i <= radiusUp; i++) {
                    spawn("explosion", bomb.getX(), bomb.getY() - 64 * i);

                }

                FXGL.play("explode.wav");
                bomb.removeFromWorld();
            }
        }, Duration.seconds(3));
    }

    public PhysicsComponent getPhysics() {
        return physics;
    }


}

