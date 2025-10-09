package com.banksimulation.service;

public interface EmailService {
    boolean emailSender(String from,String to,String subject, String text);
}