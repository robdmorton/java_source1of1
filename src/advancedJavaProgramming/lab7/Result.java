package advancedJavaProgramming.lab7;


public class Result {

    private BiblioDocument[] colllection = new BiblioDocument[0];

    public Result() {
    }

    public Result(BiblioDocument[] collection) {
        this.colllection = collection;
    }

    public int getCount() {
        return this.colllection.length;
    }

    public BiblioDocument getItem(int i) {
        return this.colllection[i];
    }
} // end of class
