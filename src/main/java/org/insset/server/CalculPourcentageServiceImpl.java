/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.insset.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.insset.client.service.CalculPourcentageService;

/**
 *
 * @author user
 */
public class CalculPourcentageServiceImpl extends RemoteServiceServlet implements
        CalculPourcentageService {

    @Override
    public String calculRemise(Integer p, Integer nbr) throws IllegalArgumentException {
        float montantRemise = 0;
        float montantFinal = 0;
        if (p == 0) {
            return new String("Le montant de la remise est de 0 €, le prix final est" + nbr + " €");
        }

        montantRemise = nbr * p / 100;
        montantFinal = nbr - montantRemise;
        return new String("Le montant de la remise est de " + montantRemise + "€, le prix final est" + montantFinal + "€");

    }

}
