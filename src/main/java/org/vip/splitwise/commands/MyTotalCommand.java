package org.vip.splitwise.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.vip.splitwise.controllers.SettleUpController;
import org.vip.splitwise.dtos.MyTotalResponseDto;

@Component
public class MyTotalCommand implements Command {
    /*
        Expected Input:     u1 MyTotal (u1 is asking to see the total amount they owe/recieve after everything is settled)
        Expected Output:    SUCCESS. Your total outstanding is Rs (+/-)XX.XX
    */
    private SettleUpController settleUpController;

    @Autowired
    public MyTotalCommand(SettleUpController settleUpController) {
        this.settleUpController = settleUpController;
    }

    @Override
    public boolean matches(String input) {
        String[] params = input.split(" ");
        return params.length == 2 && CommandType.MY_TOTAL.getLabel().equals(params[1]);
    }

    @Override
    public void execute(String input) {
        String[] params = input.split(" ");
        MyTotalResponseDto responseDto = settleUpController.getUserTotalOutstanding(params[0]);
        if (responseDto.getResponseCode() == HttpStatus.OK) {
            System.out.println(responseDto.getResponseMsg() + ". Your total outstanding is Rs " + responseDto.getOutstanding());
        } else {
            System.out.println(responseDto.getResponseMsg());
        }
    }
}
