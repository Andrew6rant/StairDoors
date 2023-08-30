package io.github.andrew6rant.stairdoors.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DoorBlock.class)
public class DoorBlockMixin {
    // Massive thanks to Setadokalo for the code this mixin is modified from
    // https://gitlab.com/Setadokalo/stairdoors-fabric/-/blob/master/src/main/java/com/setadokalo/stairdoors/mixin/DoorsMixin.java
    @Inject(cancellable = true, method = "canPlaceAt(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z", at = @At("HEAD"))
    private void stairdoors$injectStairPlacementRules(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState stairState = world.getBlockState(pos.down());
        if (stairState.getBlock() instanceof StairsBlock) {
            if (state.get(DoorBlock.FACING) == stairState.get(StairsBlock.FACING).getOpposite())
                cir.setReturnValue(true);
            else if (state.get(DoorBlock.FACING) == stairState.get(StairsBlock.FACING).rotateYClockwise() && state.get(DoorBlock.HINGE) == DoorHinge.LEFT)
                cir.setReturnValue(true);
            else if (state.get(DoorBlock.FACING) == stairState.get(StairsBlock.FACING).rotateYCounterclockwise() && state.get(DoorBlock.HINGE) == DoorHinge.RIGHT)
                cir.setReturnValue(true);
        }
    }

}
