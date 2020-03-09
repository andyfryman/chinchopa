package andyfryman.chinchopa.strategy;

import andyfryman.chinchopa.Compile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skadistats.clarity.Clarity;
import skadistats.clarity.decoder.Util;
import skadistats.clarity.model.CombatLogEntry;
import skadistats.clarity.model.Entity;
import skadistats.clarity.model.FieldPath;
import skadistats.clarity.processor.entities.Entities;
import skadistats.clarity.processor.gameevents.OnCombatLogEntry;

import skadistats.clarity.decoder.Util;
import skadistats.clarity.model.EngineId;
import skadistats.clarity.model.Entity;
import skadistats.clarity.model.FieldPath;
import skadistats.clarity.processor.entities.Entities;
import skadistats.clarity.processor.entities.UsesEntities;
import skadistats.clarity.processor.runner.ControllableRunner;
import skadistats.clarity.source.MappedFileSource;
import skadistats.clarity.util.TextTable;
import java.io.IOException;
import skadistats.clarity.Clarity;
import skadistats.clarity.wire.common.proto.Demo;
import skadistats.clarity.wire.common.proto.Demo.CDemoFileInfo;

@UsesEntities
public class StatsStrategy {

    private final Logger log = LoggerFactory.getLogger(StatsStrategy.class.getPackage().getClass());

    private static final Compile compile = new Compile();

    private final ControllableRunner runner;
    private final CDemoFileInfo info;

    public StatsStrategy(ControllableRunner runner, String file) throws IOException {
        onStart();
        this.runner = runner;
        this.info = Clarity.infoForFile(file);
    }

    public void onStart() {

    }

    public void onEnd() {
        log.info("name,team,level,experience,gold,lasthit,deny,kill,death,assist,time,match,won");
        showTableWithColumns(
                new DefaultResolver<Integer>("PlayerResource", "m_vecPlayerData.%i.m_iPlayerTeam"),
                new DefaultResolver<String>("PlayerResource", "m_vecPlayerData.%i.m_iszPlayerName"),
                new DefaultResolver<Integer>("PlayerResource", "m_vecPlayerData.%i.m_iPlayerTeam"),
                new DefaultResolver<Integer>("PlayerResource", "m_vecPlayerTeamData.%i.m_iLevel"),
                new DefaultResolver<Integer>("Data%n", "m_vecDataTeam.%p.m_iTotalEarnedXP"),
                new DefaultResolver<Integer>("Data%n", "m_vecDataTeam.%p.m_iTotalEarnedGold"),
                new DefaultResolver<Integer>("Data%n", "m_vecDataTeam.%p.m_iLastHitCount"),
                new DefaultResolver<Integer>("Data%n", "m_vecDataTeam.%p.m_iDenyCount"),
                new DefaultResolver<Integer>("PlayerResource", "m_vecPlayerTeamData.%i.m_iKills"),
                new DefaultResolver<Integer>("PlayerResource", "m_vecPlayerTeamData.%i.m_iDeaths"),
                new DefaultResolver<Integer>("PlayerResource", "m_vecPlayerTeamData.%i.m_iAssists"),
                new DefaultResolver<Integer>("PlayerResource", "m_vecPlayerData.%i.m_iPlayerTeam"),
                new DefaultResolver<Integer>("PlayerResource", "m_vecPlayerData.%i.m_iPlayerTeam"),
                new DefaultResolver<Integer>("PlayerResource", "m_vecPlayerData.%i.m_iPlayerTeam")
        );
    }

    private void showTableWithColumns(ValueResolver<Integer> teamResolver, ValueResolver<?>... columnDefs) {
        int team = 0;
        int pos = 0;
        int r = 0;

        Demo.CGameInfo game = info.getGameInfo();
        Demo.CGameInfo.CDotaGameInfo dota = game.getDota();
        Integer winner = dota.getGameWinner();
        long id = dota.getMatchId();
        float time = info.getPlaybackTime();


        for (int idx = 0; idx < 256; idx++) {
            try {
                int newTeam = teamResolver.resolveValue(idx, team, pos);
                if (newTeam != team) {
                    team = newTeam;
                    pos = 0;
                } else {
                    pos++;
                }
            } catch (Exception e) {
                // when the team resolver throws an exception, this was the last index there was
                break;
            }
            if (team != 2 && team != 3) {
                continue;
            }

            String line = "";
            for (int c = 0; c < columnDefs.length; c++) {
                String value = "\"" + columnDefs[c].resolveValue(idx, team, pos).toString() + "\"";
                if (c == 1) {
                    value = compile.team((Integer) columnDefs[c].resolveValue(idx, team, pos));
                }

                if (c == columnDefs.length - 3) {
                    value = compile.time(time);
                }

                if (c == columnDefs.length - 2) {
                    value = String.valueOf(id);
                }

                if (c == columnDefs.length - 1) {
                    value = columnDefs[c].resolveValue(idx, team, pos).toString().equalsIgnoreCase(winner.toString()) ? "1" : "0";
                }

                line += value;
                if (c < columnDefs.length - 1) line += ",";
            }
            log.info(line);
            r++;
        }
    }


    private String getEngineDependentEntityName(String entityName) {
        switch (runner.getEngineType().getId()) {
            case SOURCE1:
                return "DT_DOTA_" + entityName;
            case SOURCE2:
                return "CDOTA_" + entityName;
            default:
                throw new RuntimeException("invalid engine type");
        }
    }

    private Entity getEntity(String entityName) {
        return runner.getContext().getProcessor(Entities.class).getByDtName(getEngineDependentEntityName(entityName));
    }

    private interface ValueResolver<V> {
        V resolveValue(int index, int team, int pos);
    }

    private class DefaultResolver<V> implements ValueResolver<V> {
        private final String entityName;
        private final String pattern;

        public DefaultResolver(String entityName, String pattern) {
            this.entityName = entityName;
            this.pattern = pattern;
        }

        @Override
        public V resolveValue(int index, int team, int pos) {
            String fieldPathString = pattern
                    .replaceAll("%i", Util.arrayIdxToString(index))
                    .replaceAll("%t", Util.arrayIdxToString(team))
                    .replaceAll("%p", Util.arrayIdxToString(pos));
            String compiledName = entityName.replaceAll("%n", compile.team(team));
            Entity entity = getEntity(compiledName);
            FieldPath fieldPath = entity.getDtClass().getFieldPathForName(fieldPathString);
            return entity.getPropertyForFieldPath(fieldPath);
        }
    }

}

