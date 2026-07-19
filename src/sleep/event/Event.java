package sleep.event;

import sleep.event.bonus.Bonus;
import sleep.event.bundle.Bundle;
import sleep.event.bundle.BundlePack;
import sleep.pokemon.SimplifiedPokemon;
import utilitaire.Util;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public record Event(String name, Date startDate, int weekDuration, List<Bonus> bonuses, List<SimplifiedPokemon> newPokemon,
                    List<SimplifiedPokemon> boostedPokemon, List<Mission> missions, List<BundlePack> bundles, URL linkToEvent) {

    public String getWikiCode()
    {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return """
        [[Fichier:%s Sleep.png|thumb|right|350px|Image promotionelle pour l'évènement dans {{Jeu|Sleep}}.]]
        '''%s''' est un évènement de {{jeu|Sleep}}.

        == Déroulement ==

        %s est un évènement qui s'est déroulé sur toutes les îles pendant %s. Chaque journée de l'évènement débute à 04:00, heure locale.

        %s%s%s%s== Lien externe ==
        * [%s Notice de l'évènement sur le site officiel] '''(fr)'''

        {{Évènements Pokémon Sleep|%d}}

        [[Catégorie:Évènement de Pokémon Sleep]]""".formatted(
                name,
                getNameWithArticle(),
                getNameWithArticle(),
                getDateSection(),
                getBonusSection(),
                getFullPokemonSection(),
                getMissionsSection(),
                getBundleSection(),
                linkToEvent,
                year
        );
    }

    private String getDateSection() {
        Date endDate = Util.getEndDate(startDate, weekDuration*7 - 1);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(startDate);
        c2.setTime(endDate);
        boolean sameMonth = c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH);

        String durationStr = switch (weekDuration) {
            case 1 -> "une";
            case 2 -> "deux";
            case 3 -> "trois";
            default -> "beaucoup";
        };

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM", Locale.FRENCH);

        String semaine = weekDuration == 1 ? " semaine" : " semaines";
        String from = sameMonth ? String.valueOf(c1.get(Calendar.DAY_OF_MONTH)) : formatter.format(startDate);

        return "%s %s, du %s au %s".formatted(durationStr, semaine, from, Util.dateToString(endDate));
    }

    private String getBonusSection() {
        if (bonuses.isEmpty()) {return "";}

        StringBuilder bonusSection = new StringBuilder("Comme les autres évènements du jeu, plusieurs bonus d'événement"
                + "ont été mis en place pendant la durée de la Semaine des compétences Pokémon en vedette 1. Ainsi :\n");

        for (Bonus bonus : bonuses) {
            bonusSection.append("\n* ").append(bonus.getBonusString()).append(" ;");
        }
        bonusSection.replace(bonusSection.length()-2, bonusSection.length(), ".\n\n");
        bonusSection.append("""
                Les bonus d'évènement ne s'appliquent uniquement que lors des sessions commencées \
                lors de la période de l'évènement. Ainsi, même si le rapport de sommeil est lu pendant l'évènement, \
                le joueur ne reçoit pas de bonus si la session a été débutée avant le début de celui-ci. Inversement, \
                si le rapport est lu après la fin de l'évènement, mais que la session a été commencée durant celui-ci, \
                alors le joueur reçoit les bonus d'évènements.
                
                Enfin, les bonus d'évènement ne s'appliquent pas lors de la session de sommeil enregistrée pendant le tutoriel du jeu.
                
                """);

        return bonusSection.toString();
    }

    private String getFullPokemonSection() {
        if (newPokemon.isEmpty() && boostedPokemon.isEmpty()) {return "";}
        String section = "== Pokémon à l'affiche ==\n\n";

        if (boostedPokemon.isEmpty()) {
            String adverb = newPokemon.size() > 1 ? "ont" : "a";
            return section + getNewPokemonString() + " au cours de l'évènement, et " + adverb
                    + " plus de chances d'apparaître lors d'une session de sommeil.\n\n" + getNewPokemonTable() + "\n\n";
        }

        if (newPokemon.isEmpty()) {
            return section + boostedPokemon.size() + "anciens Pokémon déjà introduits sont à l'affiche, et ont plus de chances d'apparaître lors d'une session de sommeil.\n\n"
                    + getReturningPokemonTable() + "\n\n";
        }

        return section + """
                Les Pokémon suivants sont à l'affiche durant l'évènement, et ont plus de chances d'apparaître lors d'une session de sommeil.
                
                === Nouveaux Pokémon ===
                
                %s au cours de l'évènement.
                
                %s
                
                === Anciens Pokémon ===
                
                Additionnellement, %d anciens Pokémon déjà introduits sont également à l'affiche.
                
                %s
                
                """.formatted(getNewPokemonString(), getNewPokemonTable(), boostedPokemon.size(), getReturningPokemonTable());
    }

    private String getMissionsSection() {
        if (missions.isEmpty()) return "";

        StringBuilder section = new StringBuilder("""
                == Missions à durée limitée ==
                
                Les missions suivantes permettent d'obtenir diverses récompenses. Elles peuvent toutes être complétées jusqu'à la fin de l'événement.
                
                {| class="tableaustandard centre"
                ! Mission
                ! Récompense""");
        for (Mission mission : missions) {
            section.append("\n|-\n").append(mission.getWikiCode());
        }
        section.append("\n|}\n\n");

        return section.toString();
    }

    private String getBundleSection() {
        if (bundles.isEmpty()) return "";

        StringBuilder section = new StringBuilder("""
                == Lots à durée limitée ==
                Ces lots peuvent être achetés dans la [[Boutique (Pokémon Sleep)|boutique]] uniquement durant la période de l'évènement.
                
                {| class="tableaustandard"
                ! Dates
                ! Lot
                ! Contenu
                ! Prix
                """);

        for (BundlePack bundle : bundles) {
            section.append("|-\n").append(bundle.getWikiCode());
        }
        section.append("|}\n\n");

        return section.toString();
    }

    private String getNewPokemonTable() {
        StringBuilder section = new StringBuilder("""
                {| class="tableaustandard sortable" style="text-align:center; white-space:nowrap"
                ! Pokémon
                ! Type
                ! Spécialité
                ! Compétence principale
                """);

        for (SimplifiedPokemon pokemon : newPokemon) {
            section.append("|-\n").append(pokemon.getNewWikiCode());
        }
        section.append("|}\n\n");

        String apparition = newPokemon.size() > 1 ? "les nouveaux Pokémon" : newPokemon.getFirst().getFullName();

        section.append("Ci-dessous, les rangs de dodoforce à atteindre avec Ronflex pour faire apparaître ")
                .append(apparition).append("""
                        lors d'une session de sommeil, pendant l'évènement.
                        
                        {| class="tableaustandard"
                        ! Nom
                        ! Type de dodo
                        ! Rareté du Style
                        ! Rang nécessaire
                        |-
                        ! colspan="4" | Pokémon « Plus fréquents »
                        |-""");
        for (SimplifiedPokemon pokemon : newPokemon) {
            section.append("\n").append(pokemon.getSleepData());
        }
        section.replace(section.length()-1, section.length(), "}");

        return section.toString();
    }

    private String getReturningPokemonTable() {
        StringBuilder section = new StringBuilder("""
                {| class="tableaustandard sortable" style="text-align:center; white-space:nowrap"
                        ! Pokémon
                        ! Type
                        ! Spécialité
                        ! Type de dodo
                        |-
                        ! colspan="6" | Pokémon « Un peu plus fréquents »""");

        for (SimplifiedPokemon pokemon : boostedPokemon) {
            section.append("\n|-").append(pokemon.getOldWikiCode());
        }
        section.append("\n|}");

        return section.toString();
    }

    private String getNewPokemonString() {
        if (newPokemon.isEmpty()) return "ERROR, NO NEW POKEMON";

        if (newPokemon.size() == 1) return newPokemon.getFirst().getFullName() + " est introduit";

        StringBuilder sb = new StringBuilder(newPokemon.getFirst().getFullName());
        for (int i  = 1; i < newPokemon.size()-1; i++) {
            sb.append(", ").append(newPokemon.get(i).getFullName());
        }
        sb.append(" et ").append(newPokemon.getLast().getFullName()).append(" sont introduits");

        return sb.toString();
    }

    private String getNameWithArticle() {
        return name.startsWith("Semaine") ? "La " + name : name;
    }
}
