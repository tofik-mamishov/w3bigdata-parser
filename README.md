# w3bigdata-parser
Java parser for *.w3g files.

## Definition and purpose.
The library parses w3g replay files and returns statitics and different KPIs. This will be an inner part of a set of tools for analysing, comparing and demonstrating W3 data. 

## Requirements.
The library must accept a w3g file and return human readable Statistics data. 
Statistics data must represent (but not limited to):
* File information: size, creation date, replay version.
* Map information: length of the game, name of the winner.
* Player's information for each player: color, race, APM, key bindings.

##WarCraft III Replay file format description

Description of format is here: http://w3g.deepnode.de/files/w3g_format.txt

Description of action blocs is here: http://w3g.deepnode.de/files/w3g_actions.txt

##New blocks
* 107(0x6B): 77 77 68 46 68 
* 116(0x74): 0 118
* 108(0x6C) 58 57 57 57 48 0 
                