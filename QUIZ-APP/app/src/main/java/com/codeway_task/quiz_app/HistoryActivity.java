package com.codeway_task.quiz_app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.codeway_task.quiz_app.adapter.HistoryAdapter;
import com.codeway_task.quiz_app.data.Attempt;
import com.codeway_task.quiz_app.data.UserDatabase;
import com.codeway_task.quiz_app.data.UserDatabaseClient;
import com.codeway_task.quiz_app.data.UserWithAttempts;
import com.codeway_task.quiz_app.other.SharedPref;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView rvHistory;
    private List<UserWithAttempts> userWithAttempts;
    private TextView tvTotalPoints, tvTotalQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rvHistory = findViewById(R.id.rvHistory);
        tvTotalQuestions = findViewById(R.id.tvTotalQuestionsHistory);
        tvTotalPoints = findViewById(R.id.tvOverAllPointsHistory);

        findViewById(R.id.imageViewHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String email = SharedPref.getInstance().getUser(this).getEmail();
        GetAllUserAttemptTask getAllUserAttemptTask = new GetAllUserAttemptTask(email);
        getAllUserAttemptTask.execute();
    }


    class GetAllUserAttemptTask extends AsyncTask<Void, Void, Void> {

        private final String email;
        ArrayList<Attempt> attempts = new ArrayList<>();

        public GetAllUserAttemptTask(String email) {
            this.email = email;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            UserDatabase databaseClient = UserDatabaseClient.getInstance(getApplicationContext());
            attempts = (ArrayList<Attempt>) databaseClient.userDao().getUserAndAttemptsWithSameEmail(email);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            int overallPoints = 0;

            for (Attempt userWithAttempts : attempts) {
                overallPoints += userWithAttempts.getEarned();
            }

            tvTotalQuestions.setText(String.valueOf(attempts.size()));
            tvTotalPoints.setText(String.valueOf(overallPoints));

            Collections.sort(attempts, new AttemptCreatedTimeComparator());

            HistoryAdapter adapter = new HistoryAdapter(attempts);
            rvHistory.setAdapter(adapter);


        }
    }

    public class AttemptCreatedTimeComparator implements Comparator<Attempt> {

        @Override
        public int compare(Attempt attempt, Attempt t1) {
            return String.valueOf(t1.getCreatedTime()).compareTo(String.valueOf(attempt.getCreatedTime()));
        }
    }


}