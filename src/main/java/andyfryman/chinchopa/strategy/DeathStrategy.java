package andyfryman.chinchopa.strategy;

import andyfryman.chinchopa.Compile;
import andyfryman.chinchopa.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skadistats.clarity.model.CombatLogEntry;
import skadistats.clarity.processor.gameevents.OnCombatLogEntry;
import skadistats.clarity.processor.runner.SimpleRunner;
import skadistats.clarity.source.MappedFileSource;
import skadistats.clarity.wire.common.proto.DotaUserMessages;

public class DeathStrategy {

    private final Logger log = LoggerFactory.getLogger(Main.class.getPackage().getClass());

    private static final Compile compile = new Compile();

    public DeathStrategy() {
        onStart();
    }

    public void onStart() {
        log.info("time,victim,slayer");
    }

    @OnCombatLogEntry
    public void onCombatLogEntry(CombatLogEntry cle) {
        switch (cle.getType()) {
            case DOTA_COMBATLOG_DEATH:
                log.info("{},{},{}",
                        compile.time(cle),
                        compile.target(cle),
                        compile.attacker(cle)
                );
                break;
        }
    }

}