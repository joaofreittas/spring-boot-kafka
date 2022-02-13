package com.home.kafkaconsumer.dto;

import com.home.kafkaconsumer.enums.PaymentMethod;
import lombok.Data;

import java.io.Serializable;

@Data
public class TransactionDTO implements Serializable {

  private String UUID;
  private float value;
  private String issuerUsername;
  private String issuerDocumentUser;
  private String receiverUsername;
  private String receiverDocumentUser;
  private PaymentMethod paymentMethod;

}
