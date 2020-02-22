package andyfryman.chinchopa.strategy;

import andyfryman.chinchopa.Compile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skadistats.clarity.model.CombatLogEntry;
import skadistats.clarity.processor.gameevents.OnCombatLogEntry;
import skadistats.clarity.wire.common.proto.DotaUserMessages;

public class DebugStrategy {

    private final Logger log = LoggerFactory.getLogger(DebugStrategy.class.getPackage().getClass());

    private static final Compile compile = new Compile();

    public DebugStrategy() {
        onStart();
    }

    public void onStart() {
    }

    @OnCombatLogEntry
    public void onCombatLogEntry(CombatLogEntry cle) {
        DotaUserMessages.DOTA_COMBATLOG_TYPES type = cle.getType();
        String message = cle.toString().replace('\n', ' ');
        log.info("{}", message);
    }

}