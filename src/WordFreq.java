public class WordFreq {

    private String key;
    private int freq;

    WordFreq(String key) {
        this.key = key;
        freq = 1;
    }

    WordFreq(WordFreq curr) {
        key = curr.key();
        freq = curr.getFreq();
    }

    public String key() { return key; }
    public int getFreq() { return freq; }

    public void increaseFreq() { freq++; }
    public void setFreq(int freq) { this.freq = freq; }

    public String toString() { return "\""+key()+"\": "+getFreq()+" times"; }
}
