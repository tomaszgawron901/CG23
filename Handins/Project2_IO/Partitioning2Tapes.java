package Handins.Project2_IO;

public class Partitioning2Tapes {
    public static void main(String[] args) {
        final int N = 1024;
        final int p = 90000;

        var mainTape = Helpers.GetRandomTape(N);
        var helperTape = new SuperSimpleTape<Integer>(new Integer[N]);

        mainTape.position = 0;
        helperTape.position = 0;

        while(mainTape.position < mainTape.end) {
            var element = mainTape.ReadElement();
            if(element > p) {
                helperTape.WriteElement(element);
                helperTape.position++;
            }
            mainTape.position++;
        }
        helperTape.end = helperTape.position;
        helperTape.position = 0;

        while(helperTape.position < helperTape.end ) {
            mainTape.position--;
            var smallerElement = mainTape.ReadElement();
            if(smallerElement < p) {
                // swap element in mainTape and helperTape
                var greaterElement = helperTape.ReadElement();
                mainTape.WriteElement(greaterElement);
                helperTape.WriteElement(smallerElement);
                helperTape.position++;
            }
            else {
                helperTape.end--;
            }
            
        }

        while(helperTape.position > 0) {
            // move to first greater element
            do {
                mainTape.position--;
            }
            while(mainTape.ReadElement() < p);
            // replace greater element with smaller element
            helperTape.position--;
            mainTape.WriteElement(helperTape.ReadElement());
            
        }

        Helpers.PrintTape(mainTape, p);

    }
}
