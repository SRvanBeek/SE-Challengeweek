import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;

    public class BombermanFactory implements EntityFactory {
        public BombermanFactory() {
        }

        @Spawns("player")
        public Entity newPlayer(SpawnData data) {
            return FXGL.entityBuilder()
                    .from(data)
                    .viewWithBBox("sprite.png")
                    .with(new CollidableComponent(true))
                    .with(new Player())
                    .scale(0.4D, 0.4D)
                    .type(EntityTypes.PLAYER)
                    .build();
        }

        @Spawns("wall")
        public Entity newWall(SpawnData data) {
            return FXGL.entityBuilder()
                    .from(data)
                    .viewWithBBox("sprite.png")
                    .with(new CollidableComponent(true))
                    .scale(0.4D, 0.4D)
                    .type(EntityTypes.PLAYER)
                    .build();
        }
    }

