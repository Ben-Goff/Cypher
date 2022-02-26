import java.io.*;

public class Miner {

    String fileName;

    public Miner(String fn) {
        fileName = fn;
    }

    public int[][] Mine() throws IOException {
        int[][] tMatrix = new int[27][27];
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
                tMatrix[previousIndex][currentIndex]++; //add 1 to transition position in matrix
            }
        }
        return tMatrix;
    }
}
