package brickbreaker.main.objects;

import brickbreaker.main.Main;
import brickbreaker.main.components.BoxCollider;
import brickbreaker.main.components.CircleCollider;
import brickbreaker.main.components.CircleComponent;
import brickbreaker.main.components.RectComponent;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import imgui.type.ImBoolean;
import org.jetbrains.annotations.NotNull;

import java.awt.Dimension;
import java.util.Random;

public final class Paddle extends Entity {
    private final Main main;
    private final Random random = new Random();

    public Paddle(@NotNull Engine engine, Main main) {
        engine.addEntity(this);

        add(new RectComponent(new Vector2(Gdx.graphics.getWidth()/2f-30, Gdx.graphics.getHeight()/2f-250), new Dimension(300, 20)));
        add(new BoxCollider(new Vector2(Gdx.graphics.getWidth()/2f-30, Gdx.graphics.getHeight()/2f-250), new Dimension(300, 20)));

        this.main = main;
    }

    public void render(ImBoolean mouseMode, Ball ball) {
        getComponent(RectComponent.class).render();

        getComponent(BoxCollider.class).updatePosition(getComponent(RectComponent.class).position);

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            getComponent(RectComponent.class).position.add(new Vector2(-15, 0));
        } else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            getComponent(RectComponent.class).position.add(new Vector2(15, 0));
        }

        if(mouseMode.get()) getComponent(RectComponent.class).position.x = Gdx.input.getX()-(Gdx.graphics.getWidth()/2f-30)/4f;

        if(getComponent(BoxCollider.class).checkWith(ball.getComponent(CircleCollider.class)) && ball.getComponent(CircleComponent.class).position.y > Gdx.graphics.getHeight()/2f-235) {
            ball.getComponent(CircleComponent.class).position.y += 10;

            main.ballDirection = 1;

            if(ball.getComponent(CircleComponent.class).position.x < getComponent(RectComponent.class).position.x+(Gdx.graphics.getWidth()/2f-30)/4f) {
                main.sideDirection = -1;
            } else if(ball.getComponent(CircleComponent.class).position.x > getComponent(RectComponent.class).position.x+(Gdx.graphics.getWidth()/2f-30)/4f) {
                main.sideDirection = 1;
            } else {
                if(random.nextInt(2) == 0) {
                    main.sideDirection = 1;
                } else {
                    main.sideDirection = -1;
                }
            }

            if(main.controller != null) {
                if(main.controller.canVibrate()) {
                    main.controller.startVibration(100, 0.5f);
                }
            }

            main.sound.play(1);
        }

        if(main.controller != null) {
            if(main.controller.getButton(main.controller.getMapping().buttonDpadLeft)) {
                getComponent(RectComponent.class).position.add(new Vector2(-15, 0));
            } else if(main.controller.getButton(main.controller.getMapping().buttonDpadRight)) {
                getComponent(RectComponent.class).position.add(new Vector2(15, 0));
            }
        }
    }
}
