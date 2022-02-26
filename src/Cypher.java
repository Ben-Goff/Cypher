import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Cypher {

    static double beta = 220;

    public static void main(String[] args) throws IOException {
        Miner ggMiner = new Miner("C:\\Users\\Ben\\IdeaProjects\\Cypher\\src\\GreatGatsby.txt");
        int[][] ggMatrix = ggMiner.Mine();
        List<Integer> perm = IntStream.rangeClosed(0, 26).boxed().collect(Collectors.toList());
        Collections.shuffle(perm);
        Encoded fEncode = new Encoded("C:\\Users\\Ben\\IdeaProjects\\Cypher\\src\\j_10.txt");
        double currentNRG;
        double potentialNRG;
        int count = 0;
        while (count < 150000) {
            count++;
            List<Integer> potentialPerm = transpose(new ArrayList<>(perm));
            potentialNRG = fEncode.Energy(ggMatrix, potentialPerm);
            currentNRG = fEncode.Energy(ggMatrix, perm);
            if (potentialNRG > currentNRG || Math.random() <= Math.exp(beta * (potentialNRG - currentNRG))) {
                perm = potentialPerm;
            }
        }
        System.out.println(perm);
        System.out.println(decode("C:\\Users\\Ben\\IdeaProjects\\Cypher\\src\\j_10.txt", perm));
    }

    public static <E> List<E> transpose(List<E> s) {
        int length = s.size();
        int first = ThreadLocalRandom.current().nextInt(0, length);//random index of s
        int second = (first + ThreadLocalRandom.current().nextInt(1, length)) % length; //random index of s not equal to first
        E firstEntry = s.get(first);
        E secondEntry = s.get(second);
        s.set(first, secondEntry);
        s.set(second, firstEntry);
        return s;
    }

    public static String decode(String fn, List<Integer> p) throws IOException {
        File f = new File(fn);     //Creation of File Descriptor for input file
        FileReader fr = new FileReader(f);   //Creation of File Reader object
        BufferedReader br = new BufferedReader(fr);  //Creation of BufferedReader object
        int c;
        StringBuilder decoding = new StringBuilder();
        while ((c = br.read()) != -1) {
            if (c < 125) {
                c = p.get(c % 32);
                if (c == 0) {
                    decoding.append(" ");
                } else decoding.append((char) ((c % 32) + 96));
            }
        }
        return decoding.toString();
    }
}
