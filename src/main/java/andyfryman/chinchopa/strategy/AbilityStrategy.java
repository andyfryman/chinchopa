package andyfryman.chinchopa.strategy;

import andyfryman.chinchopa.Compile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skadistats.clarity.model.CombatLogEntry;
import skadistats.clarity.processor.gameevents.OnCombatLogEntry;

public class AbilityStrategy {

    private final Logger log = LoggerFactory.getLogger(AbilityStrategy.class.getPackage().getClass());

    private static final Compile compile = new Compile();

    public AbilityStrategy() {
        onStart();
    }

    public void onStart() {
        log.info("time,attacker,ability,toggle,level,target");
    }

    @OnCombatLogEntry
    public void onCombatLogEntry(CombatLogEntry cle) {
        switch (cle.getType()) {
            case DOTA_COMBATLOG_ABILITY:
                log.info("{},{},{},{},{},{}",
                        compile.time(cle),
                        compile.attacker(cle),
                        compile.inflictor(cle),
                        cle.isAbilityToggleOn() ? "on" : cle.isAbilityToggleOff() ? "off" : "cast",
                        cle.getAbilityLevel(),
                        cle.isTargetSelf() ? compile.attacker(cle) : compile.target(cle)
                );
                break;
        }
    }

}