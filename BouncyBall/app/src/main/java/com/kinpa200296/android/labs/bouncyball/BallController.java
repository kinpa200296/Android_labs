package com.kinpa200296.android.labs.bouncyball;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.SoundPool;

public class BallController {

    public final static float GRAVITY_CONST = 9.81f;
    public final static float GRAVITY_MULTIPLIER = 10.0f;
    public final static float BORDER_ACCURACY = 0.1f;

    private Ball ball;
    private int fieldWidth;
    private int fieldHeight;
    private boolean gravityEnabled = true;
    private float accelerationX;
    private float accelerationY;
    private SoundPool soundPool;
    private int soundIdWallHit;

    public BallController(Ball ball, int fieldWidth, int fieldHeight, SoundPool soundPool, int soundIdWallHit) {
        this.ball = ball;
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.soundPool = soundPool;
        this.soundIdWallHit = soundIdWallHit;
    }

    public void drawBall(Canvas canvas, Paint ballPaint) {
        canvas.drawCircle(ball.getPosX(), ball.getPosY(), ball.getSize(), ballPaint);
    }

    public void enableGravity() {
        gravityEnabled = true;
    }

    public void disableGravity() {
        gravityEnabled = false;
    }

    public boolean isGravityEnabled() {
        return gravityEnabled;
    }

    public float getPosX() {
        return ball.getPosX();
    }

    public void setPosX(float posX) {
        if (posX - ball.getSize() < 0)
            ball.setPosX(ball.getSize());
        else if (posX > fieldWidth - ball.getSize())
            ball.setPosX(fieldWidth - ball.getSize());
        else ball.setPosX(posX);
    }

    public float getPosY() {
        return ball.getPosY();
    }

    public void setPosY(float posY) {
        if (posY - ball.getSize() < 0)
            ball.setPosY(ball.getSize());
        else if (posY > fieldHeight - ball.getSize())
            ball.setPosY(fieldHeight - ball.getSize());
        else ball.setPosY(posY);
    }

    public float getVelX() {
        return ball.getVelX();
    }

    public void setVelX(float velX) {
        ball.setVelX(velX);
    }

    public float getVelY() {
        return ball.getVelY();
    }

    public void setVelY(float velY) {
        ball.setVelY(velY);
    }

    public float getBounceRate() {
        return ball.getBounceRate();
    }

    public void setBounceRate(float bounceRate) {
        ball.setBounceRate(bounceRate);
    }

    public float getSize() {
        return ball.getSize();
    }

    public void setSize(float size) {
        ball.setSize(size);
    }

    public float getFriction() {
        return ball.getFriction();
    }

    public void setFriction(float friction) {
        ball.setFriction(friction);
    }

    public boolean isTouchingBall(float x, float y) {
        float deltaX = x - getPosX();
        float deltaY = y - getPosY();
        float radius = getSize();
        return deltaX * deltaX + deltaY * deltaY < radius * radius;
    }

    public void applyPhysics() {
        float deltaTime = FieldView.REFRESH_RATE / 1000.0f;

        setPosX(getPosX() + getVelX() * deltaTime + accelerationX * deltaTime * deltaTime / 2.0f);
        setPosY(getPosY() + getVelY() * deltaTime + accelerationY * deltaTime * deltaTime / 2.0f);

        accelerationX += (getVelX() >= 0 ? -1 : 1) * GRAVITY_CONST * GRAVITY_MULTIPLIER * getFriction();
        accelerationY += (getVelY() >= 0 ? -1 : 1) * GRAVITY_CONST * GRAVITY_MULTIPLIER * getFriction();

        setVelX(getVelX() + accelerationX * deltaTime);
        setVelY(getVelY() + accelerationY * deltaTime);

        if (getPosX() < getSize() + BORDER_ACCURACY || getPosX() > fieldWidth - getSize() - BORDER_ACCURACY) {
            setVelX(-getVelX() * getBounceRate());
            soundPool.play(soundIdWallHit, 1, 1, 0, 0, 1);
        }

        if (getPosY() < getSize() + BORDER_ACCURACY || getPosY() > fieldHeight - getSize() - BORDER_ACCURACY) {
            setVelY(-getVelY() * getBounceRate());
            soundPool.play(soundIdWallHit, 1, 1, 0, 0, 1);
        }
    }

    public void setGravityForce(float accelerationX, float accelerationY) {
        if (isGravityEnabled()) {
            this.accelerationX = -accelerationX * GRAVITY_MULTIPLIER;
            this.accelerationY = accelerationY * GRAVITY_MULTIPLIER;
        } else {
            this.accelerationX = 0;
            this.accelerationY = 0;
        }
    }
}
