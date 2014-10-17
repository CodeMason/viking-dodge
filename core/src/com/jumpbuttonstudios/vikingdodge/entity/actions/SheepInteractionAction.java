package com.jumpbuttonstudios.vikingdodge.entity.actions;

import com.jumpbuttonstudios.vikingdodge.entity.EntityAction;
import com.jumpbuttonstudios.vikingdodge.entity.Player;

public class SheepInteractionAction extends EntityAction<Player> {

	private boolean hasSheep = false;
	
	private float slowDownFactor = 0.6f;

	public SheepInteractionAction(Player entity) {
		super(entity);
	}

	@Override
	public void execute() {
		this.hasSheep = true;
	}
	
	public void setHasSheep(boolean hasSheep) {
		this.hasSheep = hasSheep;
	}

	/** @return {@link #sheep} */
	public boolean hasSheep() {
		return hasSheep;
	}
	
	public float getSlowDownFactor() {
		return slowDownFactor;
	}

}
