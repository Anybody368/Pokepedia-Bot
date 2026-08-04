package sleep.dodos;

public record SleepRank(
        Ball rankBall,
        int rankLevel
) {
    enum Ball {
        BASIC("Basique"),
        SUPER("Super"),
        HYPER("Hyper"),
        MASTER("Master");

        private final String name;
        Ball(String name) {
            this.name = name;
        }

        public static Ball getBallFromString(String name) {
            for (Ball ball : Ball.values()) {
                if (ball.name.equals(name)) return ball;
            }
            throw new IllegalArgumentException(name + " isn't an existing rank");
        }
    }
    public SleepRank(String fullRank) {
        Ball newRankBall;
        int newRankLevel;
        if (fullRank.length() <= 3) {
            newRankBall = switch (fullRank.charAt(0)) {
                case 'b' -> Ball.BASIC;
                case 's' -> Ball.SUPER;
                case 'h' -> Ball.HYPER;
                case 'm' -> Ball.MASTER;
                default -> null;
            };
            newRankLevel = Integer.parseInt(fullRank.substring(1));
        } else {
            String[] temp = fullRank.split(" ");
            newRankBall = Ball.getBallFromString(temp[0]);
            newRankLevel = Integer.parseInt(temp[1]);
        }
        this(newRankBall, newRankLevel);
    }

    public String getPalier() {
        return rankBall.name + " " + rankLevel;
    }
    public String getBall() {return rankBall.name;}
}
