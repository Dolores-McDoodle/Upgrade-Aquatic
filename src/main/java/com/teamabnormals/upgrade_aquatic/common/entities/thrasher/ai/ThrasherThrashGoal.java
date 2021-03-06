package com.teamabnormals.upgrade_aquatic.common.entities.thrasher.ai;

import java.util.EnumSet;

import com.teamabnormals.upgrade_aquatic.api.endimator.EndimatedEntity;
import com.teamabnormals.upgrade_aquatic.api.util.NetworkUtil;
import com.teamabnormals.upgrade_aquatic.common.entities.thrasher.EntityThrasher;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;

public class ThrasherThrashGoal extends Goal {
	public EntityThrasher thrasher;
	private float originalYaw;
	private float thrashedTicks;
	
	public ThrasherThrashGoal(EntityThrasher thrasher) {
		this.thrasher = thrasher;
		this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
	}

	@Override
	public boolean shouldExecute() {
		Entity passenger = !thrasher.getPassengers().isEmpty() ? this.thrasher.getPassengers().get(0) : null;
		if(passenger instanceof PlayerEntity) {
			if(((PlayerEntity) passenger).isCreative() || passenger.isSpectator()) {
				return false;
			}
		}
		return passenger != null && this.thrasher.isNoAnimationPlaying() && this.thrasher.getRNG().nextFloat() < 0.05F;
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		Entity passenger = !thrasher.getPassengers().isEmpty() ? this.thrasher.getPassengers().get(0) : null;
		if(passenger instanceof PlayerEntity) {
			if(((PlayerEntity) passenger).isCreative() || passenger.isSpectator()) {
				return false;
			}
		}
		return this.thrashedTicks <= 55 && passenger != null;
	}
	
	@Override
	public void startExecuting() {
		this.originalYaw = this.thrasher.rotationYaw;
		NetworkUtil.setPlayingAnimationMessage(this.thrasher, EntityThrasher.THRASH_ANIMATION);
	}
	
	@Override
	public void resetTask() {
		this.originalYaw = 0;
		this.thrashedTicks = 0;
		NetworkUtil.setPlayingAnimationMessage(this.thrasher, EndimatedEntity.BLANK_ANIMATION);
	}
	
	@Override
	public void tick() {
		this.thrashedTicks++;
		
		this.thrasher.getNavigator().clearPath();
		
		this.thrasher.prevRotationYaw = this.thrasher.rotationYaw;
		
		this.thrasher.renderYawOffset = (float) ((this.originalYaw) + 75 * MathHelper.cos(this.thrasher.ticksExisted * 0.5F) * 1F);
		this.thrasher.rotationYaw = (float) ((this.originalYaw) + 75 * MathHelper.cos(this.thrasher.ticksExisted * 0.5F) * 1F);
		
		if(this.thrasher.isAnimationPlaying(EntityThrasher.THRASH_ANIMATION) && this.thrasher.getAnimationTick() % 5 == 0 && this.thrasher.getAnimationTick() != 0) {
			this.thrasher.getPassengers().get(0).attackEntityFrom(DamageSource.causeMobDamage(this.thrasher), 4.0F);
		}
	}
}