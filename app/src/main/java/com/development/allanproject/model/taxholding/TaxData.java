package com.development.allanproject.model.taxholding;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class TaxData {
    private int id;
    private String question;
    private ArrayList<RadioSelectionOption> radio;
    private ArrayList<InputTextField> text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<RadioSelectionOption> getRadio() {
        return radio;
    }

    public void setRadio(ArrayList<RadioSelectionOption> radio) {
        this.radio = radio;
    }

    public ArrayList<InputTextField> getText() {
        return text;
    }

    public void setText(ArrayList<InputTextField> text) {
        this.text = text;
    }

}
