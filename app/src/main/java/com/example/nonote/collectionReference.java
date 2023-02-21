package com.example.nonote;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;

public class collectionReference {
    static CollectionReference getCollectionReference(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
         return FirebaseFirestore.getInstance().collection("notes")
                .document(currentUser.getUid()).collection("myNotes");
    }
}
