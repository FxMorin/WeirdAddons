# WeirdAddons
[Fabric Carpet](https://github.com/gnembon/fabric-carpet) extension mod which adds a bunch of interesting but weird new features.

# Features
## commandWeird
Simple toggle for the /weird command
* Type: `String`
* Default value: `ops`
* Required options: `ops`,`false`,`true`
* Categories: `WEIRD`
* Additional notes:
    * The /weird command allows for chunk monitoring as well as other weird additions that need more arguments then a carpet rule
  
## observerDelay
Change the delay length of observers (how long it takes to turn on)
* Type: `int`  
* Default value: `2` 
* Categories: `WEIRD`,`CREATIVE`

## observerPulse
Change the pulse length of observers (how long it stays on)
* Type: `int`
* Default value: `2`
* Categories: `WEIRD`,`CREATIVE`

## fastPistons
Pistons will extend and retract with 1 tick of delay
* Type: `boolean`
* Default value: `false`
* Required options: `true`,`false`
* Categories: `WEIRD`,`CREATIVE`

## movableMovingPiston
Makes moving_piston (B36) movable. Requires MovableBlockEntities to be on
* Type: `boolean`
* Default value: `false`
* Required options: `true`,`false`
* Categories: `WEIRD`,`CREATIVE`,`EXPERIMENTAL`

## blockUpdateHell
Basically all setBlock calls will use this number
* Type: `int`  
* Default value: `-2` 
* Categories: `WEIRD`,`CREATIVE`,`EXPERIMENTAL`
* Additional notes:
  * Custom block updates from: -1 to 127 | use -2 to disable

## spongeEverything
Makes it so that sponges can absorb all blocks
* Type: `boolean`  
* Default value: `false` 
* Required options: `true`,`false`
* Categories: `WEIRD`,`CREATIVE`

## spongeLimit
Change the max amount of that a sponge can absorb
* Type: `int`
* Default value: `64`
* Categories: `WEIRD`,`CREATIVE`

## spongeLava
The sponge now absorbs lava instead of water, you happy now?
* Type: `boolean`
* Default value: `false`
* Required options: `true`,`false`
* Categories: `WEIRD`,`CREATIVE`

## spongeInfinite
Removes all restrictions that sponges have
* Type: `boolean`
* Default value: `false`
* Required options: `true`,`false`
* Categories: `WEIRD`,`CREATIVE`,`EXPERIMENTAL`
* Additional notes:
  * You can still change the spongeLimit to avoid crashing. spongeLimit is ignored if its the default value!

## spongeFaster
Modify sponges so that the blocks they remove don't create blockUpdates
* Type: `boolean`
* Default value: `false`
* Required options: `true`,`false`
* Categories: `WEIRD`,`CREATIVE`
* Additional notes:
  * Only really noticeable when using spongeInfinite

## spongeCeption
Sponge, sponges itself
* Type: `boolean`
* Default value: `false`
* Required options: `true`, `false`
* Categories: `WEIRD`,`CREATIVE`

## spongeReusable
Sponge does not become wet when used
* Type: `boolean`
* Default value: `false`
* Required options: `true`,`false`
* Categories: `WEIRD`,`CREATIVE`
* Additional notes:
  * If spongeCeption is enabled, this will not do anything

## spongeAbsorbsExplosions
Sponge will prevent block explosion damage if its in the tnt list of blocks to break
* Type: `boolean`
* Default value: `false`
* Required options: `true`,`false`
* Categories: `WEIRD`,`CREATIVE`

## enchantmentOverride
Allows you to enchant past the highest vanilla enchant limit using /enchant
* Type: `boolean`
* Default value: `false`
* Required options: `true`,`false`
* Categories: `WEIRD`,`CREATIVE`
* Additional notes:
  * Also makes it so every enchantment works on all items

## uncappedTridentSpeed
Makes it so that tridents can move just as fast as they usually do on the y-axis on the x & z axis
* Type: `boolean`
* Default value: `false`
* Required options: `true`,`false`
* Categories: `WEIRD`,`CREATIVE`,`EXPERIMENTAL`

## executeBlockLimit
Customizable execute volume limit
* Type: `int`
* Default value: `32768`
* Categories: `WEIRD`,`CREATIVE`
* Additional notes:
  * It's weird cause nobody knows this limit exists xD
  
## scaffoldingBreaking
Scaffolding breaking rules. Change how scaffolding breaks
* Type: `String`
* Default value: `break`
* Required options: `break`,`float`,`gravity`
* Categories: `WEIRD`,`CREATIVE`

## instantLiquidFlow
Instant-Liquid flow in 1.16, basically instant tile ticks but for liquids
* Type: `boolean`
* Default value: `false`
* Required options: `true`,`false`
* Categories: `WEIRD`,`CREATIVE`

## fastLiquidFlow
Makes all liquid ticks happen within the next tick
* Type: `boolean`
* Default value: `false`
* Required options: `true`,`false`
* Categories: `WEIRD`,`CREATIVE`

## fastTileTicks
Makes all tile ticks happen within the next tick
* Type: `boolean`
* Default value: `false`
* Required options: `true`,`false`
* Categories: `WEIRD`,`CREATIVE`

## instantTileTick
Re-implementing instant tile ticks into 1.16. Although it's more of an imitation
* Type: `boolean`
* Default value: `false`
* Required options: `true`,`false`
* Categories: `WEIRD`,`CREATIVE`

## instantTileTickMechanic
A game mechanic for instant tick ticks. Requires a lit redstone lamp to be moved in border chunks surrounded by Ticking Chunks
* Type: `boolean`
* Default value: `false`
* Required options: `true`,`false`,`crashFix`
* Categories: `WEIRD`
* Additional notes:
  * Also makes it so that instantTileTick crashes the server when generating new chunks

## instantFall
Re-implementing instant fall into 1.16, what could possibly go wrong 
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`,`false`  
* Categories: `WEIRD`,`CREATIVE`  
  
## instantFallMechanic
A game mechanic for instant fall. Requires a lit redstone lamp to be moved in border chunks surrounded by border chunks, this will turn on instantFall
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`,`false`
* Categories: `WEIRD`,`EXPERIMENTAL`
* Additional notes:  
  * Also makes it so that instantFall turns off when generating new chunks

## preventBreaking
Prevents all players from breaking blocks
* Type: `boolean`
* Default value: `false`
* Required options: `true`,`false`
* Categories: `WEIRD`,`CREATIVE`

## permaloader
This is a joke, it actually just prevents all chunks from being unloaded ;)
* Type: `boolean`
* Default value: `false`
* Required options: `true`,`false`
* Categories: `WEIRD`,`CREATIVE`,`EXPERIMENTAL`

## blueSkullTesting
Makes it so blue skulls from wither always spawn to test if your wither based farm will break
* Type: `String`
* Default value: `Vanilla`
* Required options: `Vanilla`,`Entity`,`Passive`,`All`
* Categories: `WEIRD`,`CREATIVE`,`EXPERIMENTAL`

## blockTransparency
Makes all the blocks in the game act like transparent blocks!
* Type: `String`
* Default value: `Vanilla`
* Required options: `Vanilla`,`Solid`,`Transparent`,`Inverse`
* Categories: `WEIRD`,`CREATIVE`,`EXPERIMENTAL`

## worldborderNotSpecial
Everything works the same past the world border
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `WEIRD`,`CREATIVE`,`EXPERIMENTAL`

## totallyLegitElytra
Elytra won't take damage from flight
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `WEIRD`,`EXPERIMENTAL`
* Additional notes:
    * Requested by Pixeils

## allowUnauthenticatedPlayers
Allow's players with invalid sessions to join the server, a lot like onlineMode
* Type: `boolean`
* Default value: `false`
* Required options: `false`,`true`
* Categories: `WEIRD`,`CREATIVE`,`EXPERIMENTAL`

# Chunk Legend
- `#ff0000` [Red] Inaccessible Chunks
- `#000000` [Black] Null Chunks
- `#555555` [DarkGray] Null Status
- `#aaaaaa` [Gray] Border Chunks
- `#55ff55` [Green] Ticking Chunks
- `#00aa00` [DarkGreen] Entity Processing Chunks
- `#aa00aa` [Purple] Chunk a player is in
- ◎ Chunk containing the redstone lamp
- ☻ Chunk containing your player

# /weird
/weird is the main command for the carpet extension. Currently, it only adds the chunk actions:
`/weird chunk <watch|set|radius|start|stop>`
 - chunk 
   - watch - Toggles on & off if you want to watch the chunk
   - set - Sets the chunk that should be watched
   - radius - Sets the amount of chunks that should be watched next to it
   - start - Starts displaying the chunks
   - stop - Stops displaying the chunks




