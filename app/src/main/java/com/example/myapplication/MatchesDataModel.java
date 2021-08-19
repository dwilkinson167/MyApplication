package com.example.myapplication;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MatchesDataModel {

    private FirebaseFirestore db;
    private List<ListenerRegistration> listeners;

    public MatchesDataModel() {
        db = FirebaseFirestore.getInstance();
        listeners = new ArrayList<>();
    }

    public void addMatch(Match match) {
        CollectionReference matchesRef = db.collection("matches");
        matchesRef.add(match);
    }

    public void getMatches(Consumer<QuerySnapshot> dataChangedCallback, Consumer<FirebaseFirestoreException> dataErrorCallback) {
        ListenerRegistration listener = db.collection("matches")
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if(e != null) {
                        dataErrorCallback.accept(e);
                    }
                    dataChangedCallback.accept(queryDocumentSnapshots);
                });
        listeners.add(listener);
    }

    public void updateMatchById(Match match) {
        DocumentReference matchRef = db.collection("matches").document(match.getUid());
        Map<String, Object> data = new HashMap<>();
        data.put("imageUrl", match.getImageUrl());
        data.put("lat", match.getLat());
        data.put("liked", match.getIsLiked());
        data.put("longitude", match.getLongitude());
        data.put("name", match.getName());
        data.put("uid", match.getUid());
        matchRef.update(data);
    }

    public void clear() {
        // Clears all the listeners onPause
        listeners.forEach(ListenerRegistration::remove);
    }
}
