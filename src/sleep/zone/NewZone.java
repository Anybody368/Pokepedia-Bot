/*
 * Copyright (c) 2026 - Poképedia's contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package sleep.zone;

import sleep.Rank;
import utilitaire.Page;
import utilitaire.PageToPublish;
import utilitaire.Util;
import utilitaire.Wiki;

import java.util.ArrayList;
import java.util.List;

public record NewZone(Island zone, int recommendedPS, List<ZoneRank> ranks, List<PokemonOnZone> pokemonList) {
    public List<PageToPublish> getAllPages() {
        List<PageToPublish> allPages = new ArrayList<>();
        allPages.add(getZonePage());
        //allPages.add(updateSleepListPage());
        allPages.addAll(updatePokemonPages());

        return allPages;
    }

    public PageToPublish getZonePage() {
        String content = """
            {{Édité par robot}}
            %s
            
            %s
            
            == Description ==
            
            %s
            
            == Caractéristiques de Ronflex ==
            
            %s
            
            == Liste des Pokémon disponibles ==
            
            %s
            
            == Imagerie ==
            
            %s""".formatted(getInfobox(), getIntroSection(), getDescriptionSection(), getSnorlaxSection(),getPokemonSection(), getImagerySection());

        return new PageToPublish(new Page(zone.getFullName(false), Wiki.POKEPEDIA), content, "Création automatique de la page à remplir");
    }

    private String getInfobox() {
        return """
                {{Infobox Lieu
                | nom=%s
                | nomen={{?}}
                | nomja={{?}}
                | nomtm={{?}}
                | image=Carte %s Sleep.png
                | desc-carte={{?}}
                }}""".formatted(zone.getFullName(false), zone.getFullName(true));
    }

    private String getIntroSection() {
        if (zone.isExpert()) {
            return """
                    %s'''%s''' est une zone visitable par le joueur dans {{Jeu|Sleep}} afin d'y conduire des recherches sur le sommeil.
                    
                    Il s'agit d'une version alternative de %s[[%s]], en [[mode expert]]. Celle-ci est débloquée après avoir atteint le rang Master 18 sur la version originale.
                    
                    Cette zone est accessible depuis le {{?}} avec la version {{?}} du jeu.""".formatted(
                            zone.getArticle(), zone.getFullName(false), zone.getArticle().toLowerCase(), zone.getName());
        }
        else {
            return "TODO";
        }
    }

    private String getDescriptionSection() {
        StringBuilder description = new StringBuilder();
        if (zone.isExpert()) {
            description.append("Le mode expert de ").append(zone.getArticle().toLowerCase()).append(zone.getName())
                    .append(" est une version spéciale de la {{?}} zone du jeu proposant une progression plus compliquée, mais offrant d'avantage de récompenses.");
        }
        else {
            description.append("TODO");
        }

        description.append("Le PS d'équipe recommandé est de %s ou plus. On peut y observer dormir %d Pokémon différents, pour un total de %s styles de dodo."
                .formatted(Util.numberDecomposition(recommendedPS), pokemonList.size(), Util.numberDecomposition(ranks.getLast().totalPokemon())));

        description.append("\n\n{{?}}");
        return description.toString();
    }

    private String getSnorlaxSection() {
        StringBuilder snorlaxSection = new StringBuilder("Sur ");

        snorlaxSection.append(zone.getArticle().toLowerCase()).append(zone.getFullName(false)).append("""
                , les caractéristiques des Ronflex sont les suivantes :
                
                {| class="tableaustandard centre" width="485px"
                ! Apparence<br>de Ronflex
                | colspan="3" | [[Fichier:Ronflex (%s) Sleep.png|250px]]
                |-
                ! {{nobr|Baies favorites}}
                | {{?}}
                | {{?}}
                | {{?}}
                |-
                ! Type Pokémon<br>associé
                | {{?}}
                | {{?}}
                | {{?}}
                |}
                
                Leurs rangs et leurs puissances évoluent ainsi :
                {| class="tableaustandard"
                ! Rang
                ! Puissance nécessaire
                ! colspan=2 | Styles de dodo disponibles
                ! Récompense
                """.formatted(zone.getName()));

        for (ZoneRank rank : ranks) {
            snorlaxSection.append(rank.getWikiCode()).append("\n");
        }

        snorlaxSection.append("|}");

        return snorlaxSection.toString();
    }

    private String getPokemonSection() {
        StringBuilder pokemonSection = new StringBuilder("""
                {| class="tableaustandard triable"
                ! Pokémon
                ! Type de dodo
                ! Rareté du Style
                ! Rang nécessaire
                """);

        for (PokemonOnZone pokemon : pokemonList) {
            pokemonSection.append(pokemon.getZoneCode()).append("\n");
        }

        pokemonSection.append("|}");

        return pokemonSection.toString();
    }

    private String getImagerySection() {
        String bonus = zone.isExpert() ? " en mode expert" : "";
        return """
            === Zone de recherche ===

            <center><gallery>
            %s Sleep.png|%s%s%s.
            </gallery></center>

            === Carte ===

            <center><gallery>
            Aperçu %s Sleep.png|Visuel de %s%s%s.
            </gallery></center>

            {{Pokémon Sleep}}

            [[Catégorie:Pokémon Sleep]]
            [[Catégorie:Lieu]]
            [[Catégorie:Lieu (jeu vidéo)]]""".formatted(zone.getFullName(true), zone.getArticle(), zone.getName(),
                bonus, zone.getFullName(true), zone.getArticle(), zone.getName(), bonus);
    }

    public PageToPublish updateSleepListPage() {
        Page sleepPage = new Page("Liste des styles de dodo de Pokémon Sleep", Wiki.POKEPEDIA);
        String content = sleepPage.getContent();
        String newContent = content;

        for (PokemonOnZone pokemon : pokemonList) {
            String pokemonID = pokemon.pokemon().getSleepListID();
            String oldSection = pokemonID + Util.searchValueOf(content, pokemonID, "}}", false);

            int insertLocation = -1;

            for (Island currentZone : Island.values()) {
                if (currentZone == zone) break;

                String zoneName = currentZone.getFullName(true);
                if (oldSection.contains(zoneName)) {
                    insertLocation = oldSection.indexOf(zoneName) + zoneName.length() + 2;
                }
            }

            if (insertLocation == -1) {
                System.err.println("Can't find valid location for " + pokemonID);
                continue;
            }

            String newSection = Util.insertIntoString(oldSection, "<br>[[%s]]".formatted(zone.getFullName(true)), insertLocation);
            newContent = newContent.replace(oldSection, newSection);
        }

        return new PageToPublish(sleepPage, newContent, "Ajout de " + zone.getFullName(true));
    }

    public List<PageToPublish> updatePokemonPages() {
        List<PageToPublish> pages = new ArrayList<>();

        for (PokemonOnZone pokemon : pokemonList) {
            String pokemonName = pokemon.pokemon().getFullName();
            if (pokemonName.contains(" forme ")) {
                System.err.println(pokemonName + " page needs to be updated manually");
                continue;
            }

            Page pokemonPage = new Page(pokemonName + "/Jeux secondaires", Wiki.POKEPEDIA);
            String content = pokemonPage.getContent();

            if (content.contains("[[%s]]".formatted(zone.getFullName(true)))) continue;

            System.out.println("Handling " + pokemonName);
            String sleepTable = Util.searchValueOf(content, "\" | Styles de dodos d", "|}", false);
            String newTable;

            if (zone.isExpert()) {
                String newContent = content.replace(sleepTable, handleExpertSection(sleepTable, pokemon));
                pages.add(new PageToPublish(pokemonPage, newContent, "Ajout de " + zone.getFullName(true)));
            } else {
                //TODO: Gestion de l'ajout d'une nouvelle zone classique
            }
        }

        return pages;
    }

    private String handleExpertSection(String oldSection, PokemonOnZone pokemon) {
        StringBuilder builder = new StringBuilder(oldSection);

        if (!oldSection.contains("Mode expert")) {
            int colspan = Integer.parseInt(Util.searchValueOf(oldSection, "colspan=\"", "\"", false));
            String bonbon = Util.searchValueOf(oldSection, "Sprite Bonbon ", " Sleep.", false);

            builder.append("""
                    |-
                    ! colspan="%d" | Mode expert
                    |-
                    ! Récompenses
                    """.formatted(colspan));

            builder.repeat("""
                    | style="white-space:nowrap; text-align:left" | [[Fichier:Sprite Point de recherche Sleep.png|30px]] Point de recherche × {{?}}\
                    <br>[[Fichier:Sprite Fragment de Rêve Sleep.png|30px]] [[Fragment de Rêve]] × {{?}}<br>\
                    [[Fichier:Sprite Bonbon %s Sleep.png|30px]] [[Bonbon (Pokémon Sleep)|Bonbon %s]] × {{?}}
                    """.formatted(bonbon, bonbon), colspan - 1);
        }

        builder.append(getSleepTableAddition(pokemon)).append("\n");
        return builder.toString();
    }

    private String getSleepTableAddition(PokemonOnZone pokemon) {
        StringBuilder sleepTableAddition = new StringBuilder("|-\n! [[%s]]".formatted(zone.getFullName(true)));
        for (Rank rank : pokemon.sleepRanks()) {
            sleepTableAddition.append("\n| ").append(rank.getRankLine());
        }
        return sleepTableAddition.toString();
    }
}
