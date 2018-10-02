package com.invest.Gui.listener.settings;

import com.invest.Gui.exception.DifferentWordsException;
import com.invest.Gui.frames.ChangingFrame;
import com.invest.Gui.frames.WarningFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangingFrameActionListener extends ConfirmDecisionRequestCreator implements ActionListener {

    private ChangingFrame changingFrame;
    private String operation;
    private String param;
    private String zero;
    private String one;
    private String two;

    public ChangingFrameActionListener(Long userId, ChangingFrame changingFrame) {
        super(userId);
        this.changingFrame = changingFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        operation = changingFrame.getTypeParam();
        param = changingFrame.getParam();
        zero = changingFrame.getOldUserParam().getText();
        one = changingFrame.getNewParamOne().getText();
        two = changingFrame.getNewParamTwo().getText();

        switch (operation) {
            case "password":
                changePassword();
                break;
            default:
                changeParam();
        }
    }

    private void changeParam() {
        try {
            String value = createNewParam();
            System.out.println("new " + operation + ": " + value);
            changingFrame.dispose();
        } catch (DifferentWordsException dwe) {
            WarningFrame.openWarningFrame(dwe.getMessage());
        }
    }

    private void changePassword() {
        try {
            String value = createNewPassword();
            System.out.println("new password: " + value);
            changingFrame.dispose();
        } catch (DifferentWordsException dwe) {
            WarningFrame.openWarningFrame(dwe.getMessage());
        }
    }

    private String createNewParam() throws DifferentWordsException {
        if(one.equals(two)) {
            return one;
        } else {
            throw new DifferentWordsException(operation);
        }
    }

    private String createNewPassword() throws DifferentWordsException {
        boolean correctOldPassword = param.equals(zero);
        boolean samePasswords = one.equals(two);
        if(correctOldPassword && samePasswords) {
            return one;
        } else {
            throw new DifferentWordsException(operation);
        }
    }

}
