package com.jumpbuttonstudios.vikingdodge.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.jumpbuttonstudios.vikingdodge.Assets;
import com.jumpbuttonstudios.vikingdodge.VikingDodge;
import com.jumpbuttonstudios.vikingdodge.event.Event;
import com.jumpbuttonstudios.vikingdodge.ui.layouts.GameScreenLayout;

public class HudTable extends Table {

	/** Game instance */
	VikingDodge vikingDodge;

	/** The layout this hud is in */
	GameScreenLayout layout;

	/* Table for the move buttons */
	Table movementControlTable = new Table();
	/* The left and right buttons */
	Button left;
	Button right;

	/* Table for actions */
	Table actionsTable = new Table();
	/* The jump and throw buttons */
	Button jump;
	Button throwSheep;

	/* Table for hearts */
	Table heartTable = new Table();
	/* 3 Hearts */
	Array<HeartUI> hearts = new Array<HeartUI>();

	/* Table for the pause button */
	Table pauseButtonTable = new Table();
	/* Pause button */
	Button pause;

	/* Tabe for the score counter */
	Table scoreTable = new Table();
	Label label;

	public HudTable(final VikingDodge vikingDodge, final GameScreenLayout layout) {
		this.vikingDodge = vikingDodge;
		this.layout = layout;

		setName("hud");

		/* Create buttons */
		left = new Button(Assets.skin.get("left", ButtonStyle.class));
		right = new Button(Assets.skin.get("right", ButtonStyle.class));
		jump = new Button(Assets.skin.get("jump", ButtonStyle.class));
		throwSheep = new Button(Assets.skin.get("throw", ButtonStyle.class));
		throwSheep.setName("throwSheep");
		pause = new Button(Assets.skin.get("pauseButton", ButtonStyle.class));
		pause.setName("pauseButton");

		label = new ScoreCounterLabel(vikingDodge.getEntityHandler()
				.getPlayer(), Assets.skin.get("label", LabelStyle.class));

		for (int x = 0; x < 3; x++) {
			hearts.add(new HeartUI(layout));
		}

		setFillParent(true);
		debug();
		addListeners();
		addAll();

	}

	/** Add all the UI elements to the table */
	public void addAll() {
		for (HeartUI heart : hearts) {
			heartTable.add(heart).padRight(5);
		}

		add(heartTable).top().left().pad(10);
		scoreTable.add(label).right().padLeft(750).padBottom(50);
		add(scoreTable).top().right().center().align(Align.left);
		row().expand();
		movementControlTable.add(left);
		movementControlTable.add(right).padLeft(50);
		add(movementControlTable).bottom().left().pad(10).padBottom(70);
		actionsTable.add(jump).padRight(30);
		actionsTable.add(throwSheep);
		add(actionsTable).bottom().right().pad(10).padBottom(70);
		// pauseButtonTable.bottom();
		// TODO Reimplement pause menu
		// pauseButtonTable.add(pause);
		addActor(pauseButtonTable);
	}

	/** Add the listeners to the buttons */
	public void addListeners() {
		left.addListener(new ClickListener() {

			
//			@Override
//			public void enter(InputEvent event, float x, float y, int pointer,
//					Actor fromActor) {
//				layout.notify(null, Event.PLAYER_MOVE_LEFT);
//			}
//			
//			@Override
//			public void exit(InputEvent event, float x, float y, int pointer,
//					Actor toActor) {
//				layout.notify(null, Event.PLAYER_STOPPED);
//
//			}
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				layout.notify(null, Event.PLAYER_MOVE_LEFT);
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				layout.notify(null, Event.PLAYER_STOPPED);
			}

		});

		right.addListener(new ClickListener() {

//			@Override
//			public void enter(InputEvent event, float x, float y, int pointer,
//					Actor fromActor) {
//				layout.notify(null, Event.PLAYER_MOVE_RIGHT);
//
//			}
//
//			@Override
//			public void exit(InputEvent event, float x, float y, int pointer,
//					Actor toActor) {
//				layout.notify(null, Event.PLAYER_STOPPED);
//			}
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				layout.notify(null, Event.PLAYER_MOVE_RIGHT);
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				layout.notify(null, Event.PLAYER_STOPPED);
			}

		});

		jump.addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchDown(event, x, y, pointer, button);
				layout.notify(null, Event.PLAYER_JUMP);
				return false;
			}

		});

		throwSheep.addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				layout.notify(null, Event.PLAYER_THROW);
				return false;
			}

		});

		pause.addListener(new ClickListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				setVisible(false);
				layout.getPauseDialogueTable().setVisible(true);
				vikingDodge.setCanUpdateLogic(false);
				return true;
			}

		});
	}

	/** @return {@link #hearts} */
	public Array<HeartUI> getHearts() {
		return hearts;
	}

}
