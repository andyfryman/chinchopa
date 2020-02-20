package andyfryman.chinchopa;

import org.joda.time.Duration;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skadistats.clarity.model.CombatLogEntry;
import skadistats.clarity.processor.gameevents.OnCombatLogEntry;
import skadistats.clarity.processor.runner.SimpleRunner;
import skadistats.clarity.source.MappedFileSource;
import skadistats.clarity.wire.common.proto.DotaUserMessages;

public class Main {

    private final Logger log = LoggerFactory.getLogger(Main.class.getPackage().getClass());

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

    private String compileName(String attackerName, boolean isIllusion) {
        attackerName = attackerName.replace("npc_dota_", "");
        return attackerName != null ? attackerName + (isIllusion ? " (illusion)" : "") : "UNKNOWN";
    }

    private String getAttackerNameCompiled(CombatLogEntry cle) {
        return compileName(cle.getAttackerName(), cle.isAttackerIllusion());
    }

    private String getTargetNameCompiled(CombatLogEntry cle) {
        return compileName(cle.getTargetName(), cle.isTargetIllusion());
    }

    @OnCombatLogEntry
    public void onCombatLogEntry(CombatLogEntry cle) {
        String time = GAMETIME_FORMATTER.print(Duration.millis((int) (1000.0f * cle.getTimestamp())).toPeriod());
        switch (cle.getType()) {
            case DOTA_COMBATLOG_DEATH:
                log.info("{},{},{}",
                    time,
                    getTargetNameCompiled(cle),
                    getAttackerNameCompiled(cle)
                );
                break;
        }
    }

    public void run(String[] args) throws Exception {
        log.info("time,victim,slayer");
        new SimpleRunner(new MappedFileSource(args[0])).runWith(this);
    }

    public static void main(String[] args) throws Exception {
        new Main().run(args);
    }

}
