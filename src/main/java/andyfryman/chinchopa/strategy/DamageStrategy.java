package andyfryman.chinchopa.strategy;

import andyfryman.chinchopa.Compile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skadistats.clarity.model.CombatLogEntry;
import skadistats.clarity.processor.gameevents.OnCombatLogEntry;

public class DamageStrategy {

    private final Logger log = LoggerFactory.getLogger(DamageStrategy.class.getPackage().getClass());

    private static final Compile compile = new Compile();

    public DamageStrategy() {
        onStart();
    }

    public void onStart() {
        log.info("time,attacker,damageSource,target,damageTarget,by,value,from,to");
    }

    @OnCombatLogEntry
    public void onCombatLogEntry(CombatLogEntry cle) {
        switch (cle.getType()) {
            case DOTA_COMBATLOG_DAMAGE:
                log.info("{},{},{},{},{},{},{},{},{}",
                        compile.time(cle),
                        compile.attacker(cle),
                        compile.damageSource(cle),
                        compile.target(cle),
                        compile.damageTarget(cle),
                        compile.inflictor(cle),
                        cle.getValue(),
                        cle.getHealth() + cle.getValue(),
                        cle.getHealth()
                );
                break;
        }
    }

}