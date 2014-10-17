package com.jumpbuttonstudios.vikingdodge.entity.actions;

import com.jumpbuttonstudios.vikingdodge.entity.EntityAction;
import com.jumpbuttonstudios.vikingdodge.entity.Player;

public class JumpAction extends EntityAction<Player> {

	/** The power of the jump */
	float power = 75;

	/** If the entity is currently grounded */
	boolean isGrounded = false;

	public JumpAction(Player entity) {
		super(entity);
	}

	@Override
	public void execute() {
		if (isGrounded && !getEntity().getSheepInteraction().hasSheep()) {
			getEntity().getPhysics().getBody()
					.applyForceToCenter(0, power, true);
			isGrounded = false;
		}
	}
	/**@return {@link #isGrounded} */
	public boolean isGrounded() {
		return isGrounded;
	}

	public void setGrounded(boolean isGrounded) {
		this.isGrounded = isGrounded;
	}

}
