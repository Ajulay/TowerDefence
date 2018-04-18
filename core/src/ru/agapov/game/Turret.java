package ru.agapov.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Turret {
    private Vector2 position;
    private TextureRegion texture;
    private float agle;
    private Bullet bullet;
    private boolean visible;
    private float timeOfFire=0f;
    private int rangeOfFire;
    private boolean shootReady = true;

    public Turret(TextureAtlas atlas) {

        this.position = new Vector2(0,0);
        this.texture = atlas.findRegion("turret");
        this.agle = 0.0f;
        this.visible = false;
        this.rangeOfFire = 300 + texture.getRegionWidth();
    }

    public void update(float dt, Monster monster) {

        Vector2 v = (monster.getPosition().cpy().sub(position)).nor(); //refactoring
        agle = (float) (Math.acos(v.x) * 180 / Math.PI);
        if (v.y < 0) {
            agle = agle * (-1);
        }
        if (!shootReady) {
            timeOfFire += dt;
        }
        if (timeOfFire >= 1) {
            shootReady = true;

        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - texture.getRegionWidth() / 2, position.y - texture.getRegionHeight()/ 2,
                40, 40, 80, 80, 1,1, agle);}

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    public Vector2 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public float getAgle() {
        return agle;
    }

    public Bullet getBullet() {
        return bullet;
    }

    public boolean isVisible() {
        return visible;
    }

    public float getTimeOfFire() {
        return timeOfFire;
    }

    public int getRangeOfFire() {
        return rangeOfFire;
    }

    public boolean isShootReady() {
        return shootReady;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setShootReady(boolean shootReady) {
        this.shootReady = shootReady;
    }

    public void setTimeOfFire(float timeOfFire) {
        this.timeOfFire = timeOfFire;
    }
    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }
}
