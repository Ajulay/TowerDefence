package ru.agapov.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class TowerDefence extends ApplicationAdapter {
    private SpriteBatch batch;
    private Map map;
    private Monster monster1;
    private Monster monster2;
    private Monster lastMonster;
    private TextureAtlas atlas;
    private Turret turret;
    private Bullet bullet;
    private boolean catchMstr1;
    private boolean catchMstr2;
    private Vector2 tmpVector = new Vector2();

//some change?
    @Override
    public void create() {
        batch = new SpriteBatch();
        atlas = new TextureAtlas(Gdx.files.internal("game.pack"));
        map = new Map(atlas);
        monster1 = new Monster(atlas, map, 0);
        monster2 = new Monster(atlas, map, 1);
        turret = new Turret(atlas);
        bullet = new Bullet(turret);
        turret.setBullet(bullet);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        map.render(batch);
        monster1.render(batch);
        monster2.render(batch);
        if (turret.isVisible()) {
            turret.render(batch);
        }
        if (bullet.isVisible()) {

            bullet.render(batch);
        }
        batch.end();
    }

    public void update(float dt) {
        monster1.update(dt);
        monster2.update(dt);
        Vector2 drctMonstr1;
        Vector2 drctMonstr2;

        if (turret.isVisible()) {

            drctMonstr1 = monster1.getPosition().cpy().sub(turret.getPosition());
            drctMonstr2 = monster2.getPosition().cpy().sub(turret.getPosition());


            if (drctMonstr1.len() <= turret.getRangeOfFire() && !catchMstr2) {

                catchMstr1 = true;
                if (turret.isShootReady()) {
                    shot(drctMonstr1);
                }

                lastMonster = monster1;
            } else {
                catchMstr1 = false;
            }

            if (drctMonstr2.len() <= turret.getRangeOfFire() && !catchMstr1) {
                catchMstr2 = true;
                if (turret.isShootReady()) {
                    shot(drctMonstr2);
                }
                lastMonster = monster2;
            } else {
                catchMstr2 = false;
            }
            if (lastMonster != null) {
                turret.update(dt, lastMonster);
                bullet.update(lastMonster, dt);
            }
        }
        if (Gdx.input.justTouched()) {
            if (map.getData()[Gdx.input.getX() / 80][(Gdx.graphics.getHeight() - Gdx.input.getY()) / 80] == 0) {
                turret.setVisible(true);
                turret.setPosition(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
                bullet.setPosition(tmpVector.set(turret.getPosition()));
                bullet.setVisible(false);
                turret.setTimeOfFire(0);
                bullet.setVelocity(tmpVector.set(0,0));
            }
        }
    }

    public void shot(Vector2 drct) {
        turret.setShootReady(false);
        turret.setTimeOfFire(0);
        bullet.setVelocity(drct.cpy().nor().scl(bullet.getSpeedOfFire()));
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
