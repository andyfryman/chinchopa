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

    public String name(String attackerName, boolean isIllusion) {
        attackerName = attackerName.replace("npc_dota_", "");
        return attackerName != null ? attackerName + (isIllusion ? " (illusion)" : "") : "UNKNOWN";
    }

    public String attacker(CombatLogEntry cle) {
        return name(cle.getAttackerName(), cle.isAttackerIllusion());
    }

    public String target(CombatLogEntry cle) {
        return name(cle.getTargetName(), cle.isTargetIllusion());
    }

}