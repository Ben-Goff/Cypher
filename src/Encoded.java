import java.io.*;
import java.util.List;

public class Encoded {

    String fileName;

    public Encoded(String fn) {
        fileName = fn;
    }

    public double Energy(int[][] Q, List<Integer> P) throws IOException {
        double nrg = 0;
        double count = 0;
        for(int[] a : Q) {
            for (int i : a) {
                count += i;
            }
        }
        File f = new File(this.fileName);     //Creation of File Descriptor for input file
        FileReader fr = new FileReader(f);   //Creation of File Reader object
        BufferedReader br = new BufferedReader(fr);  //Creation of BufferedReader object
        int currentIndex;
        int previousIndex;
        int c = br.read();
        currentIndex = c % 32; //First character
        while ((c = br.read()) != -1) {
            if (c < 123) {
                previousIndex = currentIndex;
                currentIndex = c % 32;
                nrg += Q[P.get(previousIndex)][P.get(currentIndex)];
            }
        }
        return nrg / count;
    }
}
