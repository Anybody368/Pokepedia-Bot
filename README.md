# Pokepedia-Bot
This is a compilation of Java applications made to help automate tedious updates made regularly on [Pokepedia](https://www.pokepedia.fr).
Credits to [Mewtwo-Ex](https://github.com/Mewtwo-Ex) and [GaletteLithium](https://github.com/GaletteLithium) for the API communication, everything else was made by me.

This is being developped using IntelliJ Idea, with openjdk-23 (soon to be updated to openjdk-25 in order to use an LTS version),
although it should be fully compatible with Java 21 and *potentially* Java 17.

## TCG Pocket
More details in the [TCGP directory](./src/tcgp).

The main program here, [CardTranslation](./src/tcgp/CardTranslation.java), aims to use Bulbapedia articles of cards in TCG Pocket to create equivalent articles in Poképedia.
Some data still needs to be manually filled afterwards.

A list of the Bulbapedia articles that needs to be converted must be written first.
This list can be automatically generated using [CardListMaker](./src/tcgp/CardListMaker.java), providing the expansion name.

## Pokémon Sleep
More details in the [Pokémon Sleep directory](./src/sleep) (soon).

This section's script, [SleepApp](./src/sleep/SleepApp.java) aims to add to Poképedia data regarding Pokémon newly added to the game.
For regular Pokémon, this can be done through a UI.
For special forms (regional variants or holiday forms for example), commented code can be used.