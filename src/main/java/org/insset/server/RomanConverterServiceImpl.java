/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.insset.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.insset.client.service.RomanConverterService;

/**
 *
 * @author user
 */
@SuppressWarnings("serial")
public class RomanConverterServiceImpl extends RemoteServiceServlet implements
        RomanConverterService {

    @Override
    public String convertDateYears(String nbr) throws IllegalArgumentException {
        DateFormat sourceFormat = null;

        if (nbr.contains("-")) {
            sourceFormat = new SimpleDateFormat("dd-MM-yyyy");
        } else {
            sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
        }

        Date d = null;
        try {
            d = sourceFormat.parse(nbr);
        } catch (java.text.ParseException e) {
            throw new IllegalArgumentException("The date format is dd/MM/yyyy or dd-MM-yyyy");
        }

        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append(this.convertArabeToRoman(d.getDate()) + "/");
        resultBuilder.append(this.convertArabeToRoman(1 + d.getMonth()) + "/");
        resultBuilder.append(this.convertArabeToRoman(1900 + d.getYear()));

        return resultBuilder.toString();
    }

    /**
     *
     * @param str
     * @return
     * @throws IllegalArgumentException return decimal number for a single roman
     * number given
     */
    public Integer value(char str) throws IllegalArgumentException {
        switch (str) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
        }
        return 0;
    }

    /**
     *
     * @param str
     * @return
     * @throws IllegalArgumentException Return an decimal number for a given
     * roman number
     */
    @Override
    public Integer convertRomanToArabe(String str) throws IllegalArgumentException {

        int res = 0;

        for (int i = 0; i < str.length(); i++) {
            // Getting value of symbol s[i]
            int s1 = value(str.charAt(i));

            // Getting value of symbol s[i+1]
            if (i + 1 < str.length()) {
                int s2 = value(str.charAt(i + 1));

                // Comparing both values
                if (s1 >= s2) {
                    // Value of current symbol is greater
                    // or equalto the next symbol
                    res = res + s1;
                } else {
                    res = res + s2 - s1;
                    i++; // Value of current symbol is
                    // less than the next symbol
                }
            } else {
                res = res + s1;
                i++;
            }
        }

        return res;
    }

    @Override
    public String convertArabeToRoman(Integer nbr) throws IllegalArgumentException {
        if (nbr < 1 || nbr > 2000) {
            throw new IllegalArgumentException("You need to give an integer between 1 and 2000");
        }

        String[] rnChars = {"M", "CM", "D", "C", "XC", "L", "X", "IX", "V", "I"};
        int[] rnVals = {1000, 900, 500, 100, 90, 50, 10, 9, 5, 1};
        String retVal = "";

        for (int i = 0; i < rnVals.length; i++) {
            int numberInPlace = nbr / rnVals[i];
            if (numberInPlace == 0) {
                continue;
            }
            retVal += numberInPlace == 4 && i > 0 ? rnChars[i] + rnChars[i - 1]
                    : new String(new char[numberInPlace]).replace("\0", rnChars[i]);
            nbr = nbr % rnVals[i];
        }
        return retVal;
    }

}
