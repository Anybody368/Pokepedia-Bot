package sleep.event;

import sleep.event.bonus.Bonus;
import sleep.event.bundle.BundlePack;
import sleep.pokemon.SimplifiedPokemon;

import java.util.Date;
import java.util.List;

public record Event(String name, Date startDate, int weekDuration, List<Bonus> bonuses, List<SimplifiedPokemon> newPokemon,
                    List<SimplifiedPokemon> boostedPokemon, List<Mission> missions, List<BundlePack> bundles) {
}
