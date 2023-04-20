package fr.suward.network.accounts;

import com.badlogic.gdx.Gdx;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.suward.game.entities.Character;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Account {

    @JsonIgnore
    public static int amountOfAccounts = 0;
    private String accountName = "";
    private int accountId;
    private int accountExp = 0;
    private int wards = 0;

    private ArrayList<Character> characters = new ArrayList<>();

    public Account() {

    }

    public Account(String name) {
        accountId = amountOfAccounts;
        amountOfAccounts += 1;
        accountName = name;
    }


    public void addCharacter(Character c) {
        characters.add(c);
    }

    public void removeCharacter(Character c) {
        characters.remove(c);
    }

    public void removeCharacter(int index) {
        characters.remove(index);
    }

    public void save() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writeValue(new File("./core/assets/saves/" + accountName + ".json"), this);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void load(String accountName) {
        ObjectMapper objectMapper = new ObjectMapper();

        Account account = null;
        try {
            account = objectMapper.readValue(new File("./core/assets/saves/" + accountName + ".json"), Account.class);this.accountName = accountName;
            this.accountExp = account.getAccountExp();
            this.accountId = account.getAccountId();
            this.wards = account.getWards();
            this.characters = account.getCharacters();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    public int getWards() {
        return wards;
    }

    public void setWards(int wards) {
        this.wards = wards;
    }

    public int getAccountExp() {
        return accountExp;
    }

    public void setAccountExp(int accountExp) {
        this.accountExp = accountExp;
    }
}
