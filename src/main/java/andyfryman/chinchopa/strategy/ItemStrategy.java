package andyfryman.chinchopa.strategy;

import andyfryman.chinchopa.Compile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skadistats.clarity.model.CombatLogEntry;
import skadistats.clarity.processor.gameevents.OnCombatLogEntry;
import skadistats.clarity.wire.common.proto.DotaUserMessages;

public class ItemStrategy {

    private final Logger log = LoggerFactory.getLogger(ItemStrategy.class.getPackage().getClass());

    private static final Compile compile = new Compile();

    public ItemStrategy() {
        onStart();
    }

    public void onStart() {
        log.info("time,attacker,inflictor,target");
    }

    @OnCombatLogEntry
    public void onCombatLogEntry(CombatLogEntry cle) {
        switch (cle.getType()) {
            case DOTA_COMBATLOG_ITEM:
                log.info("{},{},{},{}",
                        compile.time(cle),
                        compile.attacker(cle),
                        compile.inflictor(cle),
                        cle.isTargetSelf() ? compile.attacker(cle) : compile.target(cle)
                );
                break;
        }
    }

}