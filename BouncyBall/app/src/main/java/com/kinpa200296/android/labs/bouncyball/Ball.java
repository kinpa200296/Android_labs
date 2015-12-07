package com.kinpa200296.android.labs.bouncyball;

public class Ball {

    private float posX;
    private float posY;
    private float velX;
    private float velY;
    private float bounceRate;
    private float size;
    private float minSize;
    private float maxSize;
    private float friction;

    public Ball(float posX, float posY, float velX, float velY, float bounceRate,
                float size, float minSize, float maxSize, float friction) {
        setPosX(posX);
        setPosY(posY);
        setVelX(velX);
        setVelY(velY);
        setBounceRate(bounceRate);
        this.minSize = minSize;
        this.maxSize = maxSize;
        setSize(size);
        setFriction(friction);
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public float getBounceRate() {
        return bounceRate;
    }

    public void setBounceRate(float bounceRate) {
        if (bounceRate > 1.0)
            this.bounceRate = 1.0f;
        else if (bounceRate < 0.0)
            this.bounceRate = 0.0f;
        else this.bounceRate = bounceRate;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        if (size < minSize)
            this.size = minSize;
        else if (size > maxSize)
            this.size = maxSize;
        else this.size = size;
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        if (friction > 1.0)
            this.friction = 1.0f;
        else if (friction < 0.0)
            this.friction = 0.0f;
        else this.friction = friction;
    }
}
