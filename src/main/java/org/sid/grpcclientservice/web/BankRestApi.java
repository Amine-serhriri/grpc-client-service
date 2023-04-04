package org.sid.grpcclientservice.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.sid.bankgrpcservice.grpc.stub.Bank;
import org.sid.bankgrpcservice.grpc.stub.BankServiceGrpc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankRestApi {
    @GrpcClient("bank")
    private BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;
    @GetMapping("convert")
    public ConvertResponseDTO convert(
            @RequestParam String currencyFrom,
            @RequestParam String currencyTo,
            @RequestParam  double amount){

        Bank.ConvertCurrencyRequest currencyRequest= Bank.ConvertCurrencyRequest.newBuilder()
                .setAmount(amount)
                .setCurrencyTo(currencyTo)
                .setCurrencyFrom(currencyFrom)
                .build();
        Bank.ConvertCurrencyResponse convertCurrencyResponse = bankServiceBlockingStub.convertCurrency(currencyRequest);

        ConvertResponseDTO convertResponseDTO=new ConvertResponseDTO(
                convertCurrencyResponse.getCurrencyTo(),
                convertCurrencyResponse.getCurrencyFrom(),
                convertCurrencyResponse.getAmount(),
                convertCurrencyResponse.getConversionResult()
        );

        return convertResponseDTO;
    }

}
@Data
@AllArgsConstructor
class ConvertResponseDTO{
    private String from;
    private String to;
    private double amount;
    private double result;

}
