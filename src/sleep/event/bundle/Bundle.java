package sleep.event.bundle;

import sleep.event.ItemReward;

import java.util.List;

public record Bundle(String name, int price, int limit, List<ItemReward> items) {
}
