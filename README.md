# WeirdAddons
fabric-carpet extension mod which adds a bunch of interesting but weird new features.

# Features
## crystalOverdose
End Crystals now explode when damaged from explosions. End Crystal chaining
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `WEIRD`,`CREATIVE` 

## observerDelay
Change delay length of observers
* Type: `int`  
* Default value: `2` 
* Categories: `WEIRD`,`CREATIVE`

## blockUpdateHell
Basically all setBlock calls will use this number
* Type: `int`  
* Default value: `-2` 
* Categories: `WEIRD`,`CREATIVE`
* Additional notes:
  * Custom block updates from: -1 to 127 | use -2 to disable

## spongeUpdate
Makes it so that sponges give block updates when absorbing water. Fixes Bug [MC-220636]
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `WEIRD`,`CREATIVE`, `BUGFIX`

## spongeEverything
Makes it so that sponges can absorb all blocks
* Type: `boolean`  
* Default value: `false` 
* Required options: `true`, `false`
* Categories: `WEIRD`,`CREATIVE`

## spongeLimit
Change the max amount of that a sponge can absorb
* Type: `int`
* Default value: `64`
* Categories: `WEIRD`,`CREATIVE`

## scaffoldingBreaking
Scaffolding breaking rules. Change how scaffolding breaks
* Type: `String`
* Default value: `break`
* Required options: `break`, `float`,`gravity`
* Categories: `WEIRD`,`CREATIVE`

## instantFall
Re-implementing instant fall into 1.16, what could possibly go wrong 
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `WEIRD`,`CREATIVE`  
  
## instantFallMechanic
A game mechanic for instant fall. Requires a lit redstone lamp to be moved in border chunks surrounded by border chunks, this will turn on instantFall
* Type: `boolean`  
* Default value: `false`  
* Required options: `true`, `false`  
* Categories: `WEIRD`,`CREATIVE`, `EXPERIMENTAL`
* Additional notes:  
  * Also makes it so that instantFall turns off when generating new chunks

## lampChunkStatus
Makes it so that a lamp on top of a barrier block when un-powered sends the status of the chunks around it
int is the radius of the map (Positive Integer). default 0 disables the feature
* Type: `int`  
* Default value: `0`
* Categories: `WEIRD`,`CREATIVE`

## lampChunkDisplay
Chunk map display method. Default is just a simple chat display
lamp: Holding a lamp will show a much larger map
* Type: `String`  
* Default value: `chat`  
* Required options: `chat`, `lamp`  
* Categories: `WEIRD`,`CREATIVE`, `CLIENT`
* Additional notes:  
  * This is just a hacky way for me to make a gui bigger when im doing testing. Not meant to be used for any serious projects.

# lampChunkStatus Legend
- `#ff0000` [Red] Inaccessible Chunks
- `#000000` [Black] Null Chunks
- `#555555` [DarkGray] Null Status
- `#aaaaaa` [Gray] Border Chunks
- `#55ff55` [Green] Ticking Chunks
- `#00aa00` [DarkGreen] Entity Processing Chunks
- `#aa00aa` [Purple] Chunk you're player is in
- ◎ Chunk containing the redstone lamp
- ☻ Chunk containing the player



