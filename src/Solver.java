import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

public class Solver {
    ArrayList<Node> solution; // save the answer. Use this instead of previous node
    int iterationLimit = 10000; //this is apply to the hardest 8-puzzle instance. Go fix by yourself for harder instances
    int iteration = 0;
    /**
     * Represent for state of problems
     */
    class Node implements Comparable {
        Board b;
        int step;
        Node previous;

        public Node(Board b, int step, Node previous) {
            this.b = b;
            this.step = step;
            this.previous = previous;
        }

        //define compare via priority NOT manhattan
        @Override
        public int compareTo(Object o) {
            if (!(o instanceof Node))
                throw new UnsupportedOperationException();

            Node compare = (Node) o;
            return Integer.compare(b.manhattanV2() + step, compare.b.manhattanV2() + compare.step);
        }
    }

    /**
     * Solve the problem
     */
    public Solver(Board initial) {
        solution = new ArrayList<>();
        PriorityQueue<Node> queue = new PriorityQueue<>();
        // add the initial board
        queue.add(new Node(initial, 0, null));

        while (true) {
            iteration++;
            // delete the less priority node. Then put the deleted one to Arraylist
            Node delete = queue.poll();
            solution.add(delete);
            if (delete.b.isGoal())
                break;
            if (iteration == iterationLimit) {
                System.out.println("Game over with " + iterationLimit + " iterations");
                System.exit(0);
            }

            for (Board board : delete.b.neighbors()) {
                boolean isExist = false;
                //check the previous node to optimize finding space
                for (Node node : solution)
                    if (node.b.equals(board)) {
                        isExist = true;
                        break;
                    }

                if (!isExist)
                    queue.add(new Node(board, delete.step + 1, delete));
            }
        }
    }

    public void printSolution() {
        Stack<Node> s = new Stack<>();
        Node result = solution.get(solution.size() - 1);
        while (true) {
            s.push(result);
            if (result.previous == null)
                break;
            result = result.previous;
        }

        System.out.println("Minimum number of moves: " + (s.size() - 1));
        while (!s.isEmpty())
            System.out.println(s.pop().b.toString());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the size of N-puzzle: ");
        int n = sc.nextInt();
        int[][] blocks = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = sc.nextInt();
            }
        }

        Solver solver = new Solver(new Board(blocks));
        System.out.println("Number of iterations: " + solver.iteration);
        solver.printSolution();
    }
}

/**
 * ---------------------------------------------------------------------
 * The commented methods below is bugged and need fixed in the future. But doesn't affect in algorithm
 * @return ??????
 */
    /*
    private static int getInverse(int[][] blocks) {
        int before = -1;
        int inverse = 0;
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks.length; j++) {
                if (before > blocks[i][j] && blocks[i][j] != 0)
                    inverse++;
                before = blocks[i][j];
            }
        return inverse;
    }

    //overload method for isSolvable
    public static boolean isSolvable(int[][] blocks) {
        int inverse = getInverse(blocks);
        // if N odd then puzzle is solvable if number of inversions on the initial blocks is even
        if (blocks.length % 2 != 0)
            return inverse % 2 == 0;
        else {

                - N is even. The puzzle is solvable if:
                + The blank is on an even row counting from the bottom (second-last, fourth-last,...)
                and the inversions is odd
                + The blank is on an odd row counting from the bottom (last, third-last,...)
                and the inversions is even

            int blankRow;
            // find the row containing the blank
            for (blankRow = 0; blankRow < blocks.length; blankRow++)
                for (int i = 0; i < blocks.length; i++)
                    if (blocks[blankRow][i] == 0)
                        break;

            // Apply condition
            if ((blocks.length - blankRow + 1) % 2 == 0)
                return inverse % 2 != 0;
            else
                return inverse % 2 == 0;
        }
    }
    */