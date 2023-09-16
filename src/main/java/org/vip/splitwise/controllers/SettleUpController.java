package org.vip.splitwise.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.vip.splitwise.dtos.SettleUpResponseDto;
import org.vip.splitwise.services.SettleUpService;

@Controller
public class SettleUpController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private SettleUpService settleUpService;

    @Autowired
    public SettleUpController(SettleUpService settleUpService) {
        this.settleUpService = settleUpService;
    }

    public SettleUpResponseDto getAllSettleUpTransactions(String userId, String groupId) {
        SettleUpResponseDto responseDto = new SettleUpResponseDto();
        try {
            if (groupId == null) {
                responseDto = settleUpService.getAllUserSettleUpTransactions(userId);
            } else {
                responseDto = settleUpService.getAllGroupSettleUpTransactions(groupId);
            }
            responseDto.setResponseCode(HttpStatus.OK);
            responseDto.setResponseMsg("SUCCESS");
        } catch (Exception e) {
            LOGGER.error("Error in SettleUpController -> getAllSettleUpTransactions() : " + e.getMessage());
            responseDto.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDto.setResponseMsg("FAILURE. Reason: " + e.getMessage());
        }
        return responseDto;
    }
}
