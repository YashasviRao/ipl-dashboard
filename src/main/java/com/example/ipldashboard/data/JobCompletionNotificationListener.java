package com.example.ipldashboard.data;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.ipldashboard.model.Match;
import com.example.ipldashboard.model.Team;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final EntityManager em;

    private Map<String, Team> matchData = new HashMap<>();

    public JobCompletionNotificationListener(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            em.createQuery("select m.team1, count(*) from Match m group by m.team1", Object[].class)
                    .getResultStream()
                    .map(val -> new Team((String) val[0], (Long) val[1]))
                    .forEach(team -> matchData.put(team.getName(), team));

            em.createQuery("select m.team2, count(*) from Match m group by m.team2", Object[].class)
                    .getResultStream()
                    .forEach(obj -> {
                        String teamName = (String) obj[0];
                        Team currentTeam = matchData.get(teamName);
                        long count = currentTeam.getTotalMatches();
                        currentTeam.setTotalMatches(count + (Long) obj[1]);
                    });

            em.createQuery("select m.matchWinner, count(*) from Match m group by m.matchWinner", Object[].class)
                    .getResultStream()
                    .forEach(obj -> {
                        if (matchData.get((String) obj[0]) != null) {
                            matchData.get((String) obj[0]).setTotalWon((Long) obj[1]);
                        }
                    });

            matchData.forEach((teamName, team) -> System.out.println(team.toString()));

        }
    }
}
