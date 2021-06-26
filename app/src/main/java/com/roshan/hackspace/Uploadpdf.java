package com.roshan.hackspace;

public class Uploadpdf {

    public String pdfbookname,pdfauthor,pdfpublication,url;

    public Uploadpdf() {
    }

    public Uploadpdf(String pdfbookname, String pdfauthor, String pdfpublication, String url) {
        this.pdfbookname = pdfbookname;
        this.pdfauthor = pdfauthor;
        this.pdfpublication = pdfpublication;
        this.url = url;
    }

    public String getPdfbookname() {
        return pdfbookname;
    }

    public void setPdfbookname(String pdfbookname) {
        this.pdfbookname = pdfbookname;
    }

    public String getPdfauthor() {
        return pdfauthor;
    }

    public void setPdfauthor(String pdfauthor) {
        this.pdfauthor = pdfauthor;
    }

    public String getPdfpublication() {
        return pdfpublication;
    }

    public void setPdfpublication(String pdfpublication) {
        this.pdfpublication = pdfpublication;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
