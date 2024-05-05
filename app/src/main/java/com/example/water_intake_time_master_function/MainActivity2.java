package com.example.water_intake_time_master_function;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.water_intake_time_master_function.fragments.DrawingFragment;
import com.example.water_intake_time_master_function.fragments.PomodoroFragment;
import com.example.water_intake_time_master_function.fragments.WaterFragment;
import com.example.water_intake_time_master_function.fragments.ToDoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity2 extends AppCompatActivity {

    private final Fragment selectedFragment = new ToDoFragment(); // Initialize selectedFragment to ToDoFragment initially

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if (item.getItemId() == R.id.ic_home) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fl_wrapper, new WaterFragment())
                                    .commit();
                        } else if (item.getItemId() == R.id.ic_favourite) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fl_wrapper, new PomodoroFragment())
                                    .commit();
                        } else if (item.getItemId() == R.id.ic_settings) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fl_wrapper, new ToDoFragment())
                                    .commit();
                        }
                        else if (item.getItemId() == R.id.ic_draw)
                        {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fl_wrapper, new DrawingFragment())
                                    .commit();
                        }
//                        else if (item.getItemId() == R.id.ic_rem)
//                        {
//                            getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.fl_wrapper, new ReminderPartFragment())
//                                    .commit();
//                        }

                        else if (item.getItemId() == R.id.ic_logout)
                        {
                            FirebaseAuth.getInstance().signOut(); // Log out the user
                            Intent logoutIntent = new Intent(getApplicationContext(), LOGIN.class);
                            startActivity(logoutIntent);
                            finish();
                            return true;
                        }

                        return true;
                    }
                });

        // Replace the initial fragment with ToDoFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_wrapper, selectedFragment)
                .commit();
    }
}
