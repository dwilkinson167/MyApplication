package com.example.myapplication;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.function.Consumer;

public class MatchesViewModel {

    private final MatchesDataModel dataModel;

    public MatchesViewModel() {
        dataModel = new MatchesDataModel();
    }

    public void addMatch(Match match) {
        dataModel.addMatch(match);
    }

    public void getMatches(Consumer<ArrayList<Match>> responseCallback) {
        dataModel.getMatches(
                (QuerySnapshot querySnapshot) -> {
                    if(querySnapshot != null) {
                        ArrayList<Match> matches = new ArrayList<>();
                        for (DocumentSnapshot matchSnapshot : querySnapshot.getDocuments()) {
                            Match match = matchSnapshot.toObject(Match.class);
                            assert match != null;
                            match.setUid(matchSnapshot.getId());
                            matches.add(match);
                        }
                        responseCallback.accept(matches);
                    }
                }, (databaseError -> System.out.println("Error reading matches: " + databaseError))
        );
    }

    public void updateMatch(Match match) {
        dataModel.updateMatchById(match);
    }

    public void clear() {
        dataModel.clear();
    }
}
