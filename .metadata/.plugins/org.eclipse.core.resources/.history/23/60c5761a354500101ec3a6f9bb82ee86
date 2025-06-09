package com.minhaloja.sistema_pagamento.util;

import java.awt.*;
import java.awt.TrayIcon.MessageType;

public class NotificacaoSistema {

    public void mostrarNotificacaoPopUp(String msg) {
        // Verifica se o sistema suporta notificações na bandeja
        if (!SystemTray.isSupported()) {
            System.err.println("SystemTray não é suportado!");
            return;
        }

        // Cria um ícone (obrigatório, mesmo que oculto)
        Image image = Toolkit.getDefaultToolkit().createImage("order-management-16x16.png");

        TrayIcon trayIcon = new TrayIcon(image, "Notificação");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("FPS - SOLUTIONS");

        try {
            SystemTray tray = SystemTray.getSystemTray();
            tray.add(trayIcon);

            // Exibe a notificação
            trayIcon.displayMessage(
                "FPS - SOLUTIONS",
                msg,
                MessageType.WARNING
            );
            
            new Thread(() -> {
                try {
                    Thread.sleep(50000);
                    tray.remove(trayIcon);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (AWTException e) {
            System.err.println("Erro ao adicionar o ícone na bandeja: " + e.getMessage());
        }
    }
}
