import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.sun.javafx.scene.text.TextLayout;
import javafx.geometry.Point2D;

public class BombermanFactory implements EntityFactory {
        public BombermanFactory() {
        }
        @Spawns("platform")
        public Entity newPlatform(SpawnData data) {
            return FXGL.entityBuilder(data)
                    .type(EntityTypes.PLATFORM)
                    .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                    .with(new PhysicsComponent())
                    .build();
    }

        @Spawns("player1")
        public Entity newPlayer1(SpawnData data) {
            PhysicsComponent physics = new PhysicsComponent();
            physics.setBodyType(BodyType.DYNAMIC);

            physics.addGroundSensor(
                    new HitBox(
                            "GROUND_SENSOR",
                            new Point2D(16, 38),
                            BoundingShape.box(6, 8)
                    )
            );

            physics.setFixtureDef(new FixtureDef().friction(0.0f));

            return FXGL.entityBuilder(data)
                    .at(60, 60)
                    .type(EntityTypes.PLAYER)
                    .bbox(new HitBox(new Point2D(0, 0), BoundingShape.box(31, 41)))
                    .with(new CollidableComponent(true))
                    .with(physics)
                    .with(new Player())
                    .build();
        }

        @Spawns("player2")
        public Entity newPlayer2(SpawnData data) {
            PhysicsComponent physics = new PhysicsComponent();
            physics.setBodyType(BodyType.DYNAMIC);

            physics.addGroundSensor(
                    new HitBox(
                            "GROUND_SENSOR",
                            new Point2D(16, 38),
                            BoundingShape.box(6, 8)
                    )
            );

            physics.setFixtureDef(new FixtureDef().friction(0.0f));
            return FXGL.entityBuilder(data)
                    .at(60, 60)
                    .type(EntityTypes.PLAYER)
                    .bbox(new HitBox(new Point2D(0, 0), BoundingShape.circle(36)))
                    .with(new CollidableComponent(true))
                    .with(physics)
                    .with(new Player())
                    .build();
        }

        @Spawns("wall")
        public Entity newWall(SpawnData data) {
            return FXGL.entityBuilder()
                    .from(data)
                    .viewWithBBox("wall.png")
                    .with(new PhysicsComponent())
                    .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),data.<Integer>get("Height"))))
                    .scale(0.4D, 0.4D)
                    .type(EntityTypes.PLAYER)
                    .build();
        }

        @Spawns("bomb")
        public Entity newBomb(SpawnData data) {
            PhysicsComponent physics = new PhysicsComponent();
            physics.setBodyType(BodyType.DYNAMIC);

            physics.addGroundSensor(
                    new HitBox(
                            "GROUND_SENSOR",
                            new Point2D(16, 38),
                            BoundingShape.box(6, 8)
                    )
            );

            physics.setFixtureDef(new FixtureDef().friction(0.0f));

            return FXGL.entityBuilder()
                    .from(data)
                    .viewWithBBox("box-1.png")
                    .with(physics)
                    .bbox(new HitBox(new Point2D(0, 0), BoundingShape.box(64, 64)))
                    .with(new Bomb(data.get("radius")))
                    .scale(1, 1)
                    .type(EntityTypes.BOMB)
                    .build();
        }
    }

