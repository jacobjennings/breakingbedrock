> [!WARNING]
> **Experimental fork.** This is an experimental fork of Breaking Bedrock that updates the mod
> to **Minecraft 26.1.2** and migrates the build to the deobfuscated (unobfuscated) Minecraft
> toolchain. Starting with 26.1, Mojang ships Java Edition without obfuscation, so no Yarn/
> Intermediary/Parchment mappings are used anymore — the build compiles directly against the
> official, unobfuscated names. Treat these builds as untested until verified in-game.

# Breaking Bedrock

Allow bedrock to be mined with a netherite pickaxe!

This is a multi-loader mod (Fabric + NeoForge, via Architectury) that replaces vanilla bedrock with a
breakable variant. Behaviour is configurable via `config/breakingbedrock.properties`:

| Option             | Default     | Description                                                              |
| ------------------ | ----------- | ------------------------------------------------------------------------ |
| `destroy_time`     | `100`       | Destroy time for bedrock (obsidian is 50, stone is 1.5, `-1` = unbreakable). |
| `explosion_resist` | `3600000`   | Explosion resistance for bedrock (stone is 6, glass is 0.3).             |
| `drop_bedrock`     | `false`     | Whether bedrock drops as a block when broken.                            |

## Building

Requires **JDK 25**. Build both loaders with:

```sh
./gradlew build
```

Output jars are written to `fabric/build/libs/` and `neoforge/build/libs/`
(the unclassified `*.jar` is the loadable mod; `*-raw.jar` is the pre-bundle Loom output).

## Fork notes / what changed for 26.1.2

- Build migrated from `dev.architectury.loom` (remap) to `dev.architectury.loom-no-remap` — Minecraft
  26.1+ is unobfuscated, so there are no mappings to declare and `remapJar` is replaced by `shadowJar`.
- Toolchain bumped to Gradle 9 + Java 25; Shadow plugin moved to `com.gradleup.shadow`.
- `BlocksMixin_ReplaceBedrock` was rewritten: in 26.1 bedrock is registered via
  `register("bedrock", properties)` (no direct `new Block(...)` in `<clinit>`), so the mixin now
  redirects that registration call instead.
- `Inventory.getSelected()` → `Inventory.getSelectedItem()`.
- Mixin `compatibilityLevel` raised to `JAVA_25`; NeoForge metadata updated to `javafml` /
  `neoforge.mods.toml`; pack format and version ranges updated for 26.1.2.
