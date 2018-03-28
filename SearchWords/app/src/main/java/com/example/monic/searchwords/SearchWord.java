package com.example.monic.searchwords;

/**
 * Created by monic on 9/23/2017.
 */

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchWord {

    private Context mContext;
    ArrayList<String> lineWords = new ArrayList<String>();
    ArrayList<String> fileWords = new ArrayList<String>();
    ArrayList<String> occurences = new ArrayList<String>();
    Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
    Matcher matcher;

    public SearchWord(Context context) {
        this.mContext = context;
    }

    public int constructSentence(int index, int left, int right) {
        StringBuilder sb = new StringBuilder();
        ArrayList<String> leftString = new ArrayList<String>();
        ArrayList<String> rightString = new ArrayList<String>();
        int lf = left;
        int rf = right;
        int l = index - 1;
        int r = index + 1;

        while(left > 0 && l >= 0) {
            if (l >= 0) {
                int length = fileWords.get(l).length();
                if (left - length < 0) {
                    leftString.add(fileWords.get(l).substring(0, left - 1));
                    left = left - fileWords.get(l).substring(0, left - 1).length();
                    break;
                } else {
                    left = left - length;
                    leftString.add(fileWords.get(l));
                    l--;
                }
            }
        }

        while(right > 0 && r <= fileWords.size() - 1) {
            if (r <= fileWords.size() - 1) {
                int length = fileWords.get(r).length();
                if (right - length < 0) {
                    rightString.add(fileWords.get(r).substring(0, right - 1));
                    right = right - fileWords.get(r).substring(0, right - 1).length();
                    break;
                } else {
                    right = right - length;
                    rightString.add(fileWords.get(r));
                    r++;
                }
            }
        }

        sb.append("...");
        StringBuilder leftInnerString = new StringBuilder();
        for(int j = leftString.size() - 1; j >= 0; j--) {
            leftInnerString.append(leftString.get(j) + " ");
        }

        if(leftInnerString.toString().length() > lf) {
            int extraLength = leftInnerString.toString().length() - lf;
            sb.append(leftInnerString.toString().substring(extraLength, (leftInnerString.toString().length() - 1)) + " ");
        } else {
            sb.append(leftInnerString.toString());
        }
        sb.append(fileWords.get(index) + " ");

        StringBuilder rightInnerString = new StringBuilder();
        for(int j = 0; j < rightString.size() - 1; j++) {
            if (j != rightString.size() - 1)
                rightInnerString.append(rightString.get(j) + " ");
            else
                rightInnerString.append(rightString.get(j));
        }

        if(rightInnerString.toString().length() > rf) {
            sb.append(rightInnerString.toString().substring(0, (rf - 1)));
        } else {
            sb.append(rightInnerString.toString());
        }
        sb.append("...");
        occurences.add(sb.toString());
        return 0;
    }

    public ArrayList<String> searchWords(String wordToSearch, boolean matchCase) throws IOException {
        String line;
        String []words;

        InputStream is = mContext.getAssets().open("textfile.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while((line = br.readLine()) != null) {
            if (!(line.isEmpty())) {
                if (!(matchCase)) {
                    line.toLowerCase();
                }
                if(line.charAt(0) == '"') {
                    words = line.substring(1, line.length()-1).split(" ");
                } else {
                    words = line.split(" ");
                }
                Collections.addAll(fileWords, words);
            }
        }
        br.close();
        is.close();

        is = mContext.getAssets().open("textfile.txt");
        br = new BufferedReader(new InputStreamReader(is));
        while((line = br.readLine()) != null) {
            if (!(line.isEmpty())) {
                if (!(matchCase)) {
                    line.toLowerCase();
                }
                if(line.charAt(0) == '"') {
                    words = line.substring(1, line.length()-1).split(" ");
                } else {
                    words = line.split(" ");
                }
                int beforeAddSize = lineWords.size();
                Collections.addAll(lineWords, words);
                int afterAddSize = lineWords.size();
                for(int i = beforeAddSize; i < afterAddSize; i++) {
                    matcher = pattern.matcher(lineWords.get(i));
                    String w;
                    if(!matcher.matches()) {
                        w = lineWords.get(i).substring(0, lineWords.get(i).length()-1);
                    } else {
                        w = lineWords.get(i);
                    }
                    if (w.equals(wordToSearch)) {
                        int leftLength;
                        int rightLength;
                        int remainingLength = 30 - wordToSearch.length();
                        if (i == 0) {
                            leftLength = 0;
                            rightLength = remainingLength;
                        } else if (i == fileWords.size() - 1) {
                            rightLength = 0;
                            leftLength = remainingLength;
                        } else {
                            if (remainingLength % 2 == 0) {
                                leftLength = remainingLength / 2;
                            } else {
                                leftLength = (remainingLength / 2) + 1;
                            }
                            rightLength = remainingLength - leftLength;
                        }
                        constructSentence(i, leftLength, rightLength);
                    }
                }
            }
        }
        br.close();
        is.close();
        System.out.println(occurences.size());
        return occurences;
    }
}