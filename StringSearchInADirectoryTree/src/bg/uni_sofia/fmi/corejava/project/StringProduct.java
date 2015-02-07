package bg.uni_sofia.fmi.corejava.project;


public class StringProduct {
    private String lineOfText;
    private String fileName;
    private int lineNumber;

    public StringProduct() {
        this("", "", 0);
    }

    public StringProduct(String lineOfText, String fileName, int lineNumber) {
        this.lineOfText = lineOfText;
        this.fileName = fileName;
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public String getLineOfText() {

        return lineOfText;
    }

    public boolean findString(String stringToFind) {
        return lineOfText.contains(stringToFind);
    }

    @Override
    public String toString() {
        return String.format("%s, %s", lineOfText, fileName);
    }
}
