import java.util.ArrayList;

public class Board {
    int[][] blocks;
    private int mahattan = -1;

    public Board(int[][] blocks) {
        this.blocks = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; ++i)
            for (int j = 0; j < blocks.length; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
    }
    public int dimension() {
        return blocks.length;
    }

    public int manhattanV2() {
        if (mahattan != -1)
            return mahattan;

        int result = 0;
        int[] xFinal = new int[dimension() * dimension() - 1];
        int[] yFinal = new int[dimension() * dimension() - 1];

        for (int i = 0; i < dimension() * dimension() - 1; ++i) {
            xFinal[i] = i / dimension();
            yFinal[i] = i % dimension();
        }

        for (int i = 0; i < dimension(); i++)
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] != 0)
                    result += Math.abs(i - xFinal[blocks[i][j] - 1]) + Math.abs(j - yFinal[blocks[i][j] - 1]);
            }
        mahattan = result;
        return result;
    }

    // out-date version with O(N^3) complexity
    public int manhattan() {
        int result = 0;
        for (int i = 0; i < dimension() * dimension() - 1; ++i) {
            int x = 0, y = 0;
            int xFinal = i / dimension();
            int yFinal = i % dimension();
            for (int j = 0; j < dimension(); j++)
                for (int k = 0; k < dimension(); k++)
                    if (blocks[j][k] == i + 1) {
                        x = j;
                        y = k;
                    }
            result += Math.abs(x - xFinal) + Math.abs(y - yFinal);
        }
        return result;
    }
    public boolean isGoal() {
        return manhattanV2() == 0;
    }

    private void swap(int x1, int y1, int x2, int y2) {
        int temp = blocks[x1][y1];
        blocks[x1][y1] = blocks[x2][y2];
        blocks[x2][y2] = temp;
    }

    public boolean equals(Object y) {
        if (y == null)
            return false;
        if (y == this)
            return true;
        if (!(y instanceof Board))
            return false;

        Board board = (Board) y;
        if (board.dimension() != dimension())
            return false;

        for (int i = 0; i < dimension(); i++)
            for (int j = 0; j < dimension(); j++)
                if (blocks[i][j] != board.blocks[i][j])
                    return false;

        return true;
    }

    public Iterable<Board> neighbors() {
        //move cases
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        ArrayList<Board> result = new ArrayList<>();
        int length = dimension();

        //find zero position
        int xZero = 0, yZero = 0;
        for (int i = 0; i < length; i++)
            for (int j = 0; j < length; j++)
                if (blocks[i][j] == 0) {
                    xZero = i;
                    yZero = j;
                }

        //Establish 4 positions surrounding 0
        int[] xZeroes = new int[4];
        int[] yZeroes = new int[4];
        for (int i = 0; i < 4; ++i) {
            xZeroes[i] = xZero + dx[i];
            yZeroes[i] = yZero + dy[i];
        }

        //create neighbors and verify if they are out of bound
        for (int i = 0; i < 4; ++i) {
            if (xZeroes[i] >= 0 && xZeroes[i] < length && yZeroes[i] >= 0 && yZeroes[i] < length) {
                int[][] copy = new int[length][length];
                for (int j = 0; j < length; j++)
                    for (int k = 0; k < length; k++)
                        copy[j][k] = blocks[j][k];

                int temp = copy[xZero][yZero];
                copy[xZero][yZero] = copy[xZeroes[i]][yZeroes[i]];
                copy[xZeroes[i]][yZeroes[i]] = temp;
                result.add(new Board(copy));
            }
        }
        return result;
    }
    public String toString() {
        String result = "";
        for (int[] block : blocks) {
            for (int j: block)
                result += (j + " ");
            result += "\n";
        }
        return result;
    }
}
