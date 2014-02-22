/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.livehereandnow.sudoku.util;

/**
 *
 * @author mark
 */
public class Solver implements RunMode {

    public static void mark(String str) {
        if (IS_DEBUG) {
            System.out.println(str);
        }
    }

    public static void show(String str) {
        System.out.println(str);
    }

    private Sudoku question;
    private Sudoku answer = new Sudoku();
    private Possible possible = new Possible();

    public void setQuestion(Sudoku question) {
        this.question = question;
    }

    public void setPossible(Possible possible) {
        this.possible = possible;
    }

    public Sudoku getQuestion() {
        return question;
    }

    public Sudoku getAnswer() {
        return answer;
    }

    public Possible getPossible() {
        return possible;
    }

    /**
     * Main purpose for Solver to provide answer based on given Sudoku question
     *
     * @param problem
     */
    public Solver(Sudoku s) {
        question = s;
        answer.setSudoku(s);;
        //  run();
    }

    /**
     * for any unknown cell having no any possible values
     *
     * @return true: it's broken
     */
    public boolean isBroken() {
        for (int k = 1; k <= 81; k++) {
            if (answer.getMember(k) == 0) {
                if (possible.getCount(k) == 0) {
                    return true;
                }
            }
        }
        //for (answer.getMember(id))

        return false;
    }

    public void run() {
        if (IS_DEBUG) {
            mark("**************");
            mark("*   Step 1   *");
            mark("**************");
        }
        for (int m = 1; m <= 81; m++) {
            if (question.getMember(m) > 0) { // for known cell, 
                possible.removePossibleValueByCell(m, question.getMember(m));
            }//    
        }
        answer.setKnownMembers(possible.getSingleArray());

        if (IS_DEBUG) {
            mark(possible.toString());
            answer.show(1);
        }
//
        // === 2. going for loop ===
        if (IS_DEBUG) {
            mark("**************");
            mark("*   Step 2   *");
            mark("**************");

        }
        int possibleCnt = possible.getCount();

        while (true) {
            int[] newlyAdded = answer.getNewlyAdded();
            for (int m = 1; m <= 81; m++) {
                if (newlyAdded[m] > 0) { // for known cell, 
                    possible.removePossibleValueByCell(m, answer.getMember(m));
                }//    
            }

            //
            // to end this loop  
            //   when no more improvement
            if (possibleCnt == possible.getCount()) {
                break;
            } else {
                possibleCnt = possible.getCount();
                answer.setKnownMembers(possible.getSingleArray());
                if (IS_DEBUG) {
                    mark(possible.toString());
                    answer.show(1);
                }
            }
        }
    }

    public void show() {
        mark("======== Solver.show ======== (start)");
        show("**************");
        show("*  Question  *");
        show("**************");
        show("question is " + question.toString());
       
            question.show();
        

        show("**************");
        show("*   Answer   *");
        show("**************");
        answer.show();

        mark("**************");
        mark("*  Possible  *");
        mark("**************");
        if (IS_DEBUG) {
            possible.show();
        }

        mark("**************");
        mark("*   Status   *");
        mark("**************");
        if (isBroken()) {
            mark("This Sudoku is Broken!!!");
        } else {
            if (answer.getCount() == 81) {
                show("Got it!");

            } else {
                mark(" ... still good, need to go deeper");
            }
        }

        mark("======== Solver.show ======== (end)");

    }

}
