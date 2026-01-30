# [CardListMaker](./CardListMaker.java)

This script generates the list of Bulbapedia articles for a given TCGP expansion, to be then used by [CardTranslation](./CardTranslation.java).

## Usage

This script takes an expansion name as only input, either as an execution argument, or in stdin during execution.
It should look like the name of the page on Bulbapedia without `(tcgp)`

For example, to get the list of [Space-Time Smackdown (TCG Pocket)](https://bulbapedia.bulbagarden.net/wiki/Space-Time_Smackdown_(TCG_Pocket)),
enter `Space-Time Smackdown`.

Once compiled, it is used like this:
```shell
java CardListMaker ["expansion name"]
```

## Output

This script will produce a text file at the project's root named `cardPages.txt` containing the names of every card Bulbapedia
pages for this expansion, in the following format:
```text
Oddish (Space-Time Smackdown 1)
Gloom (Space-Time Smackdown 2)
Bellossom (Space-Time Smackdown 3)
Tangela (Space-Time Smackdown 4)
Tangrowth (Space-Time Smackdown 5)
[...]
```

This file can be directly fed to the second script, or modified to add/remove pages.

# [CardTranslation](./CardTranslation.java)

This script creates Poképedia articles of TCGP cards mostly filled using data from the equivalent Bulbapedia articles.

## Usage

This script has one mandatory argument, being the path (absolute or relative) to a text file containing the name of the
Bulbapedia articles that need to be converted (see above for the format). Login and Password for your Poképedia account
may also be added afterwards, otherwise they'll be asked during execution. These won't be saved anywhere for security concerns.

Once compiled, it is used like this:
```shell
java CardTranslation <Path to text file> [Login] [Password]
```

### Preparation
It will then read every Bulbapedia articles one after the other, stocking every relevant data. if a Bulbapedia articles
doesn't exist yet, it will be ignored without stopping the execution.

During this phase, it may crash with an error message explaining what went wrong. 
This is usually cause by an error in the Bulbapedia article that should be corrected before attempting to convert this
article again. The error message should detail what element caused the issue for better troubleshooting.

Although the name of cards of Pokémon are automatically translated, Trainer cards will require the user to write the French
name of the card in stdin. The translation will be saved in a properties file so if the same card (or a different one
with the same name) is then converted, no input will be needed.

### Article creation
Once every Bulbapedia Article was successfully read, the user will be asked for confirmation in stdin before publishing
every translated articles to Poképedia. An error message will pop up in stderr if an upload fails, but the execution won't stop.

By default, if an article already exists on Poképedia, it won't be overwritten. This can be changed if needed.

## Maintenance

Because this script relies heavily on regular patterns and enumerations, the code needs to be updated regularly in order
to function properly, hopefully its conception should make things easier.

Unless said otherwise, the program will need to be recompiled after modifying it.

### New expansion
Whenever a new expansion comes out, all of its basic data should be added to [Expansion](./enums/Expansion.java) following
the same format as the others.

If the expansion contains multiple boosters, [Booster](./enums/Booster.java) should also be
updated.

### New type of card
If a new type of card is released with different relevant data or naming scheme is released, such as `-ex` cards, a new
class extending [CategoryStrategy](./category/CategoryStrategy.java), or one of its subclasses when relevant, should be
created in order to overwrite methods used to write the Poképedia article.

At the same time, the constructor of [Card](./card/Card.java) should be updated in order to account for the new category.

### New card rarity
If a new card rarity (such as "1 star" or "crown") is introduced, the [Rarity](./enums/Rarity.java) enumeration should be
updated, as well as the `fillRest` method of [Card](./card/Card.java).

### New Pokémon
Currently, all data regarding Pokémon is stored in [pokémonData.json](../../ressources/pokémonData.json),
which can either be manually updated, or using [GeneratePokeJSON](../utilitaire/GeneratePokeJSON.java).

### New Special Form
If a card mentions a special form using lowercase in its name, translations can be added in [PokeForm](enums/PokeForm.java).

### Editing translations
If you want to manually add translation for Trainer cards, or you need to update/remove some, you can directly modify the
[tcgpDico.properties](../../ressources/tcgpDico.properties) file. No need to recompile the program for this.

### Overwrite existing articles
If you need to go over a Poképedia article that already exists for whatever reason, all you need to do is change the
`SHOULD_OVERWRITE` boolean in [CardTranslation](./CardTranslation.java).