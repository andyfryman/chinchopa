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

public class Death {
    private static final Compile compile = new Compile();

    public static final String Format  = "time,victim,slayer";

    public static final void Record(Logger log, CombatLogEntry cle) {
        log.info("{},{},{}",
                compile.time(cle),
                compile.target(cle),
                compile.attacker(cle)
        );
    }
}