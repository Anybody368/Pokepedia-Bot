package sleep.dodos;

public record SleepRank(
        String rankBall,
        int rankLevel
) {
    public SleepRank(String fullRank) {
        String newRankBall;
        int newRankLevel;
        if (fullRank.length() <= 3) {
            newRankBall = switch (fullRank.charAt(0)) {
                case 'b' -> "Basic";
                case 's' -> "Super";
                case 'h' -> "Hyper";
                case 'm' -> "Master";
                default -> "error";
            };
            newRankLevel = Integer.parseInt(fullRank.substring(1));
        } else {
            String[] temp = fullRank.split(" ");
            newRankBall = temp[0];
            newRankLevel = Integer.parseInt(temp[1]);
        }
        this(newRankBall, newRankLevel);
    }

    public String getPalier() {
        return rankBall + " " + rankLevel;
    }
}
