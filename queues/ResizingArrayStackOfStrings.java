/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class ResizingArrayStackOfStrings {
    private String[] s;
    private int N = 0;

    public ResizingArrayStackOfStrings() {
        s = new String[1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public String pop() {
        String item = s[--N]; // decrement first then evaluate
        s[N] = null; // allows gc to reclaim reference in array

        // efficient to halve array when it's 1/4 full
        if (N > 0 && N == s.length / 4) resize(s.length / 2);
        return item;
    }

    public void push(String item) {
        if (N == s.length) resize(2 * s.length);
        s[N++] = item;
    }

    public void resize(int capacity) {
        String[] copy = new String[capacity];
        for (int i = 0; i < N; i++)
            copy[i] = s[i];
        s = copy;
    }

    public static void main(String[] args) {

    }
}
