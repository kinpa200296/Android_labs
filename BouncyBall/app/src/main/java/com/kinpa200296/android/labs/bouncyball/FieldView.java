package com.kinpa200296.android.labs.bouncyball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

public class FieldView extends View implements View.OnTouchListener {

    public static final long REFRESH_RATE = 40;
    public static final float BOUNCE_CHANGE_RATE = 1000.0f;
    public static final float FRICTION_CHANGE_RATE = 1000.0f;
    public static final float SIZE_CHANGE_RATE = 5.0f;
    public static final float MAX_DEVIATION = 0.3f;

    VelocityTracker velocityTracker;
    float gainedVelocityX, gainedVelocityY, startX, startY;
    float prevFriction, prevBounceRate, prevBallSize;
    double radius;
    SoundPool soundPool;
    int soundIdWallHit;
    Paint ballPaint;
    BallController controller;
    FieldCallback listener;
    boolean isTouched;

    public FieldView(Context context) {
        super(context);

        listener = (FieldCallback) context;

        setOnTouchListener(this);

        ballPaint = new Paint();
        ballPaint.setColor(context.getResources().getColor(R.color.ballColor));
        ballPaint.setStyle(Paint.Style.FILL);

        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundIdWallHit = soundPool.load(context, R.raw.wall_hit, 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (controller == null) {
            controller = new BallController(
                    new Ball(getWidth() / 2, getHeight() / 2, 0, 0, 1, 50, 30, getWidth() / 4, 0),
                    getWidth(), getHeight(), soundPool, soundIdWallHit);
        }

        canvas.drawColor(Color.WHITE);
        controller.applyPhysics();
        controller.drawBall(canvas, ballPaint);

        listener.displaySize(controller.getSize());
        listener.displayBounceRate(controller.getBounceRate());
        listener.displayFriction(controller.getFriction());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int actionMask = event.getActionMasked();
        int actionIndex = event.getActionIndex();
        int pointerCount = event.getPointerCount();

        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }

        switch (actionMask) {
            case MotionEvent.ACTION_DOWN:
                isTouched = true;
                if (controller.isTouchingBall(event.getX(), event.getY())) {
                    controller.disableGravity();
                    velocityTracker.clear();
                    velocityTracker.addMovement(event);
                } else {
                    startX = event.getX();
                    startY = event.getY();
                    prevBounceRate = controller.getBounceRate();
                    prevFriction = controller.getFriction();
                }
                break;
            case MotionEvent.ACTION_UP:
                isTouched = false;
                if (!controller.isGravityEnabled()) {
                    controller.setVelX(gainedVelocityX);
                    controller.setVelY(gainedVelocityY);
                    controller.enableGravity();
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (!controller.isGravityEnabled() && pointerCount == 2) {
                    prevBallSize = controller.getSize();
                    radius = Math.sqrt((event.getX(1) - startX) * (event.getX(1) - startX) +
                            (event.getY(1) - startY) * (event.getY(1) - startY));
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                if (!controller.isGravityEnabled() && pointerCount == 1) {
                    controller.setPosX(event.getX());
                    controller.setPosY(event.getY());
                    velocityTracker.addMovement(event);
                    velocityTracker.computeCurrentVelocity(1000);
                    gainedVelocityX = velocityTracker.getXVelocity();
                    gainedVelocityY = velocityTracker.getYVelocity();
                }
                if (pointerCount == 1 && controller.isGravityEnabled()) {
                    if (isHorizontalMove(event.getX(), event.getY())) {
                        controller.setFriction(prevFriction +
                                (event.getX() - startX) / FRICTION_CHANGE_RATE);
                    }
                    if (isVerticalMove(event.getX(), event.getY())) {
                        controller.setBounceRate(prevBounceRate -
                                (event.getY() - startY) / BOUNCE_CHANGE_RATE);
                    }
                }
                if (!controller.isGravityEnabled() && pointerCount == 2) {
                    controller.setSize(prevBallSize + (float) (-radius +
                            Math.sqrt((event.getX(1) - startX) * (event.getX(1) - startX) +
                                    (event.getY(1) - startY) * (event.getY(1) - startY))) / SIZE_CHANGE_RATE);
                }
                break;
            default:
                return false;
        }
        return true;
    }

    protected boolean isVerticalMove(float posX, float posY) {
        return Math.abs((startX - posX) / (startY - posY)) < MAX_DEVIATION;
    }

    protected boolean isHorizontalMove(float posX, float posY) {
        return Math.abs((startY - posY) / (startX - posX)) < MAX_DEVIATION;
    }
}
