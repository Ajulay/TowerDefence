package ru.agapov.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


public class Bullet {
    private Texture texture;
    private Vector2 position;
    private int speedOfFire;
    private Vector2 velocity;
    private Turret turret;
    private boolean visible = false;

    public Bullet(Turret turret) {
        this.position = turret.getPosition().cpy();
        this.velocity = new Vector2(0, 0);
        this.turret = turret;
        this.texture = new Texture("asteroid64.png");
        this.speedOfFire = 400;
    }

    public void update(Monster monster, float dt) {

        position.mulAdd(velocity, dt);

        if (position.cpy().sub(turret.getPosition()).len() >= turret.getTexture().getRegionWidth() / 1.5)
            visible = true;

        if (turret.getPosition().cpy().sub(position).len() > turret.getRangeOfFire()) {
            velocity.set(0, 0);
            visible = false;
            position = turret.getPosition().cpy();
        }

        if (monster.getPosition().cpy().sub(position).len()+10 < monster.getTexture().getRegionWidth() / 2) {
            visible = false;
            velocity.set(0, 0);
            position = turret.getPosition().cpy();
            monster.decrementLive();

            if (monster.getLives() == 0) {
                monster.getVelocity().set(0, 0);
                monster.getPosition().set(monster.getRoute().getStartX() * 80 + 40, monster.getRoute().getStartY() * 80 + 40);
                monster.getVelocity().set(monster.getRoute().getDirections()[0].x * monster.getSpeed(), monster.getRoute().getDirections()[0].y * monster.getSpeed());
                monster.setLastCellX(monster.getRoute().getStartX());
                monster.setLastCellY(monster.getRoute().getStartY());
                monster.setLives(3);
                monster.setRouteCounter(0);
            }
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - texture.getWidth() / 2, position.y - texture.getHeight() / 2,
                32, 32, 64, 64, .3f, .3f, 0, 0, 0, 64, 64, false, false);
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getSpeedOfFire() {
        return speedOfFire;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Turret getTurret() {
        return turret;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
