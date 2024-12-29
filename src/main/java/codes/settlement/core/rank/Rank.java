package codes.settlement.core.rank;

import codes.settlement.core.util.Utils;

public enum Rank {
    OWNER("&cOWNER", "&cOWNER", "&f", "&c", 5),
    ADMIN("&cADMIN", "&CADMIN", "&f", "&c", 4),
    MOD("&2MOD", "&2MOD", "&f", "&2", 3),
    HELPER("&9HELPER", "&9HELPER", "&f", "&9", 2),
    VIP("&aVIP", "&aVIP", "&f", "&a", 1),
    DEFAULT("&7", "&7None", "&f", "&7", 0);

    private String name;
    private String scoreboardName;
    private String chatColor;
    private String color;
    private Integer rankId;

    Rank(String name, String scoreboardName, String chatColor, String color, int rankId) {
        this.name = Utils.color(name);
        this.scoreboardName = Utils.color(scoreboardName);
        this.chatColor = Utils.color(chatColor);
        this.color = Utils.color(color);
        this.rankId = rankId;
    }

    public String getPrefix(Rank rank) {
        return rank.color + "[" + rank.name + "]";
    }

    public String getName(Rank rank) {
        return rank.name;
    }

    public String getScoreboardName(Rank rank) {
        return rank.scoreboardName;
    }

    public String getChatColor(Rank rank) {
        return rank.chatColor;
    }

    public Integer getRankId(Rank rank) {
        return rank.rankId;
    }

}
