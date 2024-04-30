package com.example.ipldashboard.data;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.springframework.batch.item.ItemProcessor;

import com.example.ipldashboard.model.Match;

public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm");

    @Override
    public Match process(MatchInput item) throws Exception {
        // item.setVisitDate(dateFormat.parse(item.getStrVisitDate()));
        Match match = new Match();
        match.setId(Long.parseLong(item.getId()));
        match.setCity(item.getCity());
        match.setDate(LocalDate.parse(item.getDate()));
        match.setPlayerOfMatch(item.getPlayer_of_match());
        match.setVenue(item.getVenue());

        String firstInningsTeam, secondInningsTeam;
        if (item.getToss_decision().equals("bat")) {
            firstInningsTeam = item.getToss_winner();
            secondInningsTeam = item.getToss_winner().equals(item.getTeam1()) ? item.getTeam2() : item.getTeam1();

        } else {
            firstInningsTeam = item.getToss_winner().equals(item.getTeam1()) ? item.getTeam2() : item.getTeam1();
            secondInningsTeam = item.getToss_winner();
        }
        match.setTeam1(firstInningsTeam);
        match.setTeam2(secondInningsTeam);
        match.setTossWinner(item.getToss_winner());
        match.setTossDecision(item.getToss_decision());
        match.setMatchWinner(item.getMatch_winner());
        match.setResult(item.getResult());
        match.setResultMargin(item.getResult_margin());
        match.setUmpire1(item.getUmpire1());
        match.setUmpire2(item.getUmpire2());

        return match;
    }
}
