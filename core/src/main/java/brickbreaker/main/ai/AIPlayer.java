package brickbreaker.main.ai;

import brickbreaker.main.components.RectComponent;
import brickbreaker.main.objects.Paddle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class AIPlayer implements Steerable<Vector2> {
    private FollowPath<Vector2, LinePath.LinePathParam> steeringBehavior;
    private SteeringAcceleration<Vector2> steerOutput;
    private Paddle paddle;

    private float orientation;
    private float maxAngularSpeed;
    private float maxLinearSpeed;
    private float maxLinearAcceleration;
    private boolean tagged;

    @Contract(value = " -> new", pure = true)
    @Override
    public @NotNull Vector2 getLinearVelocity() {
        return new Vector2();
    }

    @Override
    public float getAngularVelocity() {
        return 0;
    }

    @Override
    public float getBoundingRadius() {
        return 0;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean b) {
        tagged = b;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float v) {

    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float v) {
        maxLinearSpeed = v;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float v) {
        maxLinearAcceleration = v;
    }

    @Override
    public float getMaxAngularSpeed() {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float v) {
        maxAngularSpeed = v;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return 0;
    }

    @Override
    public void setMaxAngularAcceleration(float v) {

    }

    @Override
    public Vector2 getPosition() {
        return paddle.getComponent(RectComponent.class).position;
    }

    @Override
    public float getOrientation() {
        return orientation;
    }

    @Override
    public void setOrientation(float v) {
        orientation = v;
    }

    @Contract(pure = true)
    @Override
    public float vectorToAngle(@NotNull Vector2 vector2) {
        return (float)Math.atan2(-vector2.x, vector2.y);
    }

    @Contract("_, _ -> param1")
    @Override
    public @NotNull Vector2 angleToVector(@NotNull Vector2 vector2, float v) {
        vector2.x = -(float)Math.sin(v);
        vector2.y = (float)Math.cos(v);

        return vector2;
    }

    @Override
    public Location<Vector2> newLocation() {
        return this;
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public void init() {
        Array<Vector2> waypoints = new Array<>();
        waypoints.add(new Vector2());
        waypoints.add(new Vector2(Gdx.graphics.getWidth(), 0));

        steeringBehavior = new FollowPath<>(this, new LinePath<>(waypoints));
        steeringBehavior.setEnabled(true);
        steerOutput = new SteeringAcceleration<>(new Vector2());
    }

    public void update() {
        if(steeringBehavior != null) {
            steeringBehavior.calculateSteering(steerOutput);

            System.out.println(steerOutput.linear.x);
            System.out.println(steerOutput.linear.y);

            if(!steerOutput.linear.isZero()) paddle.getComponent(RectComponent.class).position.x += steerOutput.linear.x;
        }
    }
}
