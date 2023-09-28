public class Sort {


    private static boolean less(WordFreq[] array, int i, int j) {
        return array[i].getFreq() < array[j].getFreq();
    }

    private static void exch(WordFreq[] array, int i, int j) {
        WordFreq temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // HEAPSORT
    public static void heapsort(WordFreq[] array){
        int N = array.length-1;
        for(int k=N/2; k>=1; k--)
            sink(array, k, N);  //convert array to min-heap

        while (N>1){
            exch(array, 1, N);
            N--;
            sink(array, 1, N);
        }
    }

    private static void sink(WordFreq[] array, int k, int N) {
        while (2 * k <= N) {
            int c1 = 2 * k;
            int c2 = c1;
            if (c1 + 1 <= N)
                c2 = c1 + 1;

            int maxC = less(array, c1, c2) ? c2 : c1;

            if (less(array, k, maxC)) {
                exch(array, k, maxC);     //it is a min-heap, so root contains the element with the smallest key
                k = maxC;
            } else
                break;
        }
    }

}
