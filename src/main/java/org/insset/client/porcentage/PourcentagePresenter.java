/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.insset.client.porcentage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResetButton;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TextBox;
import org.insset.client.message.dialogbox.DialogBoxInssetPresenter;
import org.insset.client.service.CalculPourcentageService;
import org.insset.client.service.CalculPourcentageServiceAsync;
import org.insset.shared.FieldVerifier;

/**
 *
 * @author user
 */
public class PourcentagePresenter extends Composite {

    @UiField
    public ResetButton boutonClearRemise;
    @UiField
    public SubmitButton boutonCalculRemise;
    @UiField
    public ResetButton boutonClearDepart;
    @UiField
    public SubmitButton boutonCalculDepart;
    @UiField
    public TextBox montantDepar;
    @UiField
    public TextBox porcentageA;
    @UiField
    public TextBox montantFinal;
    @UiField
    public TextBox porcentageB;
    @UiField
    public Label errorLabelRemise;
    @UiField
    public Label errorLabelDepart;

    interface MainUiBinder extends UiBinder<HTMLPanel, PourcentagePresenter> {
    }

    private static MainUiBinder ourUiBinder = GWT.create(MainUiBinder.class);
    /**
     * Create a remote service proxy to talk to the server-side Greeting
     * service.
     */
    private final CalculPourcentageServiceAsync service = GWT.create(CalculPourcentageService.class);

    /**
     * Constructeur
     */
    public PourcentagePresenter() {
        initWidget(ourUiBinder.createAndBindUi(this));
        initHandler();
    }

    /**
     * Init des handler
     */
    private void initHandler() {
        boutonClearRemise.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                montantDepar.setText("");
                porcentageA.setText("");
                errorLabelRemise.setText("");
            }
        });
        boutonCalculRemise.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                calculRemise();
            }

        });
        boutonClearDepart.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                montantFinal.setText("");
                porcentageB.setText("");
                errorLabelDepart.setText("");
            }
        });
        boutonCalculDepart.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                calculPrixDepart();
            }

        });
    }

    private void calculPrixDepart() {

    }

    private void calculRemise() {

        if (!testNombre(montantDepar.getText(), porcentageA.getText())) {
            return;
        }

        service.calculRemise(Integer.parseInt(porcentageA.getText()), Integer.parseInt(montantDepar.getText()), new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                // Show the RPC error message to the user
//                Window.alert(SERVER_ERROR);
            }

            public void onSuccess(String result) {
                errorLabelRemise.setText(" ");
                String str = "Pourcentage=" + porcentageA.getText() + " Montant Innitial" + montantDepar.getText();
                new DialogBoxInssetPresenter("Convertion Roman to arabe", str, result);
            }

        });
    }

    public boolean testNombre(String nbr, String p) {
        try {
            Integer value = Integer.parseInt(montantDepar.getText());
            Integer value2 = Integer.parseInt(porcentageA.getText());
        } catch (NumberFormatException e) {
            errorLabelRemise.addStyleName("serverResponseLabelError");
            errorLabelRemise.setText("Format incorect");
            return false;
        }
        if (!FieldVerifier.isValidDecimal2(Integer.parseInt(porcentageA.getText()))) {
            errorLabelRemise.addStyleName("serverResponseLabelError");
            errorLabelRemise.setText("Le porcentage doit être compris en 0 et 100");
            return false;
        }
        if (!FieldVerifier.isEntier(porcentageA.getText())) {
            errorLabelRemise.addStyleName("serverResponseLabelError");
            errorLabelRemise.setText("Le porcentage doit être compris en 0 et 100");
            return false;
        }
        return true;
    }
}
