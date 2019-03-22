package krypto;

import my.holders.SKMap;

import java.util.LinkedList;

public class SquareII {
    SKMap<Character, Coords> key = new SKMap<>();

    private class Coords {
        int row;
        int col;

        Coords(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int hashCode() {
            int r = 17;
            r = r * row + 17;
            r = r * col + 17;
            return r;
        }

        public boolean equals(Object o) {
            return (o instanceof Coords &&
                    this.row == ((Coords) o).row &&
                    this.col == ((Coords) o).col);
        }

        public String toString() {
            return "[" + row + "][" + col + "]";
        }
    }

    SquareII(String keyWord) {
        char[] temp = keyWord.toCharArray();
        int i = 0, j = 0;
        for (Character c : temp) {
            if (key.isEmpty() || !key.containsKey(c)) {
                key.put(c, new Coords(j, i));
                if (i != 4) i++;
                else {
                    i = 0;
                    j++;
                }
            }
        }
        char next = 'a';
        while (next != 'z' + 1) {
            if (next != 'j')
                if (!key.containsKey(next)) {
                    key.put(next, new Coords(j, i));
                    if (i != 4) i++;
                    else {
                        i = 0;
                        j++;
                    }
                }
            next++;
        }
        //System.out.println(key.toString());
    }

    String code(String uncoded) throws NullPointerException {
        LinkedList<Character> temp = new LinkedList<Character>();
        String coded = "";
        for (Character c : uncoded.toCharArray()) {
            if (c == 'j') c = 'i';
            if (c == ' ') ;
            else if (temp.size() > 0 && c == temp.getLast()) {
                temp.add('x');
                temp.add(c);
            } else temp.add(c);
        }
        if (temp.size() % 2 != 0) temp.add('x');
        for (int i = 0; i < temp.size(); i += 3) {
            Coords cl1 = key.get(temp.get(i)), cl2 = key.get(temp.get(i + 1));
            temp.remove(i);
            temp.remove(i);
            if (cl1.col == cl2.col) {
                if (key.getKey(new Coords((cl1.row + 1) % 5, cl1.col)) == null) throw new NullPointerException();
                temp.add(i, key.getKey(new Coords((cl1.row + 1) % 5, cl1.col)));
                temp.add(i + 1, key.getKey(new Coords((cl2.row + 1) % 5, cl2.col)));
                temp.add(i + 2, '-');
            } else if (cl1.row == cl2.row) {
                temp.add(i, key.getKey(new Coords(cl1.row, (cl1.col + 1) % 5)));
                temp.add(i + 1, key.getKey(new Coords(cl2.row, (cl2.col + 1) % 5)));
                temp.add(i + 2, '-');
            } else {
                temp.add(i, key.getKey(new Coords(cl1.row, cl2.col)));
                temp.add(i + 1, key.getKey(new Coords(cl2.row, cl1.col)));
                temp.add(i + 2, '-');
            }
        }
        for (int i = 0; i < temp.size() - 1; i++) {
            coded += Character.toUpperCase(temp.get(i));
        }
        return coded;

    }

    String uncode(String coded) {
        LinkedList<Character> temp = new LinkedList<Character>();
        String uncoded = "";
        for (Character c : coded.toCharArray()) {
            if (c != '-')
                temp.add(Character.toLowerCase(c));
        }
       // if (temp.size() % 2 != 0) temp.add('x');
        for (int i = 0; i < temp.size(); i += 2) {
            Coords cl1 = key.get(temp.get(i)), cl2 = key.get(temp.get(i + 1));
            temp.remove(i);
            temp.remove(i);
            if (cl1.col == cl2.col) {
                temp.add(i, key.getKey(new Coords((cl1.row + 4) % 5, cl1.col)));
                temp.add(i + 1, key.getKey(new Coords((cl2.row + 4) % 5, cl2.col)));
            } else if (cl1.row == cl2.row) {
                temp.add(i, key.getKey(new Coords(cl1.row, (cl1.col + 4) % 5)));
                temp.add(i + 1, key.getKey(new Coords(cl2.row, (cl2.col + 4) % 5)));
            } else {
                temp.add(i, key.getKey(new Coords(cl1.row, cl2.col)));
                temp.add(i + 1, key.getKey(new Coords(cl2.row, cl1.col)));
            }
        }
        for (int i = 0; i < temp.size() ; i++) {
            if (temp.get(i) == 'x' &&
                    (( i > 0 && i< temp.size()-1 && temp.get(i - 1) == temp.get(i + 1)) ||
                            i==temp.size()-1)) ;
            else uncoded += temp.get(i);
        }
        return uncoded;

    }

}
