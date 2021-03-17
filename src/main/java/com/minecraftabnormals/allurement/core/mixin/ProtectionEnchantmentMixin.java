package com.minecraftabnormals.allurement.core.mixin;

import com.minecraftabnormals.allurement.core.AllurementConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.enchantment.ProtectionEnchantment.Type;
import net.minecraft.inventory.EquipmentSlotType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProtectionEnchantment.class)
public abstract class ProtectionEnchantmentMixin extends Enchantment {

	@Shadow
	@Final
	public Type protectionType;

	protected ProtectionEnchantmentMixin(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
		super(rarityIn, typeIn, slots);
	}

	@Inject(at = @At("RETURN"), method = "getMaxLevel", cancellable = true)
	private void getMaxLevel(CallbackInfoReturnable<Integer> cir) {
		if (this.protectionType == Type.ALL && AllurementConfig.COMMON.disableProtection.get())
			cir.setReturnValue(1);
	}

	@Override
	public boolean isTreasureEnchantment() {
		if (this.protectionType == Type.ALL && AllurementConfig.COMMON.disableProtection.get())
			return true;
		return super.isTreasureEnchantment();
	}

	@Override
	public boolean canVillagerTrade() {
		if (this.protectionType == Type.ALL && AllurementConfig.COMMON.disableProtection.get())
			return false;
		return super.canVillagerTrade();
	}

	@Override
	public boolean canGenerateInLoot() {
		if (this.protectionType == Type.ALL && AllurementConfig.COMMON.disableProtection.get())
			return false;
		return super.canGenerateInLoot();
	}
}