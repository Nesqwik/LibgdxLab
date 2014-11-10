package fr.nesqwik.gamelab;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import fr.nesqwik.gamelab.entities.ArrowMoveEntity;
import fr.nesqwik.gamelab.entities.Entity;
import fr.nesqwik.gamelab.entities.JumpEntity;
import fr.nesqwik.gamelab.entities.StaticBlockEntity;

public class GameLab implements ApplicationListener {
	public static final int SCREEN_WIDHT = 640;
	public static final int SCREEN_HEIGHT = 480;
	public static final int GRAVITY = 9;
	
	private SpriteBatch batch;
	private OrthographicCamera camera;

	private Array<Entity> entities = new Array<Entity>();
	private Array<Entity> entitiesToAdd = new Array<Entity>();
	private Array<Entity> entitiesToRemove = new Array<Entity>();

	@Override
	public void create () {
		batch = new SpriteBatch();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(true, SCREEN_WIDHT, SCREEN_HEIGHT);
		camera.update();
		
		addEntity(new StaticBlockEntity(this, new Vector2(0, 300)));
		addEntity(new StaticBlockEntity(this, new Vector2(20, 300)));
		addEntity(new StaticBlockEntity(this, new Vector2(40, 300)));
		addEntity(new StaticBlockEntity(this, new Vector2(60, 300)));
		addEntity(new StaticBlockEntity(this, new Vector2(80, 300)));
		addEntity(new StaticBlockEntity(this, new Vector2(100, 300)));
		addEntity(new StaticBlockEntity(this, new Vector2(120, 300)));
		addEntity(new StaticBlockEntity(this, new Vector2(140, 300)));
		addEntity(new StaticBlockEntity(this, new Vector2(160, 300)));
		//addEntity(new ArrowMoveEntity(this));
		addEntity(new JumpEntity(this));
	}

	@Override
	public void render () {
		addAllEntities();
		removeAllEntities();
		updateAllEntities();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		drawAllEntities();
		batch.end();
	}

	private void drawAllEntities() {
		for(Entity e : entities)
			e.draw(batch);
	}

	public void addEntity(Entity e) {
		entitiesToAdd.add(e);
	}
	
	public void removeEntity(Entity e) {
		entitiesToRemove.add(e);
	}
	
	private void updateAllEntities() {
		for(Entity e : entities) {
			e.update(Gdx.graphics.getDeltaTime());
		}
	}

	private void removeAllEntities() {
		entities.removeAll(entitiesToRemove, true);
		entitiesToRemove.clear();
	}

	private void addAllEntities() {
		entities.addAll(entitiesToAdd);
		entitiesToAdd.clear();
	}
	
	public Array<Entity> getEntities() {
		 return entities;
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
	}


}
