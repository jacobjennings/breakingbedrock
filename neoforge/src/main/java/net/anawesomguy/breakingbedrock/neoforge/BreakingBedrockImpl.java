package net.anawesomguy.breakingbedrock.neoforge;

import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

public final class BreakingBedrockImpl {
    public static Path configDir() {
        return FMLPaths.CONFIGDIR.get();
    }
}
