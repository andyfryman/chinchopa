package andyfryman.chinchopa;

import org.joda.time.Duration;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import skadistats.clarity.model.CombatLogEntry;

public class Compile {

    private final PeriodFormatter GAMETIME_FORMATTER = new PeriodFormatterBuilder()
            .minimumPrintedDigits(2)
            .printZeroAlways()
            .appendHours()
            .appendLiteral(":")
            .appendMinutes()
            .appendLiteral(":")
            .appendSeconds()
            .appendLiteral(".")
            .appendMillis3Digit()
            .toFormatter();

    public String time(CombatLogEntry cle) {
        String time = GAMETIME_FORMATTER.print(Duration.millis((int) (1000.0f * cle.getTimestamp())).toPeriod());
        return time;
    }

    public String time(float time) {
        return GAMETIME_FORMATTER.print(Duration.millis((int) (1000.0f * time)).toPeriod());
    }

    public String name(String name, boolean isIllusion) {
        name = name.replace("npc_dota_", "").replace("dota_", "");
        return name != null ? name + (isIllusion ? " (illusion)" : "") : "UNKNOWN";
    }

    public String attacker(CombatLogEntry cle) {
        return name(cle.getAttackerName(), cle.isAttackerIllusion());
    }

    public String target(CombatLogEntry cle) {
        return name(cle.getTargetName(), cle.isTargetIllusion());
    }

    public String inflictor(CombatLogEntry cle) {
        return name(cle.getInflictorName() != null ? cle.getInflictorName() : "", cle.isAttackerIllusion());
    }

    public String damageSource(CombatLogEntry cle) {
        return name(cle.getDamageSourceName(), cle.isAttackerIllusion());
    }

    public String damageTarget(CombatLogEntry cle) {
        return name(cle.getTargetSourceName(), cle.isTargetIllusion());
    }

    public String team(int team) {
        switch (team) {
            case 2:
                return "Radiant";
            case 3:
                return "Dire";
            default:
                return "";
        }
    }

}