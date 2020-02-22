package andyfryman.chinchopa.strategy;

import andyfryman.chinchopa.Compile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skadistats.clarity.model.CombatLogEntry;
import skadistats.clarity.processor.gameevents.OnCombatLogEntry;

public class PurchaseStrategy {

    private final Logger log = LoggerFactory.getLogger(PurchaseStrategy.class.getPackage().getClass());

    private static final Compile compile = new Compile();

    public PurchaseStrategy() {
        onStart();
    }

    public void onStart() {
        log.info("time,target,item");
    }

    @OnCombatLogEntry
    public void onCombatLogEntry(CombatLogEntry cle) {
        switch (cle.getType()) {
            case DOTA_COMBATLOG_PURCHASE:
                log.info("{},{},{}",
                        compile.time(cle),
                        compile.target(cle),
                        cle.getValueName()
                );
                break;
        }
    }

}