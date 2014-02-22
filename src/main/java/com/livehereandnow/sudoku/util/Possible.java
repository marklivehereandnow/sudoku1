/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.livehereandnow.sudoku.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mark
 */
public class Possible implements Basic {

    private int[][] possible = new int[82][10];

  //  private int[] member;

    public Possible() {
        init();
    }

//    public void setSudokuMembers(int[] m) {
//        member = m;
//    }

    public void removePossibleValueByCell( int cellId,int val) {
//        System.out.println("id=" + cellId + ", val=" + val);
        //   int val=member[cellId];
        int grp1 = CELL_GROUPS[cellId][1];
        int grp2 = CELL_GROUPS[cellId][2];
        int grp3 = CELL_GROUPS[cellId][3];

        for (int n = 1; n <= 9; n++) {
            // given cell
            possible[cellId][n] = 0;
            // same row 
            possible[GROUP_MEMBERS[grp1][n]][val] = 0;
            // same col
            possible[GROUP_MEMBERS[grp2][n]][val] = 0;
            // same box
            possible[GROUP_MEMBERS[grp3][n]][val] = 0;
        }
    }

//    
//    public int getCount() {
//        int cnt = 0;
//        for (int id = 1; id <= 81; id++) {
//                cnt=cnt+getCount(id);
//            }
//        }
//        return cnt;
//    }
    public int getCount() {
        int cnt = 0;
        for (int id = 1; id <= 81; id++) {
            cnt += getCount(id);
        }
        return cnt;
    }

    /**
     *
     * @param id
     * @return
     */
    public int getCount(int id) {
        int cnt = 0;
        for (int n = 1; n <= 9; n++) {
            if (possible[id][n] > 0) {
                cnt++;
            }
        }
        return cnt;
    }

    public int getFirstPossible(int id) {
        //  int val = 0;
        for (int n = 1; n <= 9; n++) {
            if (possible[id][n] > 0) {
                return n;
            }
        }
        return 0;
    }

    public List<Integer> getSinglePossibleArray() {
        List<Integer> single = new ArrayList<Integer>();

        for (int m = 1; m <= 81; m++) {
            // given ce
            if (this.getCount(m) == 1) {
                single.add(m);
            }
        }

        return single;
    }

    public int[] getSingleArray() {
        int[] single = new int[162];
        int cnt = 0;

        for (int m = 1; m <= 81; m++) {
            // given ce
            if (this.getCount(m) == 1) {
//                    System.out.println("cell#" + m + " has only one possible value "+getFirstPossible(m));

                single[cnt++] = m;// id
                single[cnt++] = getFirstPossible(m);// id

            }
        }

        return single;
    }

    public int[] getPossible(int id) {
        return possible[id];
    }

    public void setPossible(int id, int[] val) {
        this.possible[id] = val;
    }

    public void init() {
        for (int id = 1; id <= 81; id++) {
            // System.out.printf("%2d ", id);

            for (int i = 1; i <= 9; i++) {
                possible[id][i] = i;
            }

        }
    }

    public void removeValueByGroup(int val, int grpId) {

    }

    public void toPrint() {
        for (int id = 0; id < 81; id++) {
            System.out.printf("%2d ", id);

            for (int i = 1; i <= 9; i++) {
                System.out.printf("%d", possible[id][i]);

            }
            System.out.printf("\n");

        }
    }

    public String toString(int k) {
        if (k == 1) {
            StringBuilder sb = new StringBuilder();

            for (int id = 1; id <= 81; id++) {
                sb.append("cell#").append(id).append("{");
                for (int i = 1; i <= 9; i++) {
                    if (possible[id][i] > 0) {
                        sb.append(possible[id][i]);
                    }
                }

                if (id % 9 != 0) {
                    sb.append("}, ");
                } else {
                    sb.append("}\n");
                }
            }
            sb.append("possible value count:").append(this.getCount()).append("\n");
            return sb.toString();
        }

        return "--- not yet define ---";
    }

    public void toPrint(int style){
        System.out.println(toString());
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Sudoku 9x9 possible --- (start)\n");
        int id = 0;
        for (int m = 1; m <= 9; m++) {
            sb.append("row#").append(m).append(":");

            for (int n = 1; n <= 9; n++) {
                id = (m - 1) * 9 + n;
                sb.append("{");
                for (int i = 1; i <= 9; i++) {
                    if (possible[id][i] > 0) {
                        sb.append(possible[id][i]);
                    }
                }
                sb.append("}");

                if (n < 9) {
                    sb.append(",");
                }
            }// end of id
            sb.append("\n");

        }// end of m

        sb.append("possible value count:").append(this.getCount()).append("\n\n");

          sb.append("single possible value: ");

        int[] temp = getSingleArray();
        for (int m = 0; m < 81; m = m + 2) {
            if (temp[m] > 0) {
                //answer.setMember(temp[m], temp[m + 1]);
                //  System.out.println("cell#" + temp[m] + " is {" + temp[m + 1] + "}");
                sb.append("\n  cell[").append(temp[m]).append("]=").append(temp[m + 1]).append(" " );

            } else {
                break;
            }

        }

        sb.append("\n--- Sudoku 9x9 possible --- (end)\n");
        return sb.toString();
    }
}
