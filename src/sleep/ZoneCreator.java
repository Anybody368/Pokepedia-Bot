package sleep;

import sleep.pokemon.SimplifiedPokemon;
import sleep.zone.Island;
import sleep.zone.NewZone;
import sleep.zone.PokemonOnZone;
import sleep.zone.ZoneRank;
import utilitaire.Login;
import utilitaire.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ZoneCreator {
    static void main(String[] args) {
        Login.handleLogin(args);
        NewZone newZone = makeNewZone(Island.EX_PLAGE_CYAN);

        Util.publishMultipleEdits(newZone.getAllPages());
    }

    private static NewZone makeNewZone(Island island) {
        Scanner sc = new Scanner(System.in);
        List<PokemonOnZone> pokemonList = makePokemonOnZone(sc);
        List<ZoneRank> zoneRanks = makeZoneRanks(sc, pokemonList);

        System.out.println("Puissance recommandée : ");
        int recommended = sc.nextInt();

        return new NewZone(island, recommended, zoneRanks, pokemonList);
    }

    private static List<PokemonOnZone> makePokemonOnZone(Scanner sc) {
        List<PokemonOnZone> pokemonList = new ArrayList<>();

        for (SimplifiedPokemon pokemon : UtilSleep.getAllPokemon()) {
            System.out.println("\n" + pokemon.getFullName() + " :");

            List<Rank> sleepRanks = new ArrayList<>();

            for (int i = 1; i <= 4; i++) {
                System.out.print("Rang du dodo " + i + " : ");

                String rankString = sc.nextLine();
                if (rankString.isBlank() || rankString.equals("n")) {
                    if (i == 1) break;
                    continue;
                }

                try {
                    Rank newRank = Rank.getRankFromName(rankString);
                    sleepRanks.add(newRank);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                    i --;
                }
            }

            if (sleepRanks.isEmpty()) continue;

            pokemonList.add(new PokemonOnZone(pokemon, sleepRanks));
        }

        return pokemonList;
    }

    private static List<ZoneRank> makeZoneRanks(Scanner sc, List<PokemonOnZone> pokemonList) {
        List<ZoneRank> zoneRanks = new ArrayList<>();
        int totalSleepCount = 0;

        for (Rank rank : Rank.values()) {
            System.out.println("\n" + rank.getPalier() + " :");

            int power, reward;
            if (rank == Rank.BASIC_1) {
                power = 0;
                reward = 0;
            } else {
                System.out.print("Puissance nécessaire : ");
                power = sc.nextInt();
                System.out.print("Récompense : ");
                reward = sc.nextInt();
            }

            int sleepCount = getSleepCountForRank(rank, pokemonList);
            totalSleepCount += sleepCount;

            zoneRanks.add(new ZoneRank(rank, power, reward, sleepCount, totalSleepCount));
        }
        return zoneRanks;
    }

    private static int getSleepCountForRank(Rank rank, List<PokemonOnZone> pokemonList) {
        int count = 0;
        for (PokemonOnZone pokemon : pokemonList) {
            for (Rank sleepRank : pokemon.sleepRanks()) {
                if (sleepRank == rank) count++;
            }
        }
        return count;
    }
}
