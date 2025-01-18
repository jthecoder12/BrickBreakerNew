package brickbreaker.main.objects;

import brickbreaker.main.components.CircleCollider;
import brickbreaker.main.components.CircleComponent;
import brickbreaker.main.scenes.SingleplayerScene;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import org.jetbrains.annotations.NotNull;

public final class Ball extends Entity {
    private final SingleplayerScene main;

    public Ball(@NotNull Engine engine, SingleplayerScene main) {
        engine.addEntity(this);

        add(new CircleComponent(new Vector2(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f), 15));
        add(new CircleCollider(new Vector2(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f), 15));

        this.main = main;
    }

    public void render(int speed) {
        getComponent(CircleComponent.class).render();
        getComponent(CircleComponent.class).position.add(speed*105*Gdx.graphics.getDeltaTime()*main.sideDirection, speed*60*Gdx.graphics.getDeltaTime()*main.ballDirection);

        getComponent(CircleCollider.class).updatePosition(getComponent(CircleComponent.class).position);

        // Top
        if(getComponent(CircleComponent.class).position.y >= Gdx.graphics.getHeight()-10) {
            main.ballDirection = -1;

            main.sound.play(1);

            if(main.controller != null) {
                if(main.controller.canVibrate()) {
                    main.controller.startVibration(100, 0.5f);
                }
            }
        }

        // Reset
        if(getComponent(CircleComponent.class).position.y <= 0) {
            if(main.controller != null) {
                if(main.controller.canVibrate()) {
                    main.controller.startVibration(200, 1);
                }
            }

            main.ballDirection = -1;
            main.sideDirection = -1;

            new Thread(() -> {
                long time = System.currentTimeMillis();

                while(System.currentTimeMillis() < time+500) {
                    Gdx.app.postRunnable(() -> getComponent(CircleComponent.class).position.set(new Vector2(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f)));
                }
            }).start();
        }

        // Left
        if(getComponent(CircleComponent.class).position.x <= 18.7499990463f) {
            main.sideDirection = 1;

            main.sound.play(1);

            if(main.controller != null) {
                if(main.controller.canVibrate()) {
                    main.controller.startVibration(100, 0.5f);
                }
            }
        }
        // Right
        else if(getComponent(CircleComponent.class).position.x > Gdx.graphics.getWidth()-18.7499990463f) {
            main.sideDirection = -1;

            main.sound.play(1);

            if(main.controller != null) {
                if(main.controller.canVibrate()) {
                    main.controller.startVibration(100, 0.5f);
                }
            }
        }
    }
}
