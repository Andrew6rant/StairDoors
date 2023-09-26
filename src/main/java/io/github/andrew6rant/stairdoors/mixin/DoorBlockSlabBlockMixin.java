package io.github.andrew6rant.stairdoors.mixin;

import io.github.andrew6rant.autoslabs.VerticalType;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.github.andrew6rant.autoslabs.Util.VERTICAL_TYPE;
import static net.minecraft.block.SlabBlock.TYPE;

@Mixin(DoorBlock.class)
public class DoorBlockSlabBlockMixin {
    @Inject(cancellable = true, method = "canPlaceAt(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z", at = @At("HEAD"))
    private void stairdoors$injectAutoSlabsPlacementRules(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState slabState = world.getBlockState(pos.down());
        if (slabState.getBlock() instanceof SlabBlock) {
            switch (state.get(DoorBlock.FACING)) {
                // I cannot use switch rules because I am maintaining compatibility with Java 8
                case NORTH: {
                    if (slabState.get(VERTICAL_TYPE) == VerticalType.NORTH_SOUTH && slabState.get(TYPE) == SlabType.BOTTOM) {
                        cir.setReturnValue(true);
                    }
                    break;
                }
                case SOUTH: {
                    if (slabState.get(VERTICAL_TYPE) == VerticalType.NORTH_SOUTH && slabState.get(TYPE) == SlabType.TOP) {
                        cir.setReturnValue(true);
                    }
                    break;
                }
                case EAST: {
                    if (slabState.get(VERTICAL_TYPE) == VerticalType.EAST_WEST && slabState.get(TYPE) == SlabType.BOTTOM) {
                        cir.setReturnValue(true);
                    }
                    break;
                }
                case WEST: {
                    if (slabState.get(VERTICAL_TYPE) == VerticalType.EAST_WEST && slabState.get(TYPE) == SlabType.TOP) {
                        cir.setReturnValue(true);
                    }
                    break;
                }
            }
        }
    }
}
