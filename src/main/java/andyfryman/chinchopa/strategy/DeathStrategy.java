package andyfryman.chinchopa.strategy;

import andyfryman.chinchopa.Compile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skadistats.clarity.model.CombatLogEntry;
import skadistats.clarity.processor.gameevents.OnCombatLogEntry;

public class DeathStrategy {

    private final Logger log = LoggerFactory.getLogger(DeathStrategy.class.getPackage().getClass());

    private static final Compile compile = new Compile();

    public DeathStrategy() {
        onStart();
    }

    public void onStart() {
        log.info("time,target,attacker");
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