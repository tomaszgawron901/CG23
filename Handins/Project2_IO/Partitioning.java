package Handins.Project2_IO;

public class Partitioning {

    public static void Partition(SuperSimpleTape<Integer> tape, int start, int end, Integer[] memory, int B, int p) {
        final int M = memory.length;

        while (start < end) {
            tape.MoveToIndex(start);
            int readElements1 = Math.min(M-B, end-start);
            Helpers.WriteTo(memory, 0, tape.ReadBlock(readElements1));
            start += readElements1;

            for(int m = 0; start < end;) {
                int readElements2 = Math.min(end-start, B);
                tape.MoveToIndex(end-readElements2);
                Helpers.WriteTo(memory, M-readElements2, tape.ReadBlock(readElements2));

                for (int i = M-readElements2; i < M && m < readElements1; i++) {
                    if(memory[i] < p) {
                        for (;m < readElements1; m++) {
                            if(memory[m] > p) {
                                Helpers.swap(memory, m, i);
                                break;
                            }
                        }
                    }
                }
                tape.WriteBlock(Helpers.PopRange(memory, M-readElements2, M));
                if(m >= M-B) break;
                end -= readElements2;
            }
            
            // last run might still contain unpartitioned elements in the memory
            // so we solve it there
            if(start >= end) {
                for (int i = 0, j = readElements1-1; i < j; i++) {
                    if(memory[i] > p) {
                        for(; j > i; j--) {
                            if(memory[j] < p) {
                                Helpers.swap(memory, i, j);
                                break;
                            }
                        }
                    }
                }
            }

            tape.MoveToIndex(start-readElements1);
            tape.WriteBlock(Helpers.PopRange(memory, 0, readElements1));
        }
    }

    public static void main(String[] args) {
        final int H = 4;  // number of wraps
        final int B = 8;  // block size
        final int L = 8;  // track length
        final int N = H*B*L - 17;
        final int M = B*4;  // memory size
        final int p = 5_000;  // partition point

        var tape = Helpers.GetRandomTape(N);
        var memory = new Integer[M];

        Partition(tape, 0, N, memory, B, p);

        Helpers.PrintTape(tape, p);
        
    }


}
