/*
 * Copyright (c) 2026 - Poképedia's contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package sleep;

import sleep.dodos.SleepRank;

import static sleep.Rank.RankCategory.*;

public enum Rank {
    BASIC_1(BASIC, 1),
    BASIC_2(BASIC, 2),
    BASIC_3(BASIC, 3),
    BASIC_4(BASIC, 4),
    BASIC_5(BASIC, 5),

    SUPER_1(SUPER, 1),
    SUPER_2(SUPER, 2),
    SUPER_3(SUPER, 3),
    SUPER_4(SUPER, 4),
    SUPER_5(SUPER, 5),

    HYPER_1(HYPER, 1),
    HYPER_2(HYPER, 2),
    HYPER_3(HYPER, 3),
    HYPER_4(HYPER, 4),
    HYPER_5(HYPER, 5),

    MASTER_1(MASTER, 1),
    MASTER_2(MASTER, 2),
    MASTER_3(MASTER, 3),
    MASTER_4(MASTER, 4),
    MASTER_5(MASTER, 5),
    MASTER_6(MASTER, 6),
    MASTER_7(MASTER, 7),
    MASTER_8(MASTER, 8),
    MASTER_9(MASTER, 9),
    MASTER_10(MASTER, 10),
    MASTER_11(MASTER, 11),
    MASTER_12(MASTER, 12),
    MASTER_13(MASTER, 13),
    MASTER_14(MASTER, 14),
    MASTER_15(MASTER, 15),
    MASTER_16(MASTER, 16),
    MASTER_17(MASTER, 17),
    MASTER_18(MASTER, 18),
    MASTER_19(MASTER, 19),
    MASTER_20(MASTER, 20),
    ;

    private final RankCategory category;
    private final int subRank;
    Rank(RankCategory category, int subRank) {
        this.category = category;
        this.subRank = subRank;
    }

    enum RankCategory {
        BASIC("Basique"),
        SUPER("Super"),
        HYPER("Hyper"),
        MASTER("Master");

        public final String name;
        RankCategory(String name) {
            this.name = name;
        }

        public static RankCategory getCategoryFromString(String name) {
            for (RankCategory categ : RankCategory.values()) {
                if (categ.name.equalsIgnoreCase(name)) return categ;
            }
            throw new IllegalArgumentException(name + " isn't an existing rank category");
        }
    }

    public static Rank getRankFromName(String fullRank) {
        RankCategory newRankCategory;
        int newRankLevel;
        if (fullRank.length() <= 3) {
            newRankCategory = switch (fullRank.toLowerCase().charAt(0)) {
                case 'b' -> BASIC;
                case 's' -> SUPER;
                case 'h' -> HYPER;
                case 'm' -> MASTER;
                default -> null;
            };
            if (newRankCategory == null) throw new IllegalArgumentException(fullRank + " isn't a valid short Rank");
            newRankLevel = Integer.parseInt(fullRank.substring(1));
        } else {
            String[] temp = fullRank.split(" ");
            newRankCategory = getCategoryFromString(temp[0]);
            newRankLevel = Integer.parseInt(temp[1]);
        }

        return getRank(newRankCategory, newRankLevel);
    }

    private static Rank getRank(RankCategory category, int subRank) {
        for (Rank rank : values()) {
            if (category == rank.category && subRank == rank.subRank) return rank;
        }
        throw new IllegalArgumentException(category.name() + " " + subRank + " isn't an existing rank");
    }

    public String getPalier() {
        return category.name + " " + subRank;
    }
    public String getBall() {
        return category.name;
    }

    public String getRankLine() {
        return "[[Fichier:Sprite Rang %s Sleep.png|30px]] %s".formatted(category.name, getPalier());
    }
}
