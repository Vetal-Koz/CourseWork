package org.example.entity;




public class Textdocument extends Electronicdocument{


    private String fontfamily;


    private String textstructure;


    public String getFontfamily() {
        return fontfamily;
    }

    public void setFontfamily(String fontfamily) {
        this.fontfamily = fontfamily;
    }

    public String getTextstructure() {
        return textstructure;
    }

    public void setTextstructure(String textstructure) {
        this.textstructure = textstructure;
    }

}