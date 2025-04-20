package com.example.lutemon;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.*;

public class BattleActivity extends AppCompatActivity {

    private Lutemon fighterA, fighterB;
    private TextView txtLog, txtNameA, txtNameB, txtStatsA, txtStatsB;
    private ImageView imgA, imgB;
    private Button btnNext, btnEnd;

    private boolean isBattleOver = false;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        txtLog = findViewById(R.id.txtBattleLog);
        txtLog.setMovementMethod(new android.text.method.ScrollingMovementMethod());
        txtNameA = findViewById(R.id.txtNameA);
        txtNameB = findViewById(R.id.txtNameB);
        txtStatsA = findViewById(R.id.txtStatsA);
        txtStatsB = findViewById(R.id.txtStatsB);
        imgA = findViewById(R.id.imgA);
        imgB = findViewById(R.id.imgB);
        btnNext = findViewById(R.id.btnNextAttack);
        btnEnd = findViewById(R.id.btnEndBattle);

        btnNext.setOnClickListener(v -> {
            if (!isBattleOver && fighterA != null && fighterB != null) {
                nextRound();
            }
        });

        btnEnd.setOnClickListener(v -> finish());

        loadFighters(); // main setup
    }

    private void loadFighters() {
        ArrayList<Lutemon> list = new ArrayList<>(Storage.getInstance().getBattleLutemons().values());

        if (list.size() < 2) {
            txtLog.setText("⚠ Not enough Lutemons in the Battle Area.\nPlease send at least 2 to start a battle.");
            btnNext.setEnabled(false);
            fighterA = null;
            fighterB = null;
            return;
        }

        fighterA = list.get(0);
        fighterB = list.get(1);
        updateUI();
    }

    private void nextRound() {
        StringBuilder roundLog = new StringBuilder();

        // A attacks B
        roundLog.append(attack(fighterA, fighterB));

        if (!fighterB.isAlive()) {
            roundLog.append("\n").append(fighterB.getName()).append(" is defeated!");
            endBattle(fighterA, fighterB, roundLog);
            return;
        }

        // B attacks A
        roundLog.append("\n").append(attack(fighterB, fighterA));

        if (!fighterA.isAlive()) {
            roundLog.append("\n").append(fighterA.getName()).append(" is defeated!");
            endBattle(fighterB, fighterA, roundLog);
            return;
        }
        txtLog.post(() -> {
            final int scrollAmount = txtLog.getLayout().getLineTop(txtLog.getLineCount()) - txtLog.getHeight();
            if (scrollAmount > 0)
                txtLog.scrollTo(0, scrollAmount);
            else
                txtLog.scrollTo(0, 0);
        });
        txtLog.append("\n\n" + roundLog.toString());
        updateUI();
    }


    private String attack(Lutemon attacker, Lutemon defender) {
        boolean isCrit = random.nextFloat() < 0.2f;
        int baseDamage = attacker.getTotalAttack() - defender.getDefense();
        int damage = Math.max(baseDamage, 0);
        if (isCrit) damage *= 2;

        defender.takeDamage(damage);

        return attacker.getName() +
                (isCrit ? " lands a CRITICAL HIT! " : " attacks ") +
                defender.getName() + " for " + damage + " damage.\n" +
                defender.getName() + " HP: " + defender.getCurrentHealth() + "/" + defender.getMaxHealth();
    }

    private void endBattle(Lutemon winner, Lutemon loser, StringBuilder finalLog) {
        winner.addWin();
        winner.gainExperience();
        loser.addLoss();
        winner.resetHealth();
        loser.resetHealth();

        finalLog.append("\n\n").append(winner.getName()).append(" wins! +1 XP");
        txtLog.append("\n\n" + finalLog.toString());
        Storage.getInstance().returnToHome(winner.getId());
        Storage.getInstance().returnToHome(loser.getId());

        updateUI();
        isBattleOver = true;
    }

    private void updateUI() {
        if (fighterA == null || fighterB == null) return;

        txtNameA.setText(fighterA.getName() + " (" + fighterA.getColorName() + ")");
        txtNameB.setText(fighterB.getName() + " (" + fighterB.getColorName() + ")");
        txtStatsA.setText("HP: " + fighterA.getCurrentHealth() + "/" + fighterA.getMaxHealth() +
                " | ATK: " + fighterA.getTotalAttack() +
                " | DEF: " + fighterA.getDefense() +
                " | EXP: " + fighterA.getExperience());
        txtStatsB.setText("HP: " + fighterB.getCurrentHealth() + "/" + fighterB.getMaxHealth() +
                " | ATK: " + fighterB.getTotalAttack() +
                " | DEF: " + fighterB.getDefense() +
                " | EXP: " + fighterB.getExperience());

        imgA.setImageResource(getImageForColor(fighterA.getColor()));
        imgB.setImageResource(getImageForColor(fighterB.getColor()));
    }

    private int getImageForColor(LutemonColor color) {
        switch (color) {
            case WHITE: return R.drawable.lutemon_white;
            case GREEN: return R.drawable.lutemon_green;
            case PINK: return R.drawable.lutemon_pink;
            case ORANGE: return R.drawable.lutemon_orange;
            case BLACK: return R.drawable.lutemon_black;
            default: return R.drawable.lutemon_white;
        }
    }
}