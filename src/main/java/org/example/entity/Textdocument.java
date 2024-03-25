package org.example.entity;


import java.time.Instant;

public class Textdocument extends Electronicdocument{


    private String fontfamily;


    private String textstructure;


    public Textdocument(Integer id, String objname, Integer major, Integer classId, String theme, Instant createddate, String doclanguage, String format, Float docsize, Boolean docsecurity, String fontfamily, String textstructure) {
        super(id, objname, major, classId, theme, createddate, doclanguage, format, docsize, docsecurity);
        this.fontfamily = fontfamily;
        this.textstructure = textstructure;
    }

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