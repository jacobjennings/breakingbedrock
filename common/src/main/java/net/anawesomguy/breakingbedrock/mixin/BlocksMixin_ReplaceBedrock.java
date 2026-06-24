package net.anawesomguy.breakingbedrock.mixin;

import net.anawesomguy.breakingbedrock.BedrockBlock;
import net.anawesomguy.breakingbedrock.BreakingBedrock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.function.Function;

@Mixin(Blocks.class)
public abstract class BlocksMixin_ReplaceBedrock {
    // The block-factory registration helper. Building the bedrock block through this (instead of constructing
    // it directly) lets vanilla assign the mandatory block id (ResourceKey) that blocks require since 1.21.2.
    @Shadow
    private static Block register(String name, Function<Properties, Block> factory, Properties properties) {
        throw new AssertionError();
    }

    // As of 26.1, bedrock is no longer created with a direct `new Block(...)` in <clinit>; it is registered via
    // `register("bedrock", properties)`, which constructs the Block internally. So we redirect that registration
    // call and rebuild bedrock as our BedrockBlock with the configured strength + requiresCorrectToolForDrops.
    @Redirect(
        method = "<clinit>",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/Blocks;register(Ljava/lang/String;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;",
            ordinal = 0
        ),
        slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=bedrock"))
    )
    private static Block breakingbedrock$replaceBedrock(String name, Properties properties) {
        return register(name, BedrockBlock::new,
                        properties.strength(BreakingBedrock.DESTROY_TIME, BreakingBedrock.EXPLOSION_RESIST)
                                  .requiresCorrectToolForDrops());
    }
}
