package com.techvipin130524.quizgame;

import static com.techvipin130524.quizgame.util.Const.SCORE_CORRECT;
import static com.techvipin130524.quizgame.util.Const.SCORE_TABLE;
import static com.techvipin130524.quizgame.util.Const.SCORE_WRONG;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Score_Page extends AppCompatActivity {
    Button playAgain, exit;
    TextView scoreCorrect, scoreWrong;

    String userCorrect, userWrong;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference().child(SCORE_TABLE);

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        playAgain = findViewById(R.id.buttonPlayAgain);
        exit = findViewById(R.id.buttonExit);
        scoreCorrect = findViewById(R.id.textViewCorrectAnswer);
        scoreWrong = findViewById(R.id.textViewWrongAnswer);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userUID = user.getUid();
                userCorrect = snapshot.child(userUID).child(SCORE_CORRECT).getValue().toString();
                userWrong = snapshot.child(userUID).child(SCORE_WRONG).getValue().toString();

                scoreCorrect.setText(userCorrect);
                scoreWrong.setText(userWrong);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Score_Page.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}