package andyfryman.chinchopa.strategy;

import andyfryman.chinchopa.Compile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skadistats.clarity.model.CombatLogEntry;
import skadistats.clarity.processor.gameevents.OnCombatLogEntry;

public class GoldStrategy {

    private final Logger log = LoggerFactory.getLogger(GoldStrategy.class.getPackage().getClass());

    private static final Compile compile = new Compile();

    public GoldStrategy() {
        onStart();
    }

    public void onStart() {
        log.info("time,target,amount");
    }

    @OnCombatLogEntry
    public void onCombatLogEntry(CombatLogEntry cle) {
        switch (cle.getType()) {
            case DOTA_COMBATLOG_GOLD:
                log.info("{},{},{}",
                        compile.time(cle),
                        compile.target(cle),
                        cle.getValue()
                );
                break;
        }
    }

}